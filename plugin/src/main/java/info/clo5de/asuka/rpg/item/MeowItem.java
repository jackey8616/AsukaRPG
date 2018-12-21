package info.clo5de.asuka.rpg.item;

import info.clo5de.asuka.rpg.AsukaRPG;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MeowItem {

    private String filePath;
    private String itemKey;
    private String displayName;

    private ItemType itemType;
    private ItemID itemID;
    private ItemColor itemColor;
    private ItemLore itemLore;
    private ItemEnchant itemEnchant;
    private ItemExEnchant itemExEnchant;
    private ItemRecipe itemRecipe;

    private int quantity;
    private boolean canCraft;
    private boolean unbreakable;
    private boolean hideEnchants;
    private ItemStack itemStack;

    public MeowItem (String filePath, String itemKey, String displayName, ItemType itemType, ItemID itemID,
                     ItemColor itemColor, ItemLore itemLore, ItemEnchant itemEnchant, ItemExEnchant itemExEnchant,
                     ItemRecipe itemRecipe, int quantity, boolean canCraft, boolean unbreakable, boolean hideEnchants) {
        this.filePath = filePath;
        this.itemKey = itemKey;
        this.displayName = displayName;

        this.itemType = itemType;
        this.itemID = itemID;
        this.itemColor = itemColor;
        this.itemLore = itemLore;
        this.itemEnchant = itemEnchant;
        this.itemExEnchant = itemExEnchant;
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

    public String getFilePath () {
        return this.filePath;
    }

    public String getItemKey () {
        return this.itemKey;
    }

    public String getDisplayName () {
        return this.displayName;
    }

    public ItemType getItemType () {
        return this.itemType;
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

    public ItemExEnchant getItemExEnchant () {
        return this.itemExEnchant;
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
        MeowItem meowItem = new MeowItem(this.filePath, this.itemKey, this.displayName, this.itemType, this.itemID,
                this.itemColor, this.itemLore, this.itemEnchant, this.itemExEnchant, this.itemRecipe, this.quantity,
                this.canCraft, this.unbreakable, this.hideEnchants);
        meowItem.itemStack = this.itemStack.clone();
        return meowItem;
    }

    @Deprecated
    public void writeToItemStackNBT () {
        AsukaRPG.INSTANCE.getItemHandler().writeToItemStackNBT(this);
    }

}
