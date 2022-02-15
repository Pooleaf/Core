package net.pooleaf.core.modules.commonsender.bungee.listener;

import java.time.LocalDateTime;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import net.pooleaf.core.modules.commonsender.CommonSenderModule;
import net.pooleaf.core.modules.commonsender.common.CommonPlayer;
import net.pooleaf.core.modules.support.common.logger.Logger;

public class BungeePlayerListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onPreLogin(LoginEvent e) {
        // 불러오기
        CommonPlayer player = CommonSenderModule.getPlayerInfoManager().load(e.getConnection().getUniqueId());
        player.setName(e.getConnection().getName());
        player.setIp(e.getConnection().getAddress().getAddress().getHostAddress());
        player.setLastLogin(LocalDateTime.now());

        // 로그
        Logger.log("플레이어 정보: " + player);

        // 저장
        CommonSenderModule.getPlayerInfoDao().insertPlayerInfo(player);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onQuit(PlayerDisconnectEvent e) {
        // 메모리 해제
        CommonSenderModule.getPlayerInfoManager().remove(e.getPlayer().getUniqueId());
    }

}
