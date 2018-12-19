package info.clo5de.asuka.rpg.version.v1_11_R1.item;

import net.minecraft.server.v1_11_R1.NBTTagCompound;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class ItemNMSHandler implements info.clo5de.asuka.rpg.nms.ItemNMSHandler {

    public String getItemKeyFromNBT(ItemStack itemStack) {
        net.minecraft.server.v1_11_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(itemStack);
        if (!nmsStack.hasTag())
            return null;
        NBTTagCompound compound = nmsStack.getTag();
        return compound.getString("ItemKey");
    }

    public ItemStack writeItemKeyToItemStackNBT(ItemStack itemStack, String itemKey) {
        net.minecraft.server.v1_11_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound compound = nmsStack.hasTag() ? nmsStack.getTag() : new NBTTagCompound();

        compound.setString("ItemKey", itemKey);

        nmsStack.setTag(compound);
        return CraftItemStack.asBukkitCopy(nmsStack);
    }
}
