package info.clo5de.asukarpg.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemExEnchant {

    public static ItemExEnchant fromConfig (List list) {
        if (list == null || list.size() == 0)
            return null;
        Map<String, ExEnchant> map = new HashMap<>();
        for (int i = 0; i < list.size(); ++i) {
            String[] line = ((String) list.get(i)).split(" ");
            if (line.length == 3)
                map.put(line[0], new ExEnchant(line[0], Integer.parseInt(line[1]), Integer.parseInt(line[2])));
            else
                map.put(line[0], new ExEnchant(line[0], Integer.parseInt(line[1])));
        }
        return new ItemExEnchant(map);
    }

    private Map<String, ExEnchant> map;

    public ItemExEnchant (Map<String, ExEnchant> map) {
        this.map = map;
    }

}