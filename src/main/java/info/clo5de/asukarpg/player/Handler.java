package info.clo5de.asukarpg.player;

import info.clo5de.asukarpg.AsukaRPG;
import info.clo5de.asukarpg.item.MeowItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Handler {

    private AsukaRPG plugin;
    private info.clo5de.asukarpg.item.Handler itemHandler;

    private Map<UUID, AsukaPlayer> playerMap = new HashMap<>();

    public Handler(AsukaRPG plugin) {
        this.plugin = plugin;
        this.itemHandler = this.plugin.getItemHandler();
    }

    public Map<UUID, AsukaPlayer> getPlayerMap () {
        return this.playerMap;
    }

    public AsukaPlayer getPlayer (UUID uuid) {
        return this.playerMap.get(uuid);
    }

    public AsukaPlayer getPlayer (Player player) {
        return this.playerMap.get(player.getUniqueId());
    }

    public boolean containsPlayer (UUID uuid) {
        return this.playerMap.containsKey(uuid);
    }

    public boolean containsPlayer (Player player) {
        return this.playerMap.containsKey(player.getUniqueId());
    }

    public void updatePlayerEquip (Player player, ItemStack equip) {
        MeowItem meowItem = this.itemHandler.getItemFromNBT(equip);
        if (meowItem == null)
            return;

        AsukaPlayer asukaPlayer;
        if (this.playerMap.containsKey(player.getUniqueId()))
            asukaPlayer = this.playerMap.get(player.getUniqueId());
        else {
            asukaPlayer = new AsukaPlayer(player);
            this.playerMap.put(player.getUniqueId(), asukaPlayer);
        }
        asukaPlayer.onEquip(meowItem);
    }
}
