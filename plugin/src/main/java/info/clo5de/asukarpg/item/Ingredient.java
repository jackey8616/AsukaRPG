package info.clo5de.asukarpg.item;

import com.google.common.primitives.Ints;
import info.clo5de.asukarpg.AsukaRPG;
import org.bukkit.Material;

public class Ingredient {

    public static Ingredient AIR () {
        return new Ingredient(ItemID.AIR(), null, 0);
    }

    public static Ingredient fromConfig (String configString) throws Exception {
        String[] split = configString.split(" ");
        if (split[0].startsWith("I:")) { // MeowItem
            MeowItem meowItem = AsukaRPG.INSTANCE.getItemHandler().getItemByKey(
                    split[0].replace("I:", ""));
            if (meowItem != null)
                return new Ingredient(meowItem, split.length == 2 ? Integer.parseInt(split[1]) : 1);
            return null;
        } else {
            if (split.length < 2 || split.length > 3)
                return null;

            ItemID itemID = ItemID.fromConfig(split[0]);
            String name;
            Integer quantity;
            if (split.length == 3) {
                quantity = Ints.tryParse(split[2]);
                name = split[1].equals("0") ? null : split[1];
            } else {
                quantity = Ints.tryParse(split[1]);
                name = quantity == null || quantity == 0 ? null : split[1];
            }
            quantity = quantity == null || quantity == 0 ? 1 : quantity;
            return itemID != null ? new Ingredient(itemID, name, quantity) : null;
        }
    }

    private ItemID itemID;
    private String displayName;
    private int quantity;

    public Ingredient (MeowItem item, int quantity) {
        this(item.getItemID(), item.getDisplayName(), quantity);
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

    public boolean equals (Ingredient ingredient) {
        return this.itemID.equals(ingredient.getItemID()) &&
                ((this.displayName != null && ingredient!= null) ?
                this.displayName.equals(ingredient.getDisplayName()) :
                ingredient.getDisplayName() == null) &&
                this.quantity == ingredient.getQuantity();
    }

}
