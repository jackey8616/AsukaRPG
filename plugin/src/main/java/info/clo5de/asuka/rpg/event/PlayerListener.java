package info.clo5de.asuka.rpg.event;

import com.codingforcookies.armorequip.ArmorEquipEvent;
import info.clo5de.asuka.rpg.AsukaRPG;
import info.clo5de.asuka.rpg.player.Handler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {

    private AsukaRPG plugin;
    private Handler playerHandler;

    public PlayerListener(AsukaRPG plugin) {
        this.plugin = plugin;
        this.playerHandler = this.plugin.getPlayerHandler();
    }

    @EventHandler
    public void onPlayerChangeSlot (PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack held = player.getInventory().getItem(event.getNewSlot());

        this.playerHandler.updatePlayerEquip(player, held);
    }

    @EventHandler
    public void onPlayerEquip (ArmorEquipEvent event) {
        Player player = event.getPlayer();
        ItemStack equip = event.getNewArmorPiece();

        this.playerHandler.updatePlayerEquip(player, equip);
    }

}