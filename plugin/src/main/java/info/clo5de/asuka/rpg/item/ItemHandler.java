package info.clo5de.asuka.rpg.item;

import info.clo5de.asuka.rpg.AsukaRPG;
import info.clo5de.asuka.rpg.nms.ItemNMSHandler;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
            ArrayList<File> itemFiles = new ArrayList<>(Arrays.asList(this.itemFolder.listFiles()));
            itemFiles.removeIf(file -> !file.getName().endsWith(".yml"));
            for (File file : itemFiles)
                this.items.putAll(MeowItemFactory.loadFromYaml(file));
            for (Map.Entry<String, MeowItem> entry : this.items.entrySet()) {
                entry.getValue().buildItemStack();
                entry.getValue().writeToItemStackNBT();
                if (entry.getValue().getItemRecipe() != null)
                    entry.getValue().buildItemRecipe();
            }
        }
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
