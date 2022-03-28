package net.pooleaf.core.modules.commonsender;

import com.google.common.base.Preconditions;
import lombok.Getter;
import net.pooleaf.core.module.CoreModule;
import net.pooleaf.core.modules.commonsender.bukkit.BukkitSenderAdapter;
import net.pooleaf.core.modules.commonsender.bungee.BungeeSenderAdapter;
import net.pooleaf.core.modules.commonsender.common.*;
import net.pooleaf.core.modules.commonsender.common.sql.CommonSenderSqlManager;
import net.pooleaf.core.modules.support.common.platform.Platform;
import net.pooleaf.core.plugin.CorePlugin;

import java.util.Optional;
import java.util.UUID;

/**
 * 플랫폼에 상관없이 사용할 수 있는 플레이어 객체를 제공하고,
 * 플레이어 정보를 저장하여 관리합니다.
 */
public class CommonSenderModule extends CoreModule {

    @Getter
    private static CommonPlayerManager commonPlayerManager = new CommonPlayerManager();

    @Getter
    private static CommonSenderSqlManager sqlManager;

    @Getter
    private static CommonConsoleSender consoleSender;


    private static CommonSenderAdapter commonSenderAdapter;
    private static BukkitSenderAdapter bukkitSenderAdapter;
    private static BungeeSenderAdapter bungeeSenderAdapter;


    @Override
    public String getName() {
        return "CommonSender";
    }

    @Override
    public String[] getDepends() {
        return new String[] { "Support", "SqlLib" };
    }

    @Override
    public void onEnable(CorePlugin plugin) {
        sqlManager = new CommonSenderSqlManager();
        sqlManager.connect();

        commonSenderAdapter = new CommonSenderFactory().createCommonSenderAdapter();
        consoleSender = new CommonSenderFactory().createCommonConsoleSender();

        switch (Platform.getCurrentPlatform()) {
            case BUKKIT:
                bukkitSenderAdapter = (BukkitSenderAdapter) commonSenderAdapter;
                break;
            case BUNGEECORD:
                bungeeSenderAdapter = (BungeeSenderAdapter) commonSenderAdapter;
                break;
        }

    }


    public static BukkitSenderAdapter bukkit() {
        Preconditions.checkNotNull(bungeeSenderAdapter, "Bukkit에서만 사용할 수 있습니다.");
        return bukkitSenderAdapter;
    }

    public static BungeeSenderAdapter bungee() {
        Preconditions.checkNotNull(bungeeSenderAdapter, "BungeeCord에서만 사용할 수 있습니다.");
        return bungeeSenderAdapter;
    }


    /**
     * 플레이어의 UUID로 닉네임을 불러옵니다.
     * 접속 중이 아닐 경우 캐시 없이 불러와 반환합니다.
     * @param uuid 닉네임을 불러올 플레이어의 UUID
     * @return 닉네임
     */
    public static String getName(UUID uuid) {
        return Optional.ofNullable(commonPlayerManager.getOrLoadNoCache(uuid))
                .map(CommonPlayer::getName)
                .orElse(null);
    }

    /**
     * 플레이어의 UUID로 가상닉네임을 불러옵니다.
     * 접속 중이 아닐 경우 캐시 없이 불러와 반환합니다.
     * @param uuid 가상닉네임을 불러올 플레이어의 UUID
     * @return 가상닉네임
     */
    public static String getDisplayName(UUID uuid) {
        return Optional.ofNullable(commonPlayerManager.getOrLoadNoCache(uuid))
                .map(CommonPlayer::getDisplayName)
                .orElse(null);
    }

    /**
     * 플레이어의 닉네임으로 UUID를 불러옵니다.
     * 접속 중이 아닐 경우 캐시 없이 불러와 반환합니다.
     * @param name UUID를 불러올 플레이어의 닉네임
     * @return UUID
     */
    public static UUID getUuidByName(String name) {
        return Optional.ofNullable(commonPlayerManager.getOrLoadByName(name))
                .map(CommonPlayer::getUuid)
                .orElse(null);
    }

    /**
     * 플레이어의 가상닉네임으로 UUID를 불러옵니다.
     * 접속 중이 아닐 경우 캐시 없이 불러와 반환합니다.
     * @param displayName UUID를 불러올 플레이어의 가상닉네임
     * @return UUID
     */
    public static UUID getUuidByDisplayName(String displayName) {
        return Optional.ofNullable(commonPlayerManager.getOrLoadByDisplayName(displayName))
                .map(CommonPlayer::getUuid)
                .orElse(null);
    }

