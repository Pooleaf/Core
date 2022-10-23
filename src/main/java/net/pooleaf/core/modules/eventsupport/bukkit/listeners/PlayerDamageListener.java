package net.pooleaf.core.modules.eventsupport.bukkit.listeners;

import net.pooleaf.core.modules.eventsupport.bukkit.events.damage.EntityDamageByPlayerEvent;
import net.pooleaf.core.modules.eventsupport.bukkit.events.damage.PlayerDamageByEntityEvent;
import net.pooleaf.core.modules.eventsupport.bukkit.events.damage.PlayerDamageByPlayerEvent;
import net.pooleaf.core.modules.eventsupport.bukkit.events.damage.PlayerDamageEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamageListener implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        // 플레이어인지 확인
        if (!(e.getEntity() instanceof Player)) return;

        PlayerDamageEvent event = new PlayerDamageEvent(e);
        Bukkit.getPluginManager().callEvent(event);
        e.setCancelled(event.isCancelled());
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        boolean damagedIsPlayer = e.getEntity() instanceof Player; // 데미지를 받은 엔티티가 플레이어인지 확인
        boolean damagerIsPlayer = e.getDamager() instanceof Player // 데미지를 준 엔티티가 플레이어인지 확인
                || (e.getDamager() instanceof Projectile && ((Projectile) e.getDamager()).getShooter() instanceof Player); // 발사체일 경우, 발사한 엔티티가 플레이어인지 확인

        if (damagedIsPlayer) {
            PlayerDamageByEntityEvent event = new PlayerDamageByEntityEvent(e);
            event.setCancelled(e.isCancelled());
            Bukkit.getPluginManager().callEvent(event);
            e.setCancelled(event.isCancelled());
        }

        if (damagerIsPlayer) {
            EntityDamageByPlayerEvent event = new EntityDamageByPlayerEvent(e);
            event.setCancelled(e.isCancelled());
            Bukkit.getPluginManager().callEvent(event);
            e.setCancelled(event.isCancelled());
        }

        if (damagedIsPlayer && damagerIsPlayer) {
            PlayerDamageByPlayerEvent event = new PlayerDamageByPlayerEvent(e);
            event.setCancelled(e.isCancelled());
            Bukkit.getPluginManager().callEvent(event);
            e.setCancelled(event.isCancelled());
        }
    }

}