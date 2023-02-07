package net.pooleaf.core.modules.commonsender.bukkit.listeners;

import net.pooleaf.core.modules.commonsender.common.CommonPlayer;
import net.pooleaf.core.modules.support.common.logger.Logger;
import net.pooleaf.core.modules.commonsender.bukkit.BukkitPlayer;
import net.pooleaf.core.modules.commonsender.CommonSenderModule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public class BukkitCommonPlayerListener implements Listener {

    /**
     * 비동기로 CommonPlayer 불러오기
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPreLogin(AsyncPlayerPreLoginEvent e) {
        handlePlayerLogin(e.getUniqueId(), e.getName(), e.getAddress().getHostAddress());
    }

    /**
     * 만약 플러그인 로딩 전에 접속을 시도하여
     * 비동기로 CommonPlayer 정보를 불러오지 못했을 경우
     * 동기로 불러오도록 처리
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onLogin(PlayerLoginEvent e) {
        if (!CommonSenderModule.getCommonPlayerManager().exists(e.getPlayer().getUniqueId())) {
            handlePlayerLogin(e.getPlayer().getUniqueId(), e.getPlayer().getName(), e.getAddress().getHostAddress());
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onQuit(PlayerQuitEvent e) {
        // 메모리 해제
        CommonSenderModule.getCommonPlayerManager().remove(e.getPlayer().getUniqueId());
    }


    private void handlePlayerLogin(UUID uuid, String name, String ip) {
        // 불러오기
        CommonPlayer player = CommonSenderModule.getCommonPlayerManager().load(uuid);
        if (player == null) {
            player = new BukkitPlayer();
            player.setUuid(uuid);
        }
        player.setName(name);
        player.setIp(ip);
        player.setLastLogin(LocalDateTime.now());

        CommonSenderModule.getCommonPlayerManager().set(player.getUuid(), player);

        // 로그
        Logger.log("플레이어 정보: " + player);

        // 저장
        CommonSenderModule.getSqlManager().commonPlayer().insertPlayerInfo(player);
    }

}
