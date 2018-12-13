package info.clo5de.asukarpg.item;

import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.configuration.MemorySection;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MeowItem {

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

    private String itemKey;
    private String displayName;
    private ItemID itemID;
    private ItemColor itemColor;
    private ItemLore itemLore;
    private ItemEnchant itemEnchant;
    private ItemRecipe itemRecipe;

    private int quantity;
    private boolean canCraft;
    private boolean unbreakable;
    private boolean hideEnchants;
    private ItemStack itemStack;

    public MeowItem (String itemKey, String displayName, ItemID itemID, ItemColor itemColor, ItemLore itemLore,
                     ItemEnchant itemEnchant, ItemRecipe itemRecipe, int quantity, boolean canCraft,
                     boolean unbreakable, boolean hideEnchants) {
        this.itemKey = itemKey;
        this.displayName = displayName;
        this.itemID = itemID;
        this.itemColor = itemColor;
        this.itemLore = itemLore;
        this.itemEnchant = itemEnchant;
        this.itemRecipe = itemRecipe;

        this.quantity = quantity;
        this.canCraft = canCraft;
        this.unbreakable = unbreakable;
        this.hideEnchants = hideEnchants;
    }

    public ItemStack buildItemStack () {
        ItemStack itemStack = this.itemID.makeItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(this.displayName);
        if (itemColor != null && itemID.isLeather())
            itemMeta = itemColor.applyColor(itemMeta);
        if (itemEnchant != null)
            itemMeta = itemEnchant.applyEnchant(itemMeta);
        if (itemLore != null)
            itemMeta = itemLore.applyLore(itemMeta);

        if (this.unbreakable)
            itemMeta.setUnbreakable(true);
        if (this.hideEnchants)
            itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });

        itemStack.setAmount(this.quantity);
        itemStack.setItemMeta(itemMeta);

        this.itemStack = itemStack;
        return this.itemStack;
    }

    public ItemMeta buildItemRecipe () {
        this.itemRecipe.buildItemRecipe(this.itemStack);
        return this.itemStack.getItemMeta();
    }

    public String getItemKey () {
        return this.itemKey;
    }

    public String getDisplayName () {
        return this.displayName;
    }

    public ItemID getItemID () {
        return this.itemID;
    }

    public Material getMaterial () {
        return this.itemID.getMaterial();
    }

    public ItemColor getItemColor () {
        return this.itemColor;
    }

    public ItemLore getItemLore() {
        return this.itemLore;
    }

    public ItemEnchant getItemEnchant () {
        return this.itemEnchant;
    }

    public ItemRecipe getItemRecipe () {
        return this.itemRecipe;
    }

    public ItemStack getItemStack () {
        return this.itemStack;
    }

    public void setItemStack (ItemStack newStack) {
        this.itemStack = newStack;
    }

    public MeowItem clone () {
        MeowItem meowItem = new MeowItem(this.itemKey, this.displayName, this.itemID, this.itemColor, this.itemLore,
                this.itemEnchant, this.itemRecipe, this.quantity, this.canCraft, this.unbreakable, this.hideEnchants);
        meowItem.itemStack = this.itemStack.clone();
        return meowItem;
    }

    public void writeToItemStackNBT () {
        net.minecraft.server.v1_12_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(this.itemStack);
        NBTTagCompound compound = nmsStack.hasTag() ? nmsStack.getTag() : new NBTTagCompound();

        compound.setString("ItemKey", this.itemKey);

        nmsStack.setTag(compound);
        this.itemStack = CraftItemStack.asBukkitCopy(nmsStack);
    }

}
