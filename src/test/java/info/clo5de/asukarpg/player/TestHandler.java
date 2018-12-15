package info.clo5de.asukarpg.player;

import info.clo5de.asukarpg.AsukaRPG;
import org.bukkit.entity.Player;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Handler.class })
@PowerMockIgnore({"javax.management.*"})
public class TestHandler {

    private static AsukaRPG asukaRPG;
    private static Handler playerHandler;
    private static HashMap<Player, AsukaPlayer> spyMap;

    private static Player player;
    private static AsukaPlayer asukaPlayer;

    @BeforeClass
    public static void setup () throws Exception {
        asukaRPG = mock(AsukaRPG.class);

        spyMap = spy(new HashMap<>());
        player = mock(Player.class);
        asukaPlayer = mock(AsukaPlayer.class);
        spyMap.put(player, asukaPlayer);

        whenNew(HashMap.class).withNoArguments().thenReturn(spyMap);
        playerHandler = new Handler(asukaRPG);
    }

    @Test
    public void testGetPlayerMap () {
        assertThat(playerHandler.getPlayerMap()).isEqualTo(spyMap);
    }

    @Test
    public void testGetPlayer () {
        assertThat(playerHandler.getPlayer(player)).isEqualTo(asukaPlayer);
    }

    @Test
    public void testContainsPlayer () {
        assertThat(playerHandler.containsPlayer(player)).isTrue();
        assertThat(playerHandler.containsPlayer(mock(Player.class))).isFalse();
    }

}
