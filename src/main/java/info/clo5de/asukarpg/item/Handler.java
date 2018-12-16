package info.clo5de.asukarpg.item;

import info.clo5de.asukarpg.AsukaRPG;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Handler {

    private AsukaRPG plugin;
    private final File itemFolder;
    private Map<String, MeowItem> items = new HashMap<>();

    public Handler(AsukaRPG plugin) {
        this.plugin = plugin;
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

    public MeowItem getItemFromNBT (ItemStack itemStack) {
        net.minecraft.server.v1_12_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(itemStack);
        if (!nmsStack.hasTag())
            return null;
        NBTTagCompound compound = nmsStack.getTag();
        String itemKey = compound.getString("ItemKey");
        return items.containsKey(itemKey) ? items.get(itemKey).clone() : null;
    }


}
