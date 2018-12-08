package info.clo5de.asukarpg.item;

import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemLore {

    public static ItemLore fromConfig (List list) {
        return (list == null || list.size() == 0) ? null :
                new ItemLore((String[]) list.toArray(new String[list.size()]));
    }

    private String[] lines;

    public ItemLore(String ... lines) {
        this.lines = lines;
    }

    public List<String> toList () {
        return new ArrayList<>(Arrays.asList(lines));
    }

    public ItemMeta applyLore (ItemMeta itemMeta) {
        itemMeta.setLore(toList());
        return itemMeta;
    }

}
