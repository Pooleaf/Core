package net.pooleaf.core.event.bukkit.event.damage;

import lombok.AllArgsConstructor;
import net.pooleaf.core.event.bukkit.event.CancellableEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

@AllArgsConstructor
public class PlayerDamageEvent extends CancellableEvent {

    private EntityDamageEvent entityDamageEvent;


    /**
     * 데미지를 받은 Player를 반환합니다.
     * @return 데미지를 받은 Player
     */
    public Player getPlayer() {
        return (Player) entityDamageEvent.getEntity();
    }

}
