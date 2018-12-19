package info.clo5de.asuka.rpg.item;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.powermock.api.mockito.PowerMockito.spy;

public class TestItemExEnchant {

    private static ItemExEnchant itemExEnchant;
    private static HashMap<String, ExEnchant> mockMap;

    @BeforeClass
    public static void setup () {
        mockMap = spy(new HashMap<>());
        itemExEnchant = new ItemExEnchant(mockMap);
    }

    @Test
    public void testToEntrySet () {
        assertThat(itemExEnchant.toEntrySet()).isEqualTo(mockMap.entrySet());
    }

}
