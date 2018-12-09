package info.clo5de.asukarpg.event.TestItemListener;

import info.clo5de.asukarpg.AsukaRPG;
import info.clo5de.asukarpg.TestAsukaRPGBuilder;
import info.clo5de.asukarpg.event.ItemListener;
import info.clo5de.asukarpg.item.Ingredient;
import info.clo5de.asukarpg.item.ItemID;
import info.clo5de.asukarpg.item.ItemRecipe;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemFactory;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
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
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ CraftItemFactory.class, PluginDescriptionFile.class, JavaPluginLoader.class })
public class TestOnItemCraft {

    private static TestAsukaRPGBuilder builder = new TestAsukaRPGBuilder();
    private static AsukaRPG asukaRPG;
    private static CraftItemEvent cie;
    private static ItemListener il;
    private static Map<String, ItemRecipe> map;
    private static info.clo5de.asukarpg.recipe.Handler handler;

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
    private static ItemStack craftResult;
    private static ItemStack[] craftMatrix;
    private static ItemStack[] assertMatrix;
    private static CraftingInventory craftInv;
    private static HumanEntity clicker;
    private static PlayerInventory lookingInv;

    @BeforeClass
    public static void classSetup () throws Exception {
        System.out.println("<<<<< ItemListener#onPrepareCraft init test >>>>>");
        asukaRPG = builder.getInstance();
        handler = asukaRPG.getRecipeHandler();
        il = new ItemListener(asukaRPG);
        cie = mock(CraftItemEvent.class);
        new TestOnItemCraft().resetAll();
    }

    @AfterClass
    public static void classTeardown () {
        System.out.println(">>>>> ItemListener#onItemCraft test done <<<<<");
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
        // Player
        lookingInv = mock(PlayerInventory.class);
        clicker = mock(HumanEntity.class);
        when(clicker.getInventory()).thenReturn(lookingInv);
        when(cie.getWhoClicked()).thenReturn(clicker);
        craftInv = mock(CraftingInventory.class);
        when(cie.getInventory()).thenReturn(craftInv);
    }

    @Test
    public void nothingFollowSystemRecipe () {
        // Not thing follow system recipe.
        when(craftInv.getResult()).thenReturn(null);
        il.onItemCraft(cie);
        verify(craftInv, Mockito.only()).getResult();
    }

    @Test
    public void followButNoAsukaRecipes () {
        // There is something follow system recipe.
        craftResult = mock(ItemStack.class);
        when(craftInv.getResult()).thenReturn(craftResult);
        // Nothing in Asuka recipes
        when(handler.getRecipeMap()).thenReturn(new HashMap<>());
        il.onItemCraft(cie);
        verify(craftInv, Mockito.atMost(2)).getResult();
    }

    @Test
    public void followButNoAsukaRecipesResultFound () {
        // There is something follow system recipe.
        craftResult = spy(new ItemStack(Material.DIRT));
        // Follow system recipe and also Asuka recipe.
        when(craftResult.isSimilar(Mockito.any(ItemStack.class))).thenReturn(false);
        when(craftInv.getResult()).thenReturn(craftResult);
        il.onItemCraft(cie);
    }

    @Test
    public void followAndAsukaRecipesResultFound () {
        // There is something follow system recipe.
        craftResult = spy(new ItemStack(Material.DIRT));
        // Follow system recipe and also Asuka recipe.
        when(craftResult.isSimilar(Mockito.any(ItemStack.class))).thenReturn(true);
        when(craftInv.getResult()).thenReturn(craftResult);
        doAnswer((InvocationOnMock invocationOnMock) -> {
            ItemStack[] matrix = (ItemStack[]) invocationOnMock.getArguments()[0];
            for (int i = 0; i < matrix.length; ++i)
                if (matrix[i] != assertMatrix[i])
                    if ((matrix[i] == null && assertMatrix[i] != null) || (matrix[i] != null && assertMatrix[i] == null))
                        fail("Index " + i + " should be " + assertMatrix[i] + ", but it is " + matrix[i]);
                    else if(!matrix[i].getType().equals(assertMatrix[i].getType()))
                        fail("Index " + i + " itemstack should be " +
                                assertMatrix[i].getType() + ", but it's " + matrix[i].getType());
                    else if (matrix[i].getAmount() != assertMatrix[i].getAmount())
                        fail("Index " + i + " amount should be " +
                                assertMatrix[i].getAmount() + ", but it's " + matrix[i].getAmount());
            System.out.println("Pass section " + section++);
            return matrix;
        }).when(craftInv).setMatrix(Mockito.any(ItemStack[].class));

        // Section 1: Should return same as fake result. (No any crafting input to reduce.)
        craftMatrix = new ItemStack[] {
                null, null, null,
                null, null, null,
                null, null, null
        };
        assertMatrix = craftMatrix.clone();
        when(craftResult.getAmount()).thenReturn(1);
        when(craftInv.getMatrix()).thenReturn(craftMatrix);
        il.onItemCraft(cie);
        // Section 2: Should return same as fake result. (No any ingredient bigger than crafting)
        craftMatrix = new ItemStack[] {
                new ItemStack(Material.STONE, 1), null, null,
                null, null, null,
                null, null, null
        };
        assertMatrix = craftMatrix.clone();
        when(craftResult.getAmount()).thenReturn(1);
        when(craftInv.getMatrix()).thenReturn(craftMatrix);
        il.onItemCraft(cie);
        // Section 3: Should return AIR. (Second ingredient is bigger than crafting)
        craftMatrix = new ItemStack[] {
                new ItemStack(Material.STONE, 1), new ItemStack(Material.STONE, 1), null,
                null, null, null,
                null, null, null
        };
        assertMatrix = craftMatrix.clone();
        when(craftResult.getAmount()).thenReturn(0);
        when(craftInv.getMatrix()).thenReturn(craftMatrix);
        il.onItemCraft(cie);
        // Section 4: Should return AIR. (Second ingredient is bigger than crafting)
        craftMatrix = new ItemStack[] {
                new ItemStack(Material.STONE, 2), new ItemStack(Material.STONE, 1), null,
                null, null, null,
                null, null, null
        };
        assertMatrix = craftMatrix.clone();
        when(craftResult.getAmount()).thenReturn(0);
        when(craftInv.getMatrix()).thenReturn(craftMatrix);
        il.onItemCraft(cie);
        // Section 5: Should return same as fake result, all crafting is fit to produce one result.
        craftMatrix = new ItemStack[] {
                new ItemStack(Material.STONE, 2), new ItemStack(Material.STONE, 2), null,
                null, null, null,
                null, null, null
        };
        assertMatrix = new ItemStack[] {
                new ItemStack(Material.STONE, 1), new ItemStack(Material.STONE, 0), null,
                null, null, null,
                null, null, null
        };
        when(craftResult.getAmount()).thenReturn(1);
        when(craftInv.getMatrix()).thenReturn(craftMatrix);
        il.onItemCraft(cie);
        // Section 6: Should return same as fake result, all crafting is fit to produce two result.
        craftMatrix = new ItemStack[] {
                new ItemStack(Material.STONE, 2), new ItemStack(Material.STONE, 4), null,
                null, null, null,
                null, null, null
        };
        assertMatrix = new ItemStack[] {
                new ItemStack(Material.STONE, 0), new ItemStack(Material.STONE, 0), null,
                null, null, null,
                null, null, null
        };
        when(craftResult.getAmount()).thenReturn(2);
        when(craftInv.getMatrix()).thenReturn(craftMatrix);
        il.onItemCraft(cie);
    }

}
