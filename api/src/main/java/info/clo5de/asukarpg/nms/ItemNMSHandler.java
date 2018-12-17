package info.clo5de.asukarpg.nms;

import org.bukkit.inventory.ItemStack;

public interface ItemNMSHandler {

    String getItemKeyFromNBT(ItemStack itemStack);

    ItemStack writeItemKeyToItemStackNBT(ItemStack itemStack, String itemKey);

}
