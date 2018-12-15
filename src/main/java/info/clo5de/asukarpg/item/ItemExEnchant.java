package info.clo5de.asukarpg.item;

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
            if (itemType.equals(ItemType.ASUKA))
                map.put(line[0], new ExEnchant(
                        line[0], Double.parseDouble(line[1]), line.length == 3 ? Double.parseDouble(line[2]) : 0.0D));
            else if (itemType.equals(ItemType.KYCRAFT))
                map.put(line[0], new ExEnchant(
                        line[0], Integer.parseInt(line[1]), line.length == 3 ? Integer.parseInt(line[2]) : 0.0D));
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