package info.clo5de.asukarpg.player;

import info.clo5de.asukarpg.AsukaRPG;
import info.clo5de.asukarpg.item.MeowItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class Handler {

    private AsukaRPG plugin;
    private info.clo5de.asukarpg.item.Handler itemHandler;

    private Map<Player, AsukaPlayer> playerMap = new HashMap<>();

    public Handler(AsukaRPG plugin) {
        this.plugin = plugin;
        this.itemHandler = this.plugin.getItemHandler();
    }

    public Map<Player, AsukaPlayer> getPlayerMap () {
        return this.playerMap;
    }

    public AsukaPlayer getPlayer (Player player) {
        return this.playerMap.get(player);
    }

    public boolean containsPlayer (Player player) {
        return this.playerMap.containsKey(player);
    }

    public void updatePlayerEquip (Player player, ItemStack equip) {
        MeowItem meowItem = this.itemHandler.getItemFromNBT(equip);
        if (meowItem == null)
            return;

        AsukaPlayer asukaPlayer;
        if (this.playerMap.containsKey(player))
            asukaPlayer = this.playerMap.get(player);
        else
            asukaPlayer = new AsukaPlayer(player);
        asukaPlayer.onEquip(meowItem);
    }
}
