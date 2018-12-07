package info.clo5de.asukarpg.item;

import info.clo5de.asukarpg.AsukaRPG;
import org.bukkit.Material;

public class Ingredient {

    public static Ingredient AIR () {
        return new Ingredient(ItemID.AIR(), null, 0);
    }

    public static Ingredient fromConfig (String configString) {
        String[] split = configString.split(" ");
        if (split[0].startsWith("I:")) { // MeowItem
            MeowItem meowItem = AsukaRPG.INSTANCE.itemHandler.getItem(
                    split[0].replace("I:", ""));
            if (meowItem != null)
                return new Ingredient(meowItem, Integer.parseInt(split[1]));
            return null;
        } else {
            int quantity = split.length == 3 ? Integer.parseInt(split[2]) : 0;
            ItemID itemID = ItemID.fromConfig(split[0]);
            return new Ingredient(itemID, split[1], quantity);
        }
    }

    private ItemID itemID;
    private String displayName;
    private int quantity;

    public Ingredient (MeowItem item, int quantity) {
        this.itemID = item.getItemID();
        this.displayName = item.getDisplayName();
        this.quantity = quantity;
    }

    public Ingredient (ItemID itemID, String displayName, int quantity) {
        this.itemID = itemID;
        this.displayName = displayName;
        this.quantity = quantity;
    }

    public ItemID getItemID () {
        return this.itemID;
    }

    public Material getMaterial () {
        return this.itemID.getMaterial();
    }

    public String getDisplayName () {
        return this.displayName;
    }

    public int getQuantity () {
        return this.quantity;
    }

}
