package net.pooleaf.core.modules.commonsender.bungee.listeners;

import java.time.LocalDateTime;

import net.md_5.bungee.api.ProxyServer;
import net.pooleaf.core.modules.commonsender.bungee.events.BungeeFirstLoginEvent;
import net.pooleaf.core.modules.commonsender.common.CommonPlayer;
import net.pooleaf.core.modules.support.common.logger.Logger;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import net.pooleaf.core.modules.commonsender.CommonSenderModule;
import net.pooleaf.core.modules.commonsender.bungee.BungeePlayer;

public class BungeeCommonPlayerListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPreLogin(LoginEvent e) {
        boolean isFirstJoin = false;

        // 불러오기
        CommonPlayer player = CommonSenderModule.getCommonPlayerManager().load(e.getConnection().getUniqueId());
        if (player == null) {
            player = new BungeePlayer();
            player.setUuid(e.getConnection().getUniqueId());
            player.setFirstJoin(LocalDateTime.now());
            isFirstJoin = true;
        }
        player.setName(e.getConnection().getName());
        player.setIp(e.getConnection().getAddress().getAddress().getHostAddress());
        player.setLastLogin(LocalDateTime.now());

        CommonSenderModule.getCommonPlayerManager().set(player.getUuid(), player);

        // 로그
        Logger.log("플레이어 정보: " + player);

        // 저장
        CommonSenderModule.getSqlManager().commonPlayer().insertPlayerInfo(player);

        if (isFirstJoin) {
            try {
                ProxyServer.getInstance().getPluginManager().callEvent(new BungeeFirstLoginEvent(player));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerDisconnectEvent e) {
        CommonPlayer player = CommonSenderModule.getCommonPlayerManager().get(e.getPlayer().getUniqueId());
        if (player == null) {
            return;
        }

        player.setLastOnline(LocalDateTime.now());

        // 저장
        CommonSenderModule.getSqlManager().commonPlayer().insertPlayerInfo(player);

        // 메모리 해제
        CommonSenderModule.getCommonPlayerManager().remove(e.getPlayer().getUniqueId());
    }

}
