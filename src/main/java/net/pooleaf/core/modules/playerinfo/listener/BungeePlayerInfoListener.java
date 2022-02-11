package net.pooleaf.core.modules.playerinfo.listener;

import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.pooleaf.core.modules.playerinfo.PlayerInfo;
import net.pooleaf.core.modules.playerinfo.PlayerInfoModule;
import net.pooleaf.core.modules.support.common.logger.Logger;

import java.time.LocalDateTime;

public class BungeePlayerInfoListener implements Listener {

    @EventHandler
    public void onPreLogin(LoginEvent e) {
        // 불러오기
        PlayerInfo playerInfo = PlayerInfoModule.getPlayerInfoManager().load(e.getConnection().getUniqueId());
        playerInfo.setName(e.getConnection().getName());
        playerInfo.setIp(e.getConnection().getAddress().getAddress().getHostAddress());
        playerInfo.setLastLogin(LocalDateTime.now());

        // 로그
        Logger.log("플레이어 정보: " + playerInfo);

        // 저장
        PlayerInfoModule.getPlayerInfoDao().insertPlayerInfo(playerInfo);
    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent e) {
        // 메모리 해제
        PlayerInfoModule.getPlayerInfoManager().remove(e.getPlayer().getUniqueId());
    }

}