    /**
     * 해당 UUID를 가진 플레이어를 반환합니다.
     * 접속 중이 아닐 경우 캐시 없이 불러와 반환합니다.
     * @param uuid 찾을 플레이어의 UUID
     * @return 해당 UUID를 가진 플레이어
     */
    public static CommonPlayer getOnlinePlayer(UUID uuid) {
        return commonPlayerManager.get(uuid);
    }

    /**
     * 해당 닉네임을 가진 플레이어를 반환합니다.
     * 접속 중이 아닐 경우 캐시 없이 불러와 반환합니다.
     * @param name 찾을 플레이어의 닉네임
     * @return 해당 닉네임을 가진 플레이어
     */
    public static CommonPlayer getOnlinePlayerByName(String name) {
        return commonPlayerManager.getByName(name);
    }

    /**
     * 해당 가상닉네임을 가진 플레이어를 반환합니다.
     * 접속 중이 아닐 경우 캐시 없이 불러와 반환합니다.
     * @param displayName 찾을 플레이어의 가상닉네임
     * @return 해당 가상닉네임을 가진 플레이어
     */
    public static CommonPlayer getOnlinePlayerByDisplayName(String displayName) {
        return commonPlayerManager.getByDisplayName(displayName);
    }

    /**
     * 플랫폼에 맞는 Player를 CommonPlayer로 변환하여 반환합니다.
     * @param platformSender 플랫폼에 맞는 Player
     * @return 해당 Player에 맞는 CommonPlayer
     */
    public static CommonPlayer getOnlinePlayerByPlatformSender(Object platformSender) {
        return commonSenderAdapter.getPlayerByPlatformSender(platformSender);
    }

    /**
     * 플랫폼에 맞는 Sender를 CommonCommandSender로 변환하여 반환합니다.
     * @param platformSender 플랫폼에 맞는 Sender
     * @return 해당 Sender에 맞는 CommonCommandSender
     */
    public static CommonCommandSender getOnlineCommandSenderByPlatformSender(Object platformSender) {
        return commonSenderAdapter.getCommandSenderByPlatformSender(platformSender);
    }

    /**
     * 해당 UUID를 가진 플레이어를 반환합니다.
     * 접속 중이 아닐 경우 캐시 없이 불러와 반환합니다.
     * @param uuid 찾을 플레이어의 UUID
     * @return 해당 UUID를 가진 플레이어
     */
    public static CommonPlayer getPlayer(UUID uuid) {
        return commonPlayerManager.getOrLoadNoCache(uuid);
    }

    /**
     * 해당 닉네임을 가진 플레이어를 반환합니다.
     * 접속 중이 아닐 경우 캐시 없이 불러와 반환합니다.
     * @param name 찾을 플레이어의 닉네임
     * @return 해당 닉네임을 가진 플레이어
     */
    public static CommonPlayer getPlayerByName(String name) {
        return commonPlayerManager.getOrLoadNoCacheByName(name);
    }

    /**
     * 해당 가상닉네임을 가진 플레이어를 반환합니다.
     * 접속 중이 아닐 경우 캐시 없이 불러와 반환합니다.
     * @param displayName 찾을 플레이어의 가상닉네임
     * @return 해당 가상닉네임을 가진 플레이어
     */
    public static CommonPlayer getPlayerByDisplayName(String displayName) {
        return commonPlayerManager.getOrLoadNoCacheByDisplayName(displayName);
    }

    /**
     * 해당 UUID를 가진 플레이어가 존재하는지 확인합니다.
     * @param uuid 찾을 플레이어의 UUID
     * @return 플레이어 존재 여부
     */
    public static boolean existsPlayer(UUID uuid) {
        return commonPlayerManager.getOrLoad(uuid) != null;
    }

    /**
     * 해당 닉네임을 가진 플레이어가 존재하는지 확인합니다.
     * @param name 찾을 플레이어의 닉네임
     * @return 플레이어 존재 여부
     */
    public static boolean existsPlayerByName(String name) {
        return commonPlayerManager.getOrLoadByName(name) != null;
    }

    /**
     * 해당 가상닉네임을 가진 플레이어가 존재하는지 확인합니다.
     * @param displayName 찾은 플레이어의 가상닉네임
     * @return 플레이어 존재 여부
     */
    public static boolean existsPlayerByDisplayName(String displayName) {
        return commonPlayerManager.getOrLoadByDisplayName(displayName) != null;
    }

}