package info.clo5de.asukarpg.item;

import info.clo5de.asukarpg.AsukaRPG;
import info.clo5de.asukarpg.utils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;

public class TestExEnchant {

    private static Random mockRandom;
    private static ExEnchant ex, ex1, ex2;

    @BeforeClass
    public static void setup () throws Exception {
        mockRandom = spy(new Random());
        utils.setFinalStatic(AsukaRPG.class.getField("random"), mockRandom);

        new TestExEnchant().resetAll();
    }

    @Before
    public void resetAll () {
        ex = new ExEnchant("TEST1", 0);
        ex1 = new ExEnchant("TEST2", 3, 4);
        ex2 = new ExEnchant("TEST3", 0.0005, 0.0004);
    }

    @Test
    public void testGetName () {
        assertThat(ex.getName().equals("TEST1")).isTrue();
        assertThat(ex1.getName().equals("TEST2")).isTrue();
        assertThat(ex2.getName().equals("TEST3")).isTrue();
    }

    @Test
    public void testGetAbility () {
        assertThat(ex.getAbility()).isEqualTo(0);
        assertThat(ex1.getAbility()).isEqualTo(0.03);
        assertThat(ex2.getAbility()).isEqualTo(0.0005);
    }

    @Test
    public void testSetAbility () {
        ex.setAbility(5.0D);
        assertThat(ex.getAbility()).isEqualTo(5.0D);
    }

    @Test
    public void testGetEffect () {
        assertThat(ex.getEffect()).isEqualTo(0);
        assertThat(ex1.getEffect()).isEqualTo(0.04);
        assertThat(ex2.getEffect()).isEqualTo(0.0004);
    }

    @Test
    public void testSetEffect () {
        ex1.setEffect(6.0D);
        assertThat(ex1.getEffect()).isEqualTo(6.0D);
    }

    @Test
    public void testIsTriggered () {
        ex1.setAbility(0.5D);
        when(mockRandom.nextDouble()).thenReturn(0.4D);
        assertThat(ex1.isTriggered()).isTrue();

        when(mockRandom.nextDouble()).thenReturn(0.6D);
        assertThat(ex1.isTriggered()).isFalse();
    }

}