package info.clo5de.asukarpg.item;

import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemLores {

    public static ItemLores fromKycConfig (List list) {
        return list.size() > 0 ? new ItemLores((String[]) list.toArray(new String[list.size()])) : null;
    }

    private String[] lines;

    public ItemLores (String ... lines) {
        this.lines = lines;
    }

    public List<String> toList () {
        return new ArrayList<>(Arrays.asList(lines));
    }

    public ItemMeta applyLores (ItemMeta itemMeta) {
        itemMeta.setLore(toList());
        return itemMeta;
    }

}
