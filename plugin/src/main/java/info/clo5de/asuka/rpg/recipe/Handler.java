package info.clo5de.asuka.rpg.recipe;

import info.clo5de.asuka.rpg.AsukaRPG;
import info.clo5de.asuka.rpg.item.ItemRecipe;
import info.clo5de.asuka.rpg.item.MeowItem;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Handler {

    private AsukaRPG plugin;
    private final File recipeFolder;
    private Map<String, ItemRecipe> recipes = new HashMap<>();

    public Handler (AsukaRPG plugin) {
        this.plugin = plugin;
        this.recipeFolder = new File(plugin.getDataFolder(), "recipe");
    }

    public void loadItemRecipes () {
        for (Map.Entry<String, MeowItem> meowItem : this.plugin.getItemHandler().getItemMap().entrySet())
            if (meowItem.getValue().getItemRecipe() != null)
                this.recipes.put(meowItem.getKey(), meowItem.getValue().getItemRecipe());
    }

    public Map<String, ItemRecipe> getRecipeMap () {
        return this.recipes;
    }

}
