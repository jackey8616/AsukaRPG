package info.clo5de.asukarpg.recipes;

import info.clo5de.asukarpg.AsukaRPG;
import info.clo5de.asukarpg.item.ItemRecipe;
import info.clo5de.asukarpg.item.MeowItem;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Handler {

    private AsukaRPG plugin;
    private final File recipeFolder;
    public Map<String, ItemRecipe> recipes = new HashMap<>();

    public Handler (AsukaRPG plugin) {
        this.recipeFolder = new File(plugin.getDataFolder(), "recipe");
        this.plugin = plugin;
    }

    public void loadItemRecipes () {
        for (Map.Entry<String, MeowItem> meowItem : this.plugin.itemHandler.items.entrySet())
            if (meowItem.getValue().getItemRecipe() != null)
                this.recipes.put(meowItem.getKey(), meowItem.getValue().getItemRecipe());
    }

}
