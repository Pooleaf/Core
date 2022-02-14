package net.pooleaf.core.modules.commonplayer.bukkit.listener;

import net.pooleaf.core.modules.commonplayer.common.CommonPlayer;
import net.pooleaf.core.modules.commonplayer.CommonPlayerModule;
import net.pooleaf.core.modules.support.common.logger.Logger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.time.LocalDateTime;

public class BukkitPlayerListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onPreLogin(AsyncPlayerPreLoginEvent e) {
        // 불러오기
        CommonPlayer player = CommonPlayerModule.getPlayerInfoManager().load(e.getUniqueId());
        player.setName(e.getName());
        player.setIp(e.getAddress().getHostAddress());
        player.setLastLogin(LocalDateTime.now());

        // 로그
        Logger.log("플레이어 정보: " + player);

        // 저장
        CommonPlayerModule.getPlayerInfoDao().insertPlayerInfo(player);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onQuit(PlayerQuitEvent e) {
        // 메모리 해제
        CommonPlayerModule.getPlayerInfoManager().remove(e.getPlayer().getUniqueId());
    }

}
