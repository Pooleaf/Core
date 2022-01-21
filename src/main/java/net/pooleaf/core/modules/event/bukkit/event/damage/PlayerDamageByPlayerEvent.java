package net.pooleaf.core.modules.event.bukkit.event.damage;

import lombok.AllArgsConstructor;
import net.pooleaf.core.modules.event.bukkit.event.CancellableEvent;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

@AllArgsConstructor
public class PlayerDamageByPlayerEvent extends CancellableEvent {

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
     * 데미지를 입힌 Player를 반환합니다.
     * @return 데미지를 입힌 Player
     */
    public Player getDamager() {
        if (isProjectileDamager()) {
            Projectile projectile = (Projectile) entityDamageByEntityEvent.getDamager();
            return (Player) projectile.getShooter();
        }

        return (Player) getProjectileDamager();
    }

}
