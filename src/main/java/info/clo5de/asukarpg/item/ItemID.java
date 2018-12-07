package info.clo5de.asukarpg.item;

import com.google.common.primitives.Ints;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemID {

    public static ItemID AIR() {
        return new ItemID(Material.AIR, (byte)0);
    }

    public static ItemID fromConfig (String configString) {
        try {
            String[] idAndSub = configString.split(":");
            String id = idAndSub[0];
            byte subId = idAndSub.length == 2 ? Byte.parseByte(idAndSub[1]) :0;

            if (Ints.tryParse(id) != null) {
                return new ItemID(Integer.valueOf(id), subId);
            } else {
                return new ItemID(id, subId);
            }

        } catch (Exception e) {
            ;
        }
        return null;
    }

    private Material material;
    private byte subId;

    public ItemID (int id, byte subId) {
        this(Material.getMaterial(id), subId);
    }

    public ItemID (String name, byte subId) {
        this(Material.getMaterial(name), subId);
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
            return new ItemStack(this.material);
    }

}
