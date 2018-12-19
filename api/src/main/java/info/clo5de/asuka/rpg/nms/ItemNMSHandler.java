package info.clo5de.asuka.rpg.nms;

import org.bukkit.inventory.ItemStack;

public interface ItemNMSHandler {

    String getItemKeyFromNBT(ItemStack itemStack);

    ItemStack writeItemKeyToItemStackNBT(ItemStack itemStack, String itemKey);

}
