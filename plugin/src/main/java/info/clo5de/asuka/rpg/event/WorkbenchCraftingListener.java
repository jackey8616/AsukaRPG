package info.clo5de.asuka.rpg.event;

import info.clo5de.asuka.rpg.AsukaRPG;
import info.clo5de.asuka.rpg.item.ItemRecipe;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

public class WorkbenchCraftingListener implements Listener {

    private AsukaRPG plugin;

    public WorkbenchCraftingListener(AsukaRPG plugin) {
        this.plugin = plugin;
    }

    /**
     * This is the event responsible for caculcate the recipe and it's amount of result itemstack.
     * @param inventory
     */
    private void calculateCraftingResult (CraftingInventory inventory) {
        ItemStack craftingResult = inventory.getResult();
        if (craftingResult == null)
            return;

        for (ItemRecipe itemRecipe : this.plugin.getRecipeHandler().getRecipeMap().values()) {
            if (itemRecipe.isMultistackRecipe()) {
                if (craftingResult.isSimilar(itemRecipe.getRecipe().getResult())) {
                    craftingResult = craftingResult.clone();
                    ItemStack[] crafting = inventory.getMatrix();
                    int count = Integer.MAX_VALUE;
                    int ingredientAmount, craftingAmount, availableAmount;

                    for (int i = 0; i < crafting.length; ++i) {
                        if (crafting[i] == null)
                            continue;
                        ingredientAmount = itemRecipe.getIngredient(i).getQuantity();
                        craftingAmount = crafting[i].getAmount();

                        if (ingredientAmount > craftingAmount) {
                            inventory.setResult(new ItemStack(Material.AIR));
                            return;
                        }
                        availableAmount = craftingAmount / ingredientAmount;
                        count = count > availableAmount ? availableAmount : count;
                    }
                    craftingResult.setAmount(count == Integer.MAX_VALUE ? 1 : count);
                    inventory.setResult(craftingResult);
                }
                return;
            }
        }
    }

    /**
     * This event is called when crafting with left click or
     * right click put into empty slot.
     * <b>ATTENTION</b> right click with slot already have itemstack would not trigger this event.
     * Please check onRightClickWorkbench event.
     *
     * This modified correct amount of result item.
     * @see WorkbenchCraftingListener#onRightClickWorkbench(InventoryClickEvent)
     * @param event: PrepareItemCraftEvent
     */
    @EventHandler
    public void onPrepareCraft (PrepareItemCraftEvent event) {
        CraftingInventory inventory = event.getInventory();
        calculateCraftingResult(inventory);
    }

    /**
     * This is a event process any right click in Workbench slots which contains itemstacks.
     * The methods is same as onPrepareCraft, but add a scheduler to perform post event.
     *
     * @see WorkbenchCraftingListener#onPrepareCraft(PrepareItemCraftEvent)
     * @param event: InventoryClickEvent
     */
    @EventHandler
    public void onRightClickWorkbench (InventoryClickEvent event) {
        if (!(event.getClickedInventory() instanceof CraftingInventory))
            return;
        Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, () -> {
            Player player = (Player) event.getWhoClicked();
            CraftingInventory inventory = (CraftingInventory) event.getClickedInventory();
            calculateCraftingResult(inventory);
            player.updateInventory();
        }, 1L);
    }

    /**
     * This event is called when player take down the crafting result.
     *
     * This modified the left item in crafting matrix base on result item's amount set by onPrepareCraft
     * @see WorkbenchCraftingListener#onPrepareCraft (PrepareItemCraftEvent event)
     * @param event: CraftItemEvent
     */
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
