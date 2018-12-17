package info.clo5de.asukarpg.item;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ItemEnchant {


    public static ItemEnchant fromConfig (List list) {
        if (list != null && list.size() != 0) {
            Map<Enchantment, Integer> enchantments = new HashMap<>();
            for (int i = 0; i < list.size(); ++i) {
                String[] line = ((String) list.get(i)).split(":");
                enchantments.put(Enchantment.getByName(line[0]), line.length >= 2 ? Integer.valueOf(line[1]) : 1);
            }
            return new ItemEnchant(enchantments);
        }
        return null;
    }

    private Map<Enchantment, Integer> enchantment;

    public ItemEnchant (Map<Enchantment, Integer> enchantment) {
        this.enchantment = enchantment;
    }

    public ItemMeta applyEnchant (ItemMeta itemMeta) {
        Iterator<Map.Entry<Enchantment, Integer>> iterator = enchantment.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Enchantment, Integer> each = iterator.next();
            itemMeta.addEnchant(each.getKey(), each.getValue(), true);
        }
        return itemMeta;
    }

}
