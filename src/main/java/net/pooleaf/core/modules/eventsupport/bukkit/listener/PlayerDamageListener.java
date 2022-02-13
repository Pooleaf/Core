package net.pooleaf.core.modules.eventsupport.bukkit.listener;

import net.pooleaf.core.modules.eventsupport.bukkit.event.damage.PlayerDamageByEntityEvent;
import net.pooleaf.core.modules.eventsupport.bukkit.event.damage.PlayerDamageByPlayerEvent;
import net.pooleaf.core.modules.eventsupport.bukkit.event.damage.PlayerDamageEvent;
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
    }

    @EventHandler
    public void onPlayerDamageByEntity(EntityDamageByEntityEvent e) {
        // 플레이어인지 확인
        if (!(e.getEntity() instanceof Player)) return;

        PlayerDamageByEntityEvent event = new PlayerDamageByEntityEvent(e);
        Bukkit.getPluginManager().callEvent(event);
    }

    @EventHandler
    public void onPlayerDamageByPlayer(EntityDamageByEntityEvent e) {
        // 데미지를 받은 엔티티가 플레이어인지 확인
        if (!(e.getEntity() instanceof Player)) return;

        // 발사체일 경우, 발사한 엔티티가 플레이어인지 확인
        else if (e.getEntity() instanceof Projectile && !(((Projectile) e.getEntity()).getShooter() instanceof Player)) return;

        // 데미지를 준 엔티티가 플레이어인지 확인
        else if (!(e.getDamager() instanceof Player)) return;

        PlayerDamageByPlayerEvent event = new PlayerDamageByPlayerEvent(e);
        Bukkit.getPluginManager().callEvent(event);
    }

}