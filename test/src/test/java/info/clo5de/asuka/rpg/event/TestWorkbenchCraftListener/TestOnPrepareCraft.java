package info.clo5de.asuka.rpg.event.TestWorkbenchCraftListener;

import info.clo5de.asuka.rpg.AsukaRPG;
import info.clo5de.asuka.rpg.recipe.Handler;
import info.clo5de.asuka.rpg.TestAsukaRPGBuilder;
import info.clo5de.asuka.rpg.event.WorkbenchCraftingListener;
import info.clo5de.asuka.rpg.item.Ingredient;
import info.clo5de.asuka.rpg.item.ItemID;
import info.clo5de.asuka.rpg.item.ItemRecipe;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemFactory;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ CraftItemFactory.class, PluginDescriptionFile.class, JavaPluginLoader.class })
@PowerMockIgnore({"javax.management.*"})
public class TestOnPrepareCraft {

    private static TestAsukaRPGBuilder builder = new TestAsukaRPGBuilder();
    private static AsukaRPG asukaRPG;
    private static PrepareItemCraftEvent pice;
    private static WorkbenchCraftingListener il;
    private static Map<String, ItemRecipe> map;
    private static Handler handler;

    // Recipe multistack
    private static Ingredient ing_1_1, ing_1_2;
    private static ShapedRecipe shaped;
    private static ItemRecipe itemRecipe;
    private static ItemStack recipeResult;
    // Recipe without multistack
    private static Ingredient ing_1_1_withoutMul, ing_1_2_withoutMul;
    private static ShapedRecipe shapedWithoutMulti;
    private static ItemRecipe itemRecipeWithoutMulti;
    private static ItemStack recipeResultWithoutMulti;

    private static int section = 1;
    private static ItemStack assertResult;
    private static ItemStack craftResult;
    private static ItemStack[] craftMatrix;
    private static CraftingInventory craftInv;

    @BeforeClass
    public static void classSetup () throws Exception {
        System.out.println("<<<<< WorkbenchCraftingListener#onPrepareCraft init test >>>>>");
        asukaRPG = builder.getInstance();
        handler = asukaRPG.getRecipeHandler();
        il = new WorkbenchCraftingListener(asukaRPG);
        pice = mock(PrepareItemCraftEvent.class);
        new TestOnPrepareCraft().resetAll();
    }

    @AfterClass
    public static void classTeardown () {
        System.out.println(">>>>> WorkbenchCraftingListener#onPrepareCraft test done <<<<<");
    }

    @Before
    public void resetAll () {
        // Recipe setting.
        // With Multi
        ing_1_1 = spy(new Ingredient(new ItemID(1, (byte) 1), null, 1));
        ing_1_2 = spy(new Ingredient(new ItemID(1, (byte) 2), null, 2));
        recipeResult = spy(new ItemStack(Material.DIRT));
        when(recipeResult.isSimilar(Mockito.any(ItemStack.class))).thenReturn(true);
        itemRecipe = spy(new ItemRecipe("recipe", new Ingredient[] {ing_1_1, ing_1_2}, true));
        shaped = mock(ShapedRecipe.class);
        when(shaped.getResult()).thenReturn(recipeResult);
        when(itemRecipe.getRecipe()).thenReturn(shaped);
        // Without multi.
        ing_1_1_withoutMul = spy(new Ingredient(new ItemID(1, (byte) 1), null, 1));
        ing_1_2_withoutMul = spy(new Ingredient(new ItemID(1, (byte) 2), null, 1));
        recipeResultWithoutMulti = spy(new ItemStack(Material.DIRT));
        itemRecipeWithoutMulti = spy(new ItemRecipe("recipeWithoutMulti",
                new Ingredient[] {ing_1_1_withoutMul, ing_1_2_withoutMul}, false));
        shapedWithoutMulti = mock(ShapedRecipe.class);
        when(shapedWithoutMulti.getResult()).thenReturn(recipeResultWithoutMulti);
        when(itemRecipeWithoutMulti.getRecipe()).thenReturn(shapedWithoutMulti);
        map = new HashMap<>();
        map.put("recipe", itemRecipe);
        map.put("recipeWithoutMulti", itemRecipeWithoutMulti);
        when(handler.getRecipeMap()).thenReturn(map);
        // Player crafting inventory
        craftInv = mock(CraftingInventory.class);
        when(pice.getInventory()).thenReturn(craftInv);
    }

