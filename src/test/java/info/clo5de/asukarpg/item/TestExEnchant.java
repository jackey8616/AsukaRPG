package info.clo5de.asukarpg.item;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestExEnchant {

    private static ExEnchant ex, ex1;

    @BeforeClass
    public static void setup () {
        ex = new ExEnchant("TEST1", 0);
        ex1 = new ExEnchant("TEST2", 3, 4);
    }

    @Test
    public void testGetName () {
        assertThat(ex.getName().equals("TEST1")).isTrue();
        assertThat(ex1.getName().equals("TEST2")).isTrue();
    }

    @Test
    public void testGetAbility () {
        assertThat(ex.getAbility()).isEqualTo(0);
        assertThat(ex1.getAbility()).isEqualTo(3);
    }

    @Test
    public void testGetEffect () {
        assertThat(ex.getEffect()).isEqualTo(0);
        assertThat(ex1.getEffect()).isEqualTo(4);
    }

}