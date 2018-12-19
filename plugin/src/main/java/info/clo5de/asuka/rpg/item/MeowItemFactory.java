package info.clo5de.asuka.rpg.item;

import info.clo5de.asuka.rpg.exception.ItemConfigException;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MeowItemFactory {

    public static MeowItem fromConfig(String itemKey, MemorySection config) throws Exception {
        String displayName = config.getString("DisplayName");
        ItemID itemID = ItemID.fromConfig(config.getString("ItemID"));
        ItemColor itemColor = ItemColor.fromConfig(config);
        ItemLore itemLore = ItemLore.fromConfig(config.getStringList("ItemLore"));
        ItemEnchant itemEnchant = ItemEnchant.fromConfig(config.getList("ItemEnchant"));
        ItemExEnchant itemExEnchant = ItemExEnchant.fromConfig(ItemType.ASUKA, config.getList("ItemExEnchant"));
        ItemRecipe itemRecipe = ItemRecipe.fromKycConfig(itemKey, config.getList("ItemRecipe"));

        int quantity = config.getInt("Quantity", 1);
        boolean canCraft = config.getBoolean("CanCraft", true);
        boolean unbreakable = config.getBoolean("Unbreakable", false);
        boolean hideEnchants = config.getBoolean("HideEnchants", false);

        return new MeowItem(itemKey, displayName, ItemType.ASUKA, itemID, itemColor, itemLore, itemEnchant,
                itemExEnchant, itemRecipe, quantity, canCraft, unbreakable, hideEnchants);
    }

    public static MeowItem fromKycConfig(String displayName, MemorySection config) throws Exception {
        String itemKey = config.getString("ItemKey");
        ItemID itemID = ItemID.fromConfig(config.getString("ItemID"));
        ItemColor itemColor = ItemColor.fromKycConfig(config);
        ItemLore itemLore = ItemLore.fromConfig(config.getStringList("ItemLores"));
        ItemEnchant itemEnchant = ItemEnchant.fromConfig(config.getList("Enchants"));
        ItemExEnchant itemExEnchant = ItemExEnchant.fromConfig(ItemType.KYCRAFT, config.getList("SpecialEffects"));
        ItemRecipe itemRecipe = ItemRecipe.fromKycConfig(itemKey, config.getList("Materials"));

        int quantity = config.getInt("Quantity", 1);
        boolean canCraft = config.getBoolean("CanCraft", true);
        boolean unbreakable = config.getBoolean("Unbreakable", false);
        boolean hideEnchants = config.getBoolean("HideEnchants", false);

        return new MeowItem(itemKey, displayName, ItemType.KYCRAFT, itemID, itemColor, itemLore, itemEnchant,
                itemExEnchant, itemRecipe, quantity, canCraft, unbreakable, hideEnchants);
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
            for (String itemKey : config.getKeys(false)) {
                try {
                    stacks.put(itemKey, fromConfig(itemKey, (MemorySection) config.get(itemKey)));
                } catch (ItemConfigException exception) {
                    String message = "ItemKey: %s 's file has error, aborted!. ACTION: %s, STAGE: %s";
                    System.err.println(String.format(message, itemKey, exception.getAction(), exception.getStage()));
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            return stacks;
        }
    }

    public static Map<String, MeowItem> loadFromKycYaml (MemorySection config) {
        Map<String, MeowItem> stacks = new HashMap<>();
        for (String displayName : config.getKeys(false)) {
            MemorySection fileConfig = (MemorySection) config.get(displayName);
            String itemKey = fileConfig.getString("ItemKey");
            try {
                stacks.put(itemKey, fromKycConfig(displayName, fileConfig));
            } catch (ItemConfigException exception) {
                String message = "ItemKey: %s 's file has error, aborted!. ACTION: %s, STAGE: %s";
                System.err.println(String.format(message, itemKey, exception.getAction(), exception.getStage()));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return stacks;
    }
}