    @Test
    public void nothingFollowSystemRecipe () {
        // Not thing follow system recipe.
        when(craftInv.getResult()).thenReturn(null);
        il.onPrepareCraft(pice);
        verify(craftInv, Mockito.only()).getResult();
    }

    @Test
    public void followButNoAsukaRecipes () {
        // There is something follow system recipe.
        craftResult = mock(ItemStack.class);
        when(craftInv.getResult()).thenReturn(craftResult);
        // Nothing in Asuka recipes
        when(handler.getRecipeMap()).thenReturn(new HashMap<>());
        il.onPrepareCraft(pice);
        verify(craftInv, Mockito.atMost(2)).getResult();
    }

    @Test
    public void followButNoAsukaRecipesResultFound () {
        // There is something follow system recipe.
        craftResult = spy(new ItemStack(Material.DIRT));
        // Follow system recipe and also Asuka recipe.
        when(craftResult.isSimilar(Mockito.any(ItemStack.class))).thenReturn(false);
        when(craftInv.getResult()).thenReturn(craftResult);
        il.onPrepareCraft(pice);
    }

    @Test
    public void followAndAsukaRecipesResultFound () {
        // There is something follow system recipe.
        craftResult = spy(new ItemStack(Material.DIRT));
        // Follow system recipe and also Asuka recipe.
        when(craftResult.isSimilar(Mockito.any(ItemStack.class))).thenReturn(true);
        when(craftInv.getResult()).thenReturn(craftResult);
        doAnswer((InvocationOnMock invocationOnMock)-> {
            ItemStack is = (ItemStack) invocationOnMock.getArguments()[0];
            if (!is.getType().equals(assertResult.getType()))
                fail("ItemSTack should be " + assertResult + " but returned " + is);
            else if (is.getAmount() != assertResult.getAmount())
                fail("Amount should be " + assertResult.getAmount() + " but returned " + is.getAmount());
            else
                System.out.println("Pass section " + section++);
            return is;
        }).when(craftInv).setResult(Mockito.any(ItemStack.class));
        section = 1;
        // Section 1: Should return same as fake result. (No any crafting input)
        assertResult = recipeResult;
        craftMatrix = new ItemStack[] {
                null, null, null,
                null, null, null,
                null, null, null
        };
        when(craftInv.getMatrix()).thenReturn(craftMatrix);
        il.onPrepareCraft(pice);
        // Section one will not pass a setResult, so I have to manually set section index and print.
        System.out.println("Pass section " + section);
        section++;
        // Section 2: Should return same as fake result. (No any ingredient bigger than crafting)
        assertResult = recipeResult;
        craftMatrix = new ItemStack[] {
                new ItemStack(Material.STONE, 1), null, null,
                null, null, null,
                null, null, null
        };
        when(craftInv.getMatrix()).thenReturn(craftMatrix);
        il.onPrepareCraft(pice);
        // Section 3: Should return AIR. (Second ingredient is bigger than crafting)
        assertResult = spy(new ItemStack(Material.AIR));
        craftMatrix = new ItemStack[] {
                new ItemStack(Material.STONE, 1), new ItemStack(Material.STONE, 1), null,
                null, null, null,
                null, null, null
        };
        when(craftInv.getMatrix()).thenReturn(craftMatrix);
        il.onPrepareCraft(pice);
        // Section 4: Should return AIR. (Second ingredient is bigger than crafting)
        assertResult = spy(new ItemStack(Material.AIR));
        craftMatrix = new ItemStack[] {
                new ItemStack(Material.STONE, 2), new ItemStack(Material.STONE, 1), null,
                null, null, null,
                null, null, null
        };
        when(craftInv.getMatrix()).thenReturn(craftMatrix);
        il.onPrepareCraft(pice);
        // Section 5: Should return same as fake result, all crafting is fit to produce one result.
        assertResult = recipeResult;
        craftMatrix = new ItemStack[] {
                new ItemStack(Material.STONE, 2), new ItemStack(Material.STONE, 2), null,
                null, null, null,
                null, null, null
        };
        when(craftInv.getMatrix()).thenReturn(craftMatrix);
        il.onPrepareCraft(pice);
        // Section 6: Should return same as fake result, all crafting is fit to produce two result.
        assertResult = recipeResult;
        assertResult.setAmount(2);
        craftMatrix = new ItemStack[] {
                new ItemStack(Material.STONE, 2), new ItemStack(Material.STONE, 4), null,
                null, null, null,
                null, null, null
        };
        when(craftInv.getMatrix()).thenReturn(craftMatrix);
        il.onPrepareCraft(pice);
    }

}
