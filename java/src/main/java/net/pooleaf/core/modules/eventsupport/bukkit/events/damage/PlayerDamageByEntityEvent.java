package net.pooleaf.core.modules.eventsupport.bukkit.events.damage;

import lombok.AllArgsConstructor;
import net.pooleaf.core.modules.eventsupport.bukkit.events.CancellableEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

@AllArgsConstructor
public class PlayerDamageByEntityEvent extends CancellableEvent {

    private EntityDamageByEntityEvent entityDamageByEntityEvent;


    /**
     * 데미지를 입힌 Entity가 발사체인지 확인합니다.
     * @return 발사체 여부
     */
    public boolean isProjectileDamager() {
        return entityDamageByEntityEvent.getDamager() instanceof Projectile;
    }

    /**
     * 데미지를 받은 Player를 반환합니다.
     * @return 데미지를 받은 Player
     */
    public Player getPlayer() {
        return (Player) entityDamageByEntityEvent.getEntity();
    }

    /**
     * 데미지를 입힌 발사체를 반환합니다.
     * @return 데미지를 입힌 발사체
     */
    public Projectile getProjectileDamager() {
        return (Projectile) entityDamageByEntityEvent.getDamager();
    }

    /**
     * 데미지를 입힌 Entity를 반환합니다.
     * @return 데미지를 입힌 Entity, 발사체를 쏜 게 Entity가 아닐 경우 null
     */
    public Entity getDamager() {
        if (isProjectileDamager()) {
            return (Entity) getProjectileDamager().getShooter();
        }

        return entityDamageByEntityEvent.getDamager();
    }

}
