package net.pooleaf.core.modules.commonsender.common;

import net.pooleaf.core.modules.support.common.CommonChatColor;
import net.pooleaf.core.modules.commonsender.CommonSenderModule;
import net.pooleaf.core.modules.support.common.manager.AbstractEhcacheManager;
import net.pooleaf.core.modules.support.common.manager.LoadableManager;

import java.util.List;
import java.util.UUID;

public class CommonPlayerManager extends AbstractEhcacheManager<UUID, CommonPlayer> implements LoadableManager<UUID, CommonPlayer> {

    // 오프라인 플레이어 미접근 시 남아있을 시간
    private static final int IDLE_SECONDS = 60;

    private Class playerClass;


    public CommonPlayerManager() {
        playerClass = CommonSenderModule.getPlatformPlayerClass();
    }


    @Override
    public CommonPlayer loadWithoutCache(UUID key) {
        return CommonSenderModule.getSqlManager().commonPlayer().selectPlayerInfoByUuid(key, playerClass);
    }

    @Override
    public CommonPlayer load(UUID key) {
        CommonPlayer commonPlayer = LoadableManager.super.load(key);

        if (commonPlayer != null) {
            setTimeToIdle(key, IDLE_SECONDS);
        }

        return commonPlayer;
    }

    /**
     * 닉네임으로 CommonPlayer를 찾아 반환합니다.
     */
    public CommonPlayer getByName(String name) {
        return values().stream()
                .filter(commonPlayer -> commonPlayer.getName().equalsIgnoreCase(name))
                .findFirst().orElse(null);
    }

    /**
     * 닉네임으로 CommonPlayer를 찾아 반환합니다.
     * 캐싱되지 않았을 경우 데이터베이스에서 불러와 캐싱하고 반환합니다.
     */
    public CommonPlayer getOrLoadByName(String name) {
        CommonPlayer commonPlayer = getByName(name);

        if (commonPlayer == null) {
            commonPlayer = CommonSenderModule.getSqlManager().commonPlayer().selectPlayerInfoByName(name, playerClass);
            set(commonPlayer.uuid, commonPlayer);
            setTimeToIdle(commonPlayer.uuid, IDLE_SECONDS);
        }

        return commonPlayer;
    }

    /**
     * 닉네임으로 CommonPlayer를 찾아 반환합니다.
     * 캐싱되지 않았을 경우 데이터베이스에서 불러와 반환합니다.
     */
    public CommonPlayer getOrLoadByNameWithoutCache(String name) {
        CommonPlayer commonPlayer = getByName(name);

        if (commonPlayer == null) {
            return CommonSenderModule.getSqlManager().commonPlayer().selectPlayerInfoByName(name, playerClass);
        }

        return commonPlayer;
    }

    /**
     * 가상 닉네임으로 CommonPlayer를 찾아 반환합니다.
     * 색상 코드를 무시하고 검색합니다.
     */
    public CommonPlayer getByDisplayName(String displayName) {
        String strippedDisplayName = CommonChatColor.stripColor(displayName);

        return values().stream()
                .filter(commonPlayer -> CommonChatColor.stripColor(commonPlayer.getDisplayName()).equalsIgnoreCase(strippedDisplayName))
                .findFirst().orElse(null);
    }

    /**
     * 가상 닉네임으로 CommonPlayer를 찾아 반환합니다.
     * 캐싱되지 않았을 경우 데이터베이스에서 불러와 캐싱하고 반환합니다.
     * 색상 코드를 무시하고 검색합니다.
     */
    public CommonPlayer getOrLoadByDisplayName(String displayName) {
        CommonPlayer commonPlayer = getByDisplayName(displayName);

        if (commonPlayer == null) {
            commonPlayer = CommonSenderModule.getSqlManager().commonPlayer().selectPlayerInfoByDisplayName(displayName, playerClass);
            set(commonPlayer.uuid, commonPlayer);
            setTimeToIdle(commonPlayer.uuid, IDLE_SECONDS);
        }

        return commonPlayer;
    }

    /**
     * 가상 닉네임으로 CommonPlayer를 찾아 반환합니다.
     * 캐싱되지 않았을 경우 데이터베이스에서 불러와 캐싱 없이 반환합니다.
     * 색상 코드를 무시하고 검색합니다.
     */
    public CommonPlayer getOrLoadByDisplayNameWithoutCache(String displayName) {
        CommonPlayer commonPlayer = getByDisplayName(displayName);

        if (commonPlayer == null) {
            return CommonSenderModule.getSqlManager().commonPlayer().selectPlayerInfoByDisplayName(displayName, playerClass);
        }

        return commonPlayer;
    }

    /**
     * 아이피로 데이터베이스에서 CommonPlayer를 찾아 목록을 캐싱 없이 반환합니다.
     */
    public List<CommonPlayer> loadByIpWithoutNoCache(String ip) {
        return CommonSenderModule.getSqlManager().commonPlayer().selectPlayerInfosByIp(ip, playerClass);
    }

}