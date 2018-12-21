package info.clo5de.asuka.rpg.item;

import info.clo5de.asuka.rpg.AsukaRPG;
import info.clo5de.asuka.rpg.exception.ItemConfigException;
import info.clo5de.asuka.rpg.nms.ItemNMSHandler;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ItemHandler {

    private AsukaRPG plugin;
    private ItemNMSHandler itemNMSHandler;
    private final File itemFolder;
    private Map<String, MeowItem> items = new HashMap<>();

    public ItemHandler(AsukaRPG plugin, ItemNMSHandler itemNMSHandler) {
        this.plugin = plugin;
        this.itemNMSHandler = itemNMSHandler;
        this.itemFolder = new File(plugin.getDataFolder(), "item");
    }

    public void load () {
        if (!this.itemFolder.exists()) {
            this.itemFolder.mkdirs();
        } else {
            this.items.putAll(loadItemConfiguration(new HashSet<>(this.items.keySet()), this.itemFolder.listFiles()));
            for (Map.Entry<String, MeowItem> entry : this.items.entrySet()) {
                entry.getValue().buildItemStack();
                entry.getValue().writeToItemStackNBT();
                if (entry.getValue().getItemRecipe() != null)
                    entry.getValue().buildItemRecipe();
            }
        }
    }

    private Map<String, MeowItem> loadItemConfiguration (Set<String> itemKeySet, File[] files) {
        Map<String, MeowItem> folderItems = new HashMap<>();
        for (File file : files) {
            if (file.isDirectory()) {
                loadItemConfiguration(itemKeySet, file.listFiles());
            } else {
                try {
                    Map<String, MeowItem> addingMap = MeowItemFactory.loadFromYaml(itemKeySet, file);
                    itemKeySet.addAll(new HashSet<>(addingMap.keySet()));
                    folderItems.putAll(addingMap);
                } catch (ItemConfigException exception) {
                    String message = "File: %s has error, aborted!. ACTION: %s, STAGE: %s";
                    plugin.logger().warning(
                            String.format(message, file.getName(), exception.getAction(), exception.getStage()));
                }
            }
        }
        return folderItems;
    }

    public Map<String, MeowItem> getItemMap () {
        return this.items;
    }

    public MeowItem getItemByKey (String itemKey) {
        return this.items.get(itemKey);
    }

    @Deprecated
    public MeowItem getItemFromNBT (ItemStack itemStack) {
        String itemKey = itemNMSHandler.getItemKeyFromNBT(itemStack);
        return getItemByKey(itemKey);
    }

    @Deprecated
    public void writeToItemStackNBT (MeowItem meowItem) {
        meowItem.setItemStack(
                itemNMSHandler.writeItemKeyToItemStackNBT(meowItem.getItemStack(), meowItem.getItemKey()));
    }

}
