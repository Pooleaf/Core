package net.pooleaf.core.modules.commonsender.bukkit.listener;

import net.pooleaf.core.modules.commonsender.common.CommonPlayer;
import net.pooleaf.core.modules.commonsender.CommonSenderModule;
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
        CommonPlayer player = CommonSenderModule.getPlayerInfoManager().load(e.getUniqueId());
        player.setName(e.getName());
        player.setIp(e.getAddress().getHostAddress());
        player.setLastLogin(LocalDateTime.now());

        // 로그
        Logger.log("플레이어 정보: " + player);

        // 저장
        CommonSenderModule.getPlayerInfoDao().insertPlayerInfo(player);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onQuit(PlayerQuitEvent e) {
        // 메모리 해제
        CommonSenderModule.getPlayerInfoManager().remove(e.getPlayer().getUniqueId());
    }

}
