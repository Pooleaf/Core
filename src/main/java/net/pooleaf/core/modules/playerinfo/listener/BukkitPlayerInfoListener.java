package net.pooleaf.core.modules.playerinfo.listener;

import net.pooleaf.core.modules.playerinfo.PlayerInfo;
import net.pooleaf.core.modules.playerinfo.PlayerInfoModule;
import net.pooleaf.core.modules.support.common.logger.Logger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.time.LocalDateTime;

public class BukkitPlayerInfoListener implements Listener {

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent e) {
        // 불러오기
        PlayerInfo playerInfo = PlayerInfoModule.getPlayerInfoManager().load(e.getUniqueId());
        playerInfo.setName(e.getName());
        playerInfo.setIp(e.getAddress().getHostAddress());
        playerInfo.setLastLogin(LocalDateTime.now());

        // 로그
        Logger.log("플레이어 정보: " + playerInfo);

        // 저장
        PlayerInfoModule.getPlayerInfoDao().insertPlayerInfo(playerInfo);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        // 메모리 해제
        PlayerInfoModule.getPlayerInfoManager().remove(e.getPlayer().getUniqueId());
    }

}
