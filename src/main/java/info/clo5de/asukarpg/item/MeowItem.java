package info.clo5de.asukarpg.item;

import org.bukkit.Material;
import org.bukkit.configuration.MemorySection;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MeowItem {

    public static MeowItem fromConfig (String itemKey, MemorySection config) {
        String displayName = config.getString("DisplayName");
        ItemID itemID = ItemID.fromConfig(config.getString("ItemID"));
        ItemColor itemColor = ItemColor.fromKycConfig(config);
        ItemLores itemLores = ItemLores.fromKycConfig(config.getList("ItemLores"));
        ItemEnchant itemEnchant = ItemEnchant.fromConfig(config.getList("Enchants"));
        ItemRecipe itemRecipe = ItemRecipe.fromKycConfig(itemKey, config.getList("Recipe"));

        int quantity = config.getInt("Quantity", 1);
        boolean canCraft = config.getBoolean("CanCraft", true);
        boolean unbreakable = config.getBoolean("Unbreakable", false);
        boolean hideEnchants = config.getBoolean("HideEnchants", false);


        return new MeowItem(itemKey, displayName, itemID, itemColor, itemLores, itemEnchant, itemRecipe, quantity,
                canCraft, unbreakable, hideEnchants);
    }

    public static MeowItem fromKycConfig (String displayName, MemorySection config) {
        String itemKey = config.getString("ItemKey");
        ItemID itemID = ItemID.fromConfig(config.getString("ItemID"));
        ItemColor itemColor = ItemColor.fromKycConfig(config);
        ItemLores itemLores = ItemLores.fromKycConfig(config.getList("ItemLores"));
        ItemEnchant itemEnchant = ItemEnchant.fromConfig(config.getList("Enchants"));
        ItemRecipe itemRecipe = ItemRecipe.fromKycConfig(itemKey, config.getList("Materials"));

        int quantity = config.getInt("Quantity", 1);
        boolean canCraft = config.getBoolean("CanCraft", true);
        boolean unbreakable = config.getBoolean("Unbreakable", false);
        boolean hideEnchants = config.getBoolean("HideEnchants", false);


        return new MeowItem(itemKey, displayName, itemID, itemColor, itemLores, itemEnchant, itemRecipe, quantity,
                canCraft, unbreakable, hideEnchants);
    }

    private String itemKey;
    private String displayName;
    private ItemID itemID;
    private ItemColor itemColor;
    private ItemLores itemLores;
    private ItemEnchant itemEnchant;
    private ItemRecipe itemRecipe;

    private int quantity;
    private boolean canCraft;
    private boolean unbreakable;
    private boolean hideEnchants;
    private ItemStack itemStack;

    public MeowItem (String itemKEy, String displayName, ItemID itemID, ItemColor itemColor, ItemLores itemLores,
                     ItemEnchant itemEnchant, ItemRecipe itemRecipe, int quantity, boolean canCraft,
                     boolean unbreakable, boolean hideEnchants) {
        this.itemKey = itemKey;
        this.displayName = displayName;
        this.itemID = itemID;
        this.itemColor = itemColor;
        this.itemLores = itemLores;
        this.itemEnchant = itemEnchant;
        this.itemRecipe = itemRecipe;

        this.quantity = quantity;
        this.canCraft = canCraft;
        this.unbreakable = unbreakable;
        this.hideEnchants = hideEnchants;
        this.itemStack = this.buildItemStack();
    }

    public ItemStack buildItemStack () {
        ItemStack itemStack = this.itemID.makeItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemColor != null && itemID.isLeather())
            itemStack = itemColor.applyColor(itemStack);

        itemMeta.setDisplayName(this.displayName);
        if (itemEnchant != null)
            itemMeta = itemEnchant.applyEnchant(itemMeta);
        if (itemLores != null)
            itemMeta = itemLores.applyLores(itemMeta);

        if (this.unbreakable)
            itemMeta.setUnbreakable(true);
        if (this.hideEnchants)
            itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });

        itemStack.setAmount(this.quantity);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public void buildItemRecipe () {
        this.itemRecipe.buildItemRecipe(this.itemStack);
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

    public ItemLores getItemLores () {
        return this.getItemLores();
    }

    public ItemEnchant getItemEnchant () {
        return this.getItemEnchant();
    }

    public ItemRecipe getItemRecipe () {
        return this.itemRecipe;
    }

}
