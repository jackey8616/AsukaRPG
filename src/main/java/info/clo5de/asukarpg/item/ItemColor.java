package info.clo5de.asukarpg.item;

import org.bukkit.Color;
import org.bukkit.configuration.MemorySection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ItemColor {

    public static ItemColor fromConfig (MemorySection config) {
        byte[] colors = { -128, -128, -128 };
        if (config.contains("Colors")) {
            colors[0] = Byte.parseByte(config.getString("Colors.R"));
            colors[1] = Byte.parseByte(config.getString("Colors.G"));
            colors[2] = Byte.parseByte(config.getString("Colors.B"));
        }
        return new ItemColor (colors);
    }

    public static ItemColor fromKycConfig (MemorySection config) {
        byte[] colors = { -128, -128, -128 };
        String[] idSplit = config.getString("ItemID").split(":");
        if (idSplit.length == 2 && idSplit[1].contains(",")) {
            String[] split = idSplit[1].split(",");

            colors[0] = Byte.parseByte(split[0]);
            colors[1] = Byte.parseByte(split[1]);
            colors[2] = Byte.parseByte(split[2]);
        }
        return new ItemColor (colors);
    }

    private byte[] colors;

    public ItemColor (byte[] colors) {
        this.colors = colors;
    }

    public boolean hasColor () {
        return colors[0] > 0 && colors[1] > 0 && colors[2] > 0;
    }

    public ItemStack applyColor (ItemStack itemStack) {
        LeatherArmorMeta LeatherArmorMeta = (LeatherArmorMeta)itemStack.getItemMeta();
        LeatherArmorMeta.setColor(Color.fromRGB(colors[0], colors[1], colors[2]));
        itemStack.setItemMeta(LeatherArmorMeta);
        return itemStack;
    }

}
