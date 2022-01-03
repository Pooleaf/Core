package net.pooleaf.core.support.bukkit.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@UtilityClass
public class TeleportUtil {

    /**
     * 플레이어를 해당 위치로 텔레포트시킵니다.
     * 탑승물에 타고 있을 경우 내리고 텔레포트합니다.
     * @param player 텔레포트 시킬 플레이어
     * @param location 텔레포트할 위치
     */
    public static void teleport(Player player, Location location) {
        // 탑승물
        if (player.isInsideVehicle()) {
            player.leaveVehicle();
        }

        // TODO 침대 제대로 막아야 함
        player.eject();

        player.teleport(location);
    }

}
