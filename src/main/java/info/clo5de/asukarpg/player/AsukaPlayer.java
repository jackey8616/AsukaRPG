package info.clo5de.asukarpg.player;

import info.clo5de.asukarpg.item.ExEnchant;
import info.clo5de.asukarpg.item.MeowItem;
import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AsukaPlayer {

    private Player player;
    private Map<String, ExEnchant> exEnchantMap = new HashMap<>();

    public AsukaPlayer (Player player) {
        Validate.notNull(player);
        this.player = player;
    }

    public Player getVanillaPlayer () {
        return this.player;
    }

    public double getHealth () {
        return this.player.getHealth();
    }

    public void setHealth (double newHealth) {
        this.player.setHealth(newHealth);
    }

    public ItemStack getItemInMainHand () {
        return this.player.getInventory().getItemInMainHand();
    }

    public Map<String, ExEnchant> getExEnchantMap () {
        return this.exEnchantMap;
    }

    public void onEquip (MeowItem meowItem) {
        Set<Map.Entry<String, ExEnchant>> set = meowItem.getItemExEnchant().toEntrySet();
        for (Map.Entry<String, ExEnchant> each : set) {
            if (exEnchantMap.containsKey(each.getKey())) {
                exEnchantMap.put(each.getKey(), each.getValue());
            } else {
                ExEnchant exEnchant = exEnchantMap.get(each.getKey());
                exEnchant.setAbility(exEnchant.getAbility() * each.getValue().getAbility());
                exEnchant.setEffect(exEnchant.getEffect() * each.getValue().getEffect());
            }
        }
    }

}
