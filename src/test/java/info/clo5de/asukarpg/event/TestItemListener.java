package info.clo5de.asukarpg.event;

import info.clo5de.asukarpg.AsukaRPG;
import info.clo5de.asukarpg.TestAsukaRPGBuilder;
import info.clo5de.asukarpg.item.Ingredient;
import info.clo5de.asukarpg.item.ItemID;
import info.clo5de.asukarpg.item.ItemRecipe;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemFactory;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;
import java.util.Map;

import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ CraftItemFactory.class, PluginDescriptionFile.class, JavaPluginLoader.class })
@PowerMockIgnore({"javax.management.*"})
public class TestItemListener {

    private static TestAsukaRPGBuilder builder = new TestAsukaRPGBuilder();
    private static AsukaRPG asukaRPG = mock(AsukaRPG.class);
    private static PrepareItemCraftEvent pice = mock(PrepareItemCraftEvent.class);
    private static ItemListener il = new ItemListener(asukaRPG);

    private static Ingredient ing_one, ing_two;
    private static ShapedRecipe shaped = mock(ShapedRecipe.class);
    private static ItemRecipe itemRecipe = mock(ItemRecipe.class);
    private static ItemStack recipeResult = mock(ItemStack.class);
    private static Map<String, ItemRecipe> map;
    private static info.clo5de.asukarpg.recipe.Handler handler = mock(info.clo5de.asukarpg.recipe.Handler.class);

    private static ItemStack craftingResult = mock(ItemStack.class);
    private static ItemStack[] matrix;
    private static CraftingInventory ci = mock(CraftingInventory.class);

    @BeforeClass
    public static void classSetup () throws Exception {
        asukaRPG = builder.getInstance();
        handler = asukaRPG.getRecipeHandler();
    }

    @Before
    public void setup () {
        ing_one = spy(new Ingredient(new ItemID(1, (byte) 0), null, 1));
        ing_two = spy(new Ingredient(new ItemID(1, (byte) 0), null, 2));
        shaped = mock(ShapedRecipe.class);
        itemRecipe = spy(new ItemRecipe("recipe", new Ingredient[] {}, false));
        recipeResult = spy(new ItemStack(Material.STONE));
        map = new HashMap<>();

        craftingResult = spy(new ItemStack(Material.STONE));
        matrix = new ItemStack[9];
        ci = mock(CraftingInventory.class);
        pice = mock(PrepareItemCraftEvent.class);
        il = new ItemListener(asukaRPG);
        // Setup
        //when(recipeResult.isSimilar(Mockito.any())).thenReturn(true);
        when(shaped.getResult()).thenReturn(recipeResult);
        //when(itemRecipe.isMultistackRecipe()).thenReturn(true);
        //when(itemRecipe.getIngredient(0)).thenReturn(ing);
        when(itemRecipe.getRecipe()).thenReturn(shaped);
        map.put("recipe", itemRecipe);
        when(handler.getRecipeMap()).thenReturn(map);
        // Player Action
        //when(craftingResult.clone()).thenReturn(craftingResult);
        matrix[0] = mock(ItemStack.class);
        when(ci.getResult()).thenReturn(craftingResult);
        when(ci.getMatrix()).thenReturn(matrix);
        when(pice.getInventory()).thenReturn(ci);
    }

    @Test
    public void testResultEmpty () {
        itemRecipe = spy(new ItemRecipe("recipe", new Ingredient[] { ing_one }, false));
        map.put("recipe", itemRecipe);
        il.onPrepareCraft(pice);
        map.clear();
        itemRecipe = spy(new ItemRecipe("recipe", new Ingredient[] {  }, false));
        map.put("recipe", itemRecipe);
        il.onPrepareCraft(pice);
    }

    @Test
    public void testResult () {
        itemRecipe = spy(new ItemRecipe("recipe", new Ingredient[] { ing_two, ing_two }, true));
        when(itemRecipe.getRecipe()).thenReturn(shaped);
        map.put("recipe", itemRecipe);

        il.onPrepareCraft(pice);
    }
}
