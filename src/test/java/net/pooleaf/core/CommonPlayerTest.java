package net.pooleaf.core;

import net.pooleaf.core.modules.commonsender.CommonSenderModule;
import net.pooleaf.core.modules.commonsender.bukkit.BukkitPlayer;
import net.pooleaf.core.modules.commonsender.common.CommonPlayer;
import net.pooleaf.core.modules.support.common.logger.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

public class CommonPlayerTest {

    @BeforeAll
    public static void setUp() {
        Core.init(null);
    }

    @Test
    public void test() {
        UUID uuid = UUID.randomUUID();
        String name = "test";
        String ip = "127.0.0.1";


        System.out.println("uuid: " + uuid);

        // 불러오기
        CommonPlayer player = CommonSenderModule.getCommonPlayerManager().getOrLoad(uuid);
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
//        CommonSenderModule.getPlayerInfoDao().insertPlayerInfo(player);
    }

}
