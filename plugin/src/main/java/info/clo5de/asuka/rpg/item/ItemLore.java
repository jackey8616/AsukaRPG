package info.clo5de.asuka.rpg.item;

import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemLore {

    public static ItemLore fromConfig (List<String> list) {
        if (list == null)
            return null;
        return new ItemLore((String[]) list.toArray(new String[list.size()]));
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
