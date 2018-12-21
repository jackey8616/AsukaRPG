package info.clo5de.asuka.rpg.item;

import info.clo5de.asuka.rpg.AsukaRPG;
import info.clo5de.asuka.rpg.exception.ItemConfigException;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MeowItemFactory {

    public static MeowItem fromConfig(File file, String itemKey, MemorySection config) throws ItemConfigException {
        String filePath = file.getPath();

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

        return new MeowItem(filePath, itemKey, displayName == null ? itemKey : displayName, ItemType.ASUKA, itemID,
                itemColor, itemLore, itemEnchant, itemExEnchant, itemRecipe, quantity, canCraft, unbreakable,
                hideEnchants);
    }

    public static MeowItem fromKycConfig(
            File file, String displayName, MemorySection config) throws ItemConfigException {
        if (config.contains("ItemKey")) {
            String filePath = file.getPath();

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

            return new MeowItem(filePath, itemKey, displayName, ItemType.KYCRAFT, itemID, itemColor, itemLore,
                    itemEnchant, itemExEnchant, itemRecipe, quantity, canCraft, unbreakable, hideEnchants);
        }
        throw new ItemConfigException(
                ItemConfigException.Action.READ, ItemConfigException.Stage.ItemKey, "ItemKey is required.");
    }

    public static Map<String, MeowItem> loadFromYaml (Set<String> itemKeySet, File file) throws ItemConfigException {
        return loadFromYaml(itemKeySet, file, YamlConfiguration.loadConfiguration(file));
    }

    public static Map<String, MeowItem> loadFromYaml (
            Set<String> itemKeySet, File file, YamlConfiguration yaml) throws ItemConfigException {
        if (yaml.contains("CustomCrafterEx")) {
            return loadFromKycYaml(itemKeySet, file, (MemorySection) yaml.get("CustomCrafterEx"));
        } else if (yaml.contains("AsukaRPG")){
            MemorySection config = (MemorySection) yaml.get("AsukaRPG");
            Map<String, MeowItem> stacks = new HashMap<>();
            for (String itemKey : config.getKeys(false)) {
                try {
                    MeowItem meowItem = fromConfig(file, itemKey, (MemorySection) config.get(itemKey));
                    if (itemKeySet.contains(itemKey)) {
                        String message = "Item duplicated at ItemKey: %s with File: %s and File %s.";
                        AsukaRPG.INSTANCE.logger().warning(String.format(message, itemKey, meowItem.getFilePath(),
                                AsukaRPG.INSTANCE.getItemHandler().getItemByKey(itemKey).getFilePath()));
                    } else if (stacks.containsKey(itemKey)) {
                        AsukaRPG.INSTANCE.logger().warning(String.format("Item duplicated at ItemKey: %s.", itemKey));
                    } else {
                        stacks.put(itemKey, meowItem);
                        AsukaRPG.INSTANCE.logger().info(String.format("ItemKey: %s loaded.", itemKey));
                    }
                } catch (ItemConfigException e) {
                    String message = "File: %s with ItemKey: %s 's file has error"
                            + ", aborted!. ACTION: %s, STAGE: %s, Message: %s.";
                    AsukaRPG.INSTANCE.logger().warning(String.format(
                            message, file.getName(), itemKey, e.getAction(), e.getStage(), e.getMessage()));
                }
            }
            return stacks;
        } else {
            throw new ItemConfigException(ItemConfigException.Action.READ, ItemConfigException.Stage.Root,
                    "Missing Root Key, should be AsukaRPG or CustomCrafterEx");
        }
    }

    public static Map<String, MeowItem> loadFromKycYaml (Set<String> itemKeySet, File file, MemorySection config) {
        Map<String, MeowItem> stacks = new HashMap<>();
        for (String displayName : config.getKeys(false)) {
            MemorySection fileConfig = (MemorySection) config.get(displayName);
            String itemKey = fileConfig.getString("ItemKey");
            try {
                MeowItem meowItem = fromKycConfig(file, displayName, fileConfig);
                if (itemKeySet.contains(itemKey)) {
                    String message = "Item duplicated at ItemKey: %s with File: %s.";
                    AsukaRPG.INSTANCE.logger().warning(String.format(message, itemKey, meowItem.getFilePath()));
                } else if (stacks.containsKey(itemKey)) {
                    AsukaRPG.INSTANCE.logger().warning(String.format("Item duplicated at ItemKey: %s.", itemKey));
                } else {
                    stacks.put(itemKey, meowItem);
                    AsukaRPG.INSTANCE.logger().info(String.format("ItemKey: %s loaded.", itemKey));
                }
            } catch (ItemConfigException e) {
                String message = "File: %s with ItemKey: %s 's file has error"
                        + ", aborted!. ACTION: %s, STAGE: %s, Message: %s.";
                AsukaRPG.INSTANCE.logger().warning(String.format(
                        message, file.getName(), itemKey, e.getAction(), e.getStage(), e.getMessage()));
            }
        }
        return stacks;
    }
}
