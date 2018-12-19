package info.clo5de.asuka.rpg.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ItemExEnchant {

    public static ItemExEnchant fromConfig (ItemType itemType, List list) {
        if (list == null || list.size() == 0)
            return null;
        Map<String, ExEnchant> map = new HashMap<>();
        for (int i = 0; i < list.size(); ++i) {
            String[] line = ((String) list.get(i)).split(" ");
            double ability = Double.parseDouble(line[1]);
            double effect = line.length == 3 ? Double.parseDouble(line[2]) : 0.0D;
            map.put(line[0], new ExEnchant(line[0], ability > 0 ? ability / 100.0D : ability, effect));
        }
        return new ItemExEnchant(map);
    }

    private Map<String, ExEnchant> map;

    public ItemExEnchant (Map<String, ExEnchant> map) {
        this.map = map;
    }

    public Set<Map.Entry<String, ExEnchant>> toEntrySet () {
        return this.map.entrySet();
    }

}