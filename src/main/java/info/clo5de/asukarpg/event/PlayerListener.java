package info.clo5de.asukarpg.event;

import com.codingforcookies.armorequip.ArmorEquipEvent;
import info.clo5de.asukarpg.AsukaRPG;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {

    private AsukaRPG plugin;
    private info.clo5de.asukarpg.player.Handler playerHandler;

    public PlayerListener(AsukaRPG plugin) {
        this.plugin = plugin;
        this.playerHandler = this.plugin.getPlayerHandler();
    }

    @EventHandler
    public void onPlayerEquipe (ArmorEquipEvent event) {
        Player player = event.getPlayer();
        ItemStack equipe = event.getNewArmorPiece();

        this.playerHandler.updatePlayerEquip(player, equipe);
    }

}