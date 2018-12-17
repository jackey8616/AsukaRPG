package info.clo5de.asukarpg.item;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.configuration.MemorySection;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ItemColor {

    public static ItemColor fromConfig (MemorySection config) {
        if (config.contains("Colors")) {
            byte[] colors = { -128, -128, -128 };
            colors[0] = Byte.parseByte(config.getString("Colors.R"));
            colors[1] = Byte.parseByte(config.getString("Colors.G"));
            colors[2] = Byte.parseByte(config.getString("Colors.B"));
            return new ItemColor (colors);
        }
        return null;
    }

    public static ItemColor fromKycConfig (MemorySection config) {
        if (config.contains("ItemID")) {
            String[] idSplit = config.getString("ItemID").split(":");
            if (idSplit.length == 2 && idSplit[1].contains(",")) {
                String[] split = idSplit[1].split(",");
                byte[] colors = {-128, -128, -128};

                colors[0] = Byte.valueOf(Integer.toString(Integer.parseInt(split[0]) - 128));
                colors[1] = Byte.valueOf(Integer.toString(Integer.parseInt(split[1]) - 128));
                colors[2] = Byte.valueOf(Integer.toString(Integer.parseInt(split[2]) - 128));
                return new ItemColor(colors);
            }
        }
        return null;
    }

    private byte[] colors;

    public ItemColor (byte[] colors) {
        this.colors = colors;
    }

    public Color toColor () {
        return Color.fromRGB(colors[0] + 128, colors[1] + 128, colors[2] + 128);
    }

    public DyeColor toDyeColor () {
        return DyeColor.getByColor(this.toColor());
    }

    public ItemMeta applyColor (ItemMeta itemMeta) {
        if (itemMeta instanceof LeatherArmorMeta)
            ((LeatherArmorMeta) itemMeta).setColor(toColor());
        else if (itemMeta instanceof BannerMeta)
            ((BannerMeta) itemMeta).setBaseColor(toDyeColor());
        return itemMeta;
    }

}
