package info.clo5de.asuka.rpg.item;

import com.google.common.primitives.Ints;
import info.clo5de.asuka.rpg.exception.ItemConfigException;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemID {

    public static ItemID AIR() {
        return new ItemID(Material.AIR, (byte)0);
    }

    public static ItemID fromConfig (String configString) throws ItemConfigException {
        if (configString == null)
            throw new ItemConfigException(
                    ItemConfigException.Action.READ, ItemConfigException.Stage.ItemID, "ItemID is required.");
        String[] idAndSub = configString.split(":");
        String id = idAndSub[0];
        byte subId = 0;

        if (idAndSub.length == 2 && Ints.tryParse(idAndSub[1]) != null)
            subId = Byte.parseByte(idAndSub[1]);

        if (Ints.tryParse(id) != null) {
            return new ItemID(Integer.valueOf(id), subId);
        } else {
            Material material = Material.matchMaterial(id);
            if (material != null)
                return new ItemID(material, subId);
            String message = String.format("No match ID: %s, aborted.", id);
            throw new ItemConfigException(ItemConfigException.Action.READ, ItemConfigException.Stage.ItemID, message);
        }
    }

    private Material material;
    private byte subId;

    public ItemID (int id, byte subId) {
        this(Material.getMaterial(id), subId);
    }

    public ItemID (Material material, byte subId) {
        this.material = material;
        this.subId = subId;
    }

    public Material getMaterial () {
        return this.material;
    }

    public byte getSubId () {
        return this.subId;
    }

    public boolean isLeather () {
        return this.material.getId() >= 298 && this.material.getId() <= 301;
    }

    public boolean hasSubId () {
        return this.subId > 0;
    }

    public ItemStack makeItemStack () {
        if (hasSubId())
            return new ItemStack (this.material, 1, this.subId);
        else
            return new ItemStack (this.material, 1);
    }

    public boolean equals (ItemID itemID) {
        return this.material.equals(itemID.getMaterial()) && this.subId == itemID.getSubId();
    }
}
