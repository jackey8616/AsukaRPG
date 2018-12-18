package info.clo5de.asukarpg.event.TestExEnchantListener;

import info.clo5de.asukarpg.AsukaRPG;
import info.clo5de.asukarpg.event.ExEnchantListener;
import info.clo5de.asukarpg.player.AsukaPlayer;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

public class TestOnPlayerDamage {

    private static AsukaRPG asukaRPG;
    private static ExEnchantListener eel;
    private static info.clo5de.asukarpg.player.Handler ph;

    private static EntityDamageByEntityEvent edbee;
    private static AsukaPlayer mockAsukaPlayer;

    @BeforeClass
    public static void setup () {
        asukaRPG = mock(AsukaRPG.class);

        new TestOnPlayerDamage().resetAll(mock(AsukaPlayer.class));
    }

    public void resetAll (AsukaPlayer asukaPlayer) {
        eel = spy(new ExEnchantListener(asukaRPG));
        ph = mock(info.clo5de.asukarpg.player.Handler.class);
        edbee = mock(EntityDamageByEntityEvent.class);

        mockAsukaPlayer = asukaPlayer;
        when(ph.getPlayer(Mockito.any(UUID.class))).thenReturn(mockAsukaPlayer);

        when(asukaRPG.getPlayerHandler()).thenReturn(ph);

        doNothing().when(eel).onAttack(Mockito.any(AsukaPlayer.class), Mockito.any(EntityDamageByEntityEvent.class));
        doNothing().when(eel).onDefense(Mockito.any(AsukaPlayer.class), Mockito.any(EntityDamageByEntityEvent.class));
    }

    @Test
    public void testAttackerAsukaPlayer () {
        //resetAll(mock(AsukaPlayer.class));
        //when(edbee.getDamager()).thenReturn(mock(CraftPlayer.class));
        //eel.onPlayerDamage(edbee);
        //verify(eel, Mockito.times(1)).onAttack(Mockito.any(AsukaPlayer.class), Mockito.any(EntityDamageByEntityEvent.class));

        resetAll(null);
        when(edbee.getDamager()).thenReturn(null);
        eel.onPlayerDamage(edbee);
        verify(eel, Mockito.never()).onAttack(Mockito.any(AsukaPlayer.class), Mockito.any(EntityDamageByEntityEvent.class));

        resetAll(mock(AsukaPlayer.class));
        when(edbee.getDamager()).thenReturn(mock(CraftPlayer.class));
        eel.onPlayerDamage(edbee);
        verify(eel, Mockito.never()).onAttack(Mockito.any(AsukaPlayer.class), Mockito.any(EntityDamageByEntityEvent.class));

        resetAll(null);
        when(edbee.getDamager()).thenReturn(null);
        eel.onPlayerDamage(edbee);
        verify(eel, Mockito.never()).onAttack(Mockito.any(AsukaPlayer.class), Mockito.any(EntityDamageByEntityEvent.class));
    }

    @Test
    public void testDefenderAsukaPlayer () {

        //resetAll(mock(AsukaPlayer.class));
        //when(edbee.getEntity()).thenReturn(mock(CraftPlayer.class));
        //eel.onPlayerDamage(edbee);
        //verify(eel, Mockito.times(1)).onDefense(Mockito.any(AsukaPlayer.class), Mockito.any(EntityDamageByEntityEvent.class));

        resetAll(null);
        when(edbee.getEntity()).thenReturn(null);
        eel.onPlayerDamage(edbee);
        verify(eel, Mockito.never()).onDefense(Mockito.any(AsukaPlayer.class), Mockito.any(EntityDamageByEntityEvent.class));

        resetAll(mock(AsukaPlayer.class));
        when(edbee.getEntity()).thenReturn(mock(CraftPlayer.class));
        eel.onPlayerDamage(edbee);
        verify(eel, Mockito.never()).onDefense(Mockito.any(AsukaPlayer.class), Mockito.any(EntityDamageByEntityEvent.class));

        resetAll(null);
        when(edbee.getEntity()).thenReturn(null);
        eel.onPlayerDamage(edbee);
        verify(eel, Mockito.never()).onDefense(Mockito.any(AsukaPlayer.class), Mockito.any(EntityDamageByEntityEvent.class));
    }

}
