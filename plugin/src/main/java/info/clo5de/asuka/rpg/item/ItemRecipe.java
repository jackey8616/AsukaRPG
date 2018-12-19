package info.clo5de.asuka.rpg.item;

import info.clo5de.asuka.rpg.AsukaRPG;
import info.clo5de.asuka.rpg.exception.ItemConfigException;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class ItemRecipe {

    public static ItemRecipe fromKycConfig (String itemKey, List list) throws Exception {
        if (list != null && list.size() != 0) {
            Ingredient[] ingredients = {
                    Ingredient.AIR(), Ingredient.AIR(), Ingredient.AIR(),
                    Ingredient.AIR(), Ingredient.AIR(), Ingredient.AIR(),
                    Ingredient.AIR(), Ingredient.AIR(), Ingredient.AIR()
            };
            boolean isMultistackRecipe = false;

            for (int i = 0; i < list.size(); ++i) {
                String each = (String) list.get(i);
                if (!each.equalsIgnoreCase("0 0")) {
                    ingredients[i] = Ingredient.fromConfig(each);
                    isMultistackRecipe = ingredients[i].getQuantity() > 1 ? true : isMultistackRecipe;
                }
            }
            return new ItemRecipe(itemKey, ingredients, isMultistackRecipe);
        }
        throw new ItemConfigException(ItemConfigException.Action.READ, ItemConfigException.Stage.ItemRecipe, itemKey);
    }

    private boolean isConverted;
    private boolean isMultistackRecipe;
    private ShapedRecipe recipe;
    private String itemKey;
    private ItemStack itemStack;
    private Ingredient[] ingredients;

    public ItemRecipe(String itemKey, Ingredient[] ingredients, boolean isMultistackRecipe) {
        this.isConverted = false;
        this.isMultistackRecipe = isMultistackRecipe;
        this.itemKey = itemKey;
        this.ingredients = ingredients;
    }

    public void buildItemRecipe (ItemStack itemStack) {
        if (!isConverted) {
            this.itemStack = itemStack;
            this.recipe = new ShapedRecipe(new NamespacedKey(AsukaRPG.INSTANCE, itemKey), itemStack);
            String[] preRecipeShape = {"012", "345", "678"};
            this.recipe.shape(preRecipeShape);

            for (int i = 0; i < ingredients.length; ++i) {
                Ingredient ingredient = ingredients[i];
                this.recipe.setIngredient(Character.forDigit(i, 10), ingredient.getMaterial(),
                        ingredient.getMaterial().getMaxDurability() > 0 && ingredient.getItemID().getSubId() == 0 ?
                                -1 : ingredient.getItemID().getSubId()
                );
            }
            getServer().addRecipe(this.recipe);
            this.isConverted = true;
        }
    }

    public boolean isMultistackRecipe () {
        return this.isMultistackRecipe;
    }

    public boolean isConverted () {
        return this.isConverted;
    }

    public ShapedRecipe getRecipe () {
        return this.recipe;
    }

    public Ingredient getIngredient (int index) {
        return this.ingredients[index];
    }

}
