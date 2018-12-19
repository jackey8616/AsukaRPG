package info.clo5de.asuka.rpg.event;

import info.clo5de.asuka.rpg.AsukaRPG;
import info.clo5de.asuka.rpg.item.ExEnchant;
import info.clo5de.asuka.rpg.player.Handler;
import info.clo5de.asuka.rpg.player.AsukaPlayer;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import java.util.Map;

public class ExEnchantListener implements Listener {

    private AsukaRPG plugin;
    private Handler playerHandler;

    public ExEnchantListener (AsukaRPG plugin) {
        this.plugin = plugin;
        this.playerHandler = this.plugin.getPlayerHandler();
    }

    @EventHandler
    public void onPlayerDamage (EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof CraftPlayer) {
            // Player attacked other.
            AsukaPlayer attacker = this.playerHandler.getPlayer(event.getDamager().getUniqueId());
            if (attacker == null)
                return;
            onAttack(attacker, event);
        }
        if (event.getEntity() instanceof CraftPlayer) {
            // Player got damaged.
            AsukaPlayer defender = this.playerHandler.getPlayer(event.getEntity().getUniqueId());
            if (defender == null)
                return;
            onDefense(defender, event);
        }

    }

    public void onAttack (AsukaPlayer attacker, EntityDamageByEntityEvent event) {
        LivingEntity defender = (LivingEntity) event.getEntity();
        Map<String, ExEnchant> map = attacker.getExEnchantMap();

        this.healWhenDamage(attacker.getVanillaPlayer(), defender, event.getDamage(), map);
        this.flyyyyyyyyyyyyyyyyyyyyyyyOut(defender, map);

    }

    public void onDefense (AsukaPlayer defender, EntityDamageByEntityEvent event) {
        LivingEntity attacker = (LivingEntity) event.getDamager();
        Map<String, ExEnchant> map = defender.getExEnchantMap();
        double damage = event.getDamage();

        this.reduceWhenDefense(event, damage, map);
        this.healWhenDamage(attacker, defender.getVanillaPlayer(), damage, map);
        this.reboundWhenDefense(attacker, damage, map);
        this.removalWhenDefense(event, damage, map);
    }

    public void healWhenDamage (LivingEntity attacker, LivingEntity defender, double damage,
                                 Map<String, ExEnchant> map) {
        double attackerHealth = attacker.getHealth();
        double victimHealth = defender.getHealth();

        if (map.containsKey("RESTORED_HP_HIT") && map.get("RESTORED_HP_HIT").isTriggered()) {
            attackerHealth += map.get("RESTORED_HP_HIT").getEffect();
        } else if (map.containsKey("RESTORED_HP_P_HIT") && map.get("RESTORED_HP_P_HIT").isTriggered()) {
            attackerHealth *= (map.get("RESTORED_HP_P_HIT").getEffect() + 1.0D);
        } else if (map.containsKey("RESTORED_HP_VAMPIRE") && map.get("RESTORED_HP_VAMPIRE").isTriggered()) {
            double effectValue = damage * map.get("RESTORED_HP_VAMPIRE").getEffect();
            attackerHealth += effectValue;
            victimHealth -= effectValue;
            defender.setHealth(victimHealth > defender.getMaxHealth() ? defender.getMaxHealth() : victimHealth);
        } else {
            return;
        }
        attacker.setHealth(attackerHealth > attacker.getMaxHealth() ? attacker.getMaxHealth() : attackerHealth);
    }

    public void flyyyyyyyyyyyyyyyyyyyyyyyOut (LivingEntity defender, Map<String, ExEnchant> map) {
        if (map.containsKey("HIT_FLY_HIT") && map.get("HIT_FLY_HIT").isTriggered())
            defender.setVelocity(new Vector(0.0F, 0.75F, 0.0F));
        return;
    }

    public void reduceWhenDefense (EntityDamageByEntityEvent event, double damage, Map<String, ExEnchant> map) {
        if (map.containsKey("REDUCE_DAMAGE") && map.get("REDUCE_DAMAGE").isTriggered()) {
            damage -= map.get("REDUCE_DAMAGE").getEffect();
        } else if (map.containsKey("REDUCE_DAMAGE_P") && map.get("REDUCE_DAMAGE_P").isTriggered()) {
            damage *= (1.0D - map.get("REDUCE_DAMAGE_P").getEffect());
        } else {
            return;
        }
        event.setDamage(damage);
    }

    public void reboundWhenDefense (LivingEntity attacker, double damage, Map<String, ExEnchant> map) {
        if (map.containsKey("REBOUND_DAMAGE") && map.get("REBOUND_DAMAGE").isTriggered()) {
            attacker.damage(map.get("REBOUND_DAMAGE").getEffect());
        } else if (map.containsKey("REBOUND_DAMAGE_P") && map.get("REBOUND_DAMAGE_P").isTriggered()) {
            attacker.damage(map.get("REBOUND_DAMAGE_P").getEffect() * damage);
        }
    }

    public void removalWhenDefense (EntityDamageByEntityEvent event, double damage, Map<String, ExEnchant> map) {
        if (map.containsKey("REMOVAL_DAMAGE") && map.get("REMOVAL_DAMAGE").isTriggered()) {
            event.setDamage(0.0D);
        }
    }
}
