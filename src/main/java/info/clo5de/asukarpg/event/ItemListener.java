package info.clo5de.asukarpg.event;

import info.clo5de.asukarpg.AsukaRPG;
import info.clo5de.asukarpg.item.ItemRecipe;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import static org.bukkit.Bukkit.getServer;

public class ItemListener implements Listener {

    private AsukaRPG plugin;

    public ItemListener (AsukaRPG plugin) {
        this.plugin = plugin;
        getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onPrepareCraft (PrepareItemCraftEvent event) {
        for (ItemRecipe itemRecipe : this.plugin.getRecipeHandler().recipes.values()) {
            if (itemRecipe.isMultistackRecipe()) {
                ItemStack result = event.getInventory().getResult();
                if (result != null && itemRecipe.getRecipe().getResult().isSimilar(result)) {
                    result = result.clone();
                    int count = Integer.MAX_VALUE;
                    ItemStack[] crafting = event.getInventory().getMatrix();
                    for (int i = 0; i < crafting.length; ++i) {
                        if (crafting[i] == null)
                            continue;
                        int ingredientAmount = itemRecipe.getIngredient(i).getQuantity();
                        int craftingAmount = crafting[i].getAmount();

                        if (ingredientAmount > craftingAmount) {
                            event.getInventory().setResult(new ItemStack(Material.AIR));
                            return;
                        }
                        int availableAmount = craftingAmount / ingredientAmount;
                        count = count > availableAmount ? availableAmount : count;
                    }
                    result.setAmount(count == Integer.MAX_VALUE ? 1 : count);
                    event.getInventory().setResult(result);
                }
            }
        }
    }

    @EventHandler
    public void onItemCraft (CraftItemEvent event) {
        for (ItemRecipe itemRecipe : this.plugin.getRecipeHandler().recipes.values()) {
            if (itemRecipe.isMultistackRecipe()) {
                ItemStack result = event.getInventory().getResult();
                if (result != null && itemRecipe.getRecipe().getResult().isSimilar(result)) {
                    int amount = result.getAmount();
                    ItemStack[] crafting = event.getInventory().getMatrix();
                    ItemStack[] cloneCrafting = crafting.clone();
                    for (int i = 0; i < crafting.length; ++i) {
                        if (crafting[i] == null)
                            continue;
                        int ingredientAmount = itemRecipe.getIngredient(i).getQuantity();
                        int craftingAmount = cloneCrafting[i].getAmount();
                        cloneCrafting[i].setAmount(craftingAmount - amount * ingredientAmount);
                    }
                    event.getInventory().setMatrix(cloneCrafting);
                    event.getWhoClicked().getInventory().addItem(result);
                }
            }
        }
    }
}
