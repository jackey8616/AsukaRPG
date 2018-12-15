package info.clo5de.asukarpg.item;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ExEnchant.class })
@PowerMockIgnore({"javax.management.*"})
public class TestExEnchant {

    private static Random mockRandom;
    private static ExEnchant ex, ex1, ex2;

    @BeforeClass
    public static void setup () throws Exception {
        mockRandom = spy(new Random());
        whenNew(Random.class).withNoArguments().thenReturn(mockRandom);

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

    //@Test
    public void testIsTriggered () { }

}