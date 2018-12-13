package info.clo5de.asukarpg.item;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MeowItemFactory {

    public static MeowItem fromConfig(String itemKey, MemorySection config) {
        String displayName = config.getString("DisplayName");
        ItemID itemID = ItemID.fromConfig(config.getString("ItemID"));
        ItemColor itemColor = ItemColor.fromConfig(config);
        ItemLore itemLore = ItemLore.fromConfig(config.getStringList("ItemLore"));
        ItemEnchant itemEnchant = ItemEnchant.fromConfig(config.getList("ItemEnchant"));
        ItemRecipe itemRecipe = ItemRecipe.fromKycConfig(itemKey, config.getList("ItemRecipe"));

        int quantity = config.getInt("Quantity", 1);
        boolean canCraft = config.getBoolean("CanCraft", true);
        boolean unbreakable = config.getBoolean("Unbreakable", false);
        boolean hideEnchants = config.getBoolean("HideEnchants", false);

        return new MeowItem(itemKey, displayName, itemID, itemColor, itemLore, itemEnchant, itemRecipe, quantity,
                canCraft, unbreakable, hideEnchants);
    }

    public static MeowItem fromKycConfig(String displayName, MemorySection config) {
        String itemKey = config.getString("ItemKey");
        ItemID itemID = ItemID.fromConfig(config.getString("ItemID"));
        ItemColor itemColor = ItemColor.fromKycConfig(config);
        ItemLore itemLore = ItemLore.fromConfig(config.getStringList("ItemLores"));
        ItemEnchant itemEnchant = ItemEnchant.fromConfig(config.getList("Enchants"));
        ItemRecipe itemRecipe = ItemRecipe.fromKycConfig(itemKey, config.getList("Materials"));

        int quantity = config.getInt("Quantity", 1);
        boolean canCraft = config.getBoolean("CanCraft", true);
        boolean unbreakable = config.getBoolean("Unbreakable", false);
        boolean hideEnchants = config.getBoolean("HideEnchants", false);

        return new MeowItem(itemKey, displayName, itemID, itemColor, itemLore, itemEnchant, itemRecipe, quantity,
                canCraft, unbreakable, hideEnchants);
    }

    public static Map<String, MeowItem> loadFromYaml (File file) {
        return loadFromYaml(YamlConfiguration.loadConfiguration(file));
    }

    public static Map<String, MeowItem> loadFromYaml (YamlConfiguration yaml) {
        if (yaml.contains("CustomCrafterEx")) {
            return loadFromKycYaml((MemorySection) yaml.get("CustomCrafterEx"));
        } else {
            MemorySection config = (MemorySection) yaml.get("AsukaRPG");
            Map<String, MeowItem> stacks = new HashMap<>();
            for (String itemKey : config.getKeys(false))
                stacks.put(itemKey, fromConfig(itemKey, (MemorySection) config.get(itemKey)));
            return stacks;
        }
    }

    public static Map<String, MeowItem> loadFromKycYaml (MemorySection config) {
        Map<String, MeowItem> stacks = new HashMap<>();
        for (String displayName : config.getKeys(false)) {
            MemorySection fileConfig = (MemorySection) config.get(displayName);
            String itemKey = fileConfig.getString("ItemKey");
            stacks.put(itemKey, fromKycConfig(displayName, fileConfig));
        }
        return stacks;
    }
}
