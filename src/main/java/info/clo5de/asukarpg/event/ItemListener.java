package info.clo5de.asukarpg.event;

import info.clo5de.asukarpg.AsukaRPG;
import info.clo5de.asukarpg.item.ItemRecipe;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

public class ItemListener implements Listener {

    private AsukaRPG plugin;

    public ItemListener (AsukaRPG plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPrepareCraft (PrepareItemCraftEvent event) {
        if (event.getInventory().getResult() == null)
            return;
        ItemStack craftingResult = event.getInventory().getResult();

        for (ItemRecipe itemRecipe : this.plugin.getRecipeHandler().getRecipeMap().values()) {
            if (itemRecipe.isMultistackRecipe()) {
                if (craftingResult.isSimilar(itemRecipe.getRecipe().getResult())) {
                    craftingResult = craftingResult.clone();
                    ItemStack[] crafting = event.getInventory().getMatrix();
                    int count = Integer.MAX_VALUE;
                    int ingredientAmount, craftingAmount, availableAmount;

                    for (int i = 0; i < crafting.length; ++i) {
                        if (crafting[i] == null)
                            continue;
                        ingredientAmount = itemRecipe.getIngredient(i).getQuantity();
                        craftingAmount = crafting[i].getAmount();

                        if (ingredientAmount > craftingAmount) {
                            event.getInventory().setResult(new ItemStack(Material.AIR));
                            return;
                        }
                        availableAmount = craftingAmount / ingredientAmount;
                        count = count > availableAmount ? availableAmount : count;
                    }
                    craftingResult.setAmount(count == Integer.MAX_VALUE ? 1 : count);
                    event.getInventory().setResult(craftingResult);
                }
                return;
            }
        }
    }

    @EventHandler
    public void onItemCraft (CraftItemEvent event) {
        if (event.getInventory().getResult() == null)
            return;
        ItemStack result = event.getInventory().getResult();

        for (ItemRecipe itemRecipe : this.plugin.getRecipeHandler().getRecipeMap().values()) {
            if (itemRecipe.isMultistackRecipe()) {
                if (result.isSimilar(itemRecipe.getRecipe().getResult())) {
                    int amount = result.getAmount();
                    ItemStack[] crafting = event.getInventory().getMatrix();
                    ItemStack[] cloneCrafting = crafting.clone();
                    int ingredientAmount, craftingAmount;

                    for (int i = 0; i < crafting.length; ++i) {
                        if (crafting[i] == null)
                            continue;
                        ingredientAmount = itemRecipe.getIngredient(i).getQuantity();
                        craftingAmount = cloneCrafting[i].getAmount();
                        cloneCrafting[i].setAmount(craftingAmount - amount * ingredientAmount);
                    }
                    event.getInventory().setMatrix(cloneCrafting);
                    event.getWhoClicked().getInventory().addItem(result);
                }
                return;
            }
        }
    }
}
