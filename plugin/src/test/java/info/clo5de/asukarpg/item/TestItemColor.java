package info.clo5de.asukarpg.item;

import org.bukkit.Color;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.configuration.MemorySection;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class TestItemColor {

    private static ItemColor kycColor;

    @BeforeClass
    public static void setupBeforeClass () throws Exception {
        kycColor = new ItemColor(new byte[] { 127, 127, 127});
    }

    @Test
    public void testFromConfig () {
        MemorySection config = new MemoryConfiguration();
        assertThat(ItemColor.fromConfig(config)).isEqualTo(null);
        assertThat(ItemColor.fromKycConfig(config)).isEqualTo(null);
        config.set("ItemID", "leather_helmet");
        assertThat(ItemColor.fromKycConfig(config)).isEqualTo(null);
        config.set("ItemID", "leather_helmet:sub_id");
        assertThat(ItemColor.fromKycConfig(config)).isEqualTo(null);

        config.set("ItemID", "leather_helmet:255,255,255");
        config.set("Colors.R", (byte) 123);
        config.set("Colors.G", (byte) 123);
        config.set("Colors.B", (byte) 123);
        assertThat(ItemColor.fromConfig(config)).isNotEqualTo(null);
        assertThat(ItemColor.fromKycConfig(config)).isNotEqualTo(null);
    }

    @Test
    public void testToColor () {
        assertThat(kycColor.toColor()).isEqualTo(Color.fromRGB(255, 255, 255));
        assertThat(kycColor.toColor()).isNotEqualTo(Color.fromRGB(0, 0, 0));
    }

    @Test
    public void testToDyeColor () {
        assertThat(kycColor.toDyeColor()).isNull();
    }

    @Test
    public void testApplyColor () {
        LeatherArmorMeta lam = mock(LeatherArmorMeta.class);
        when(lam.getColor()).thenReturn(kycColor.toColor());
        kycColor.applyColor(lam);
        assertThat(lam.getColor()).isEqualTo(kycColor.toColor());

        BannerMeta bm = mock(BannerMeta.class);
        when(bm.getBaseColor()).thenReturn(kycColor.toDyeColor());
        kycColor.applyColor(bm);
        assertThat(bm.getBaseColor()).isEqualTo(kycColor.toDyeColor());
    }
}
