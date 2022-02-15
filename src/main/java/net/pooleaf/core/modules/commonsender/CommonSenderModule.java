package net.pooleaf.core.modules.commonsender;

import com.google.common.base.Preconditions;
import lombok.Getter;
import net.pooleaf.core.module.CoreModule;
import net.pooleaf.core.modules.commonsender.bukkit.BukkitConsoleSender;
import net.pooleaf.core.modules.commonsender.bukkit.BukkitSenderAdapter;
import net.pooleaf.core.modules.commonsender.bungee.BungeeConsoleSender;
import net.pooleaf.core.modules.commonsender.bungee.BungeeSenderAdapter;
import net.pooleaf.core.modules.commonsender.common.CommonCommandSender;
import net.pooleaf.core.modules.commonsender.common.CommonConsoleSender;
import net.pooleaf.core.modules.commonsender.common.CommonPlayer;
import net.pooleaf.core.modules.commonsender.common.CommonPlayerManager;
import net.pooleaf.core.modules.commonsender.common.sql.CommonPlayerDao;
import net.pooleaf.core.modules.support.common.platform.Platform;
import net.pooleaf.core.plugin.CorePlugin;

import java.util.UUID;

/**
 * 플랫폼에 상관없이 사용할 수 있는 플레이어 객체를 제공하고,
 * 플레이어 정보를 저장하여 관리합니다.
 */
public class CommonSenderModule extends CoreModule {

    @Getter
    private static CommonPlayerManager playerInfoManager = new CommonPlayerManager();

    @Getter
    private static CommonPlayerDao playerInfoDao;

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
        return new String[] { "Support", "Sqlib" };
    }

    @Override
    public void onEnable(CorePlugin plugin) {
        playerInfoDao = new CommonPlayerDao();

        switch (Platform.getCurrentPlatform()) {
            case BUKKIT:
                bukkitSenderAdapter = new BukkitSenderAdapter();
                commonSenderAdapter = bukkitSenderAdapter;
                consoleSender = new BukkitConsoleSender();
                break;
            case BUNGEECORD:
                bungeeSenderAdapter = new BungeeSenderAdapter();
                commonSenderAdapter = bungeeSenderAdapter;
                consoleSender = new BungeeConsoleSender();
                break;
        }

        // 접속 불러오기, 퇴장 메모리 해제 Listener
        commonSenderAdapter.registerListeners();
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
     * @param uuid 닉네임을 불러올 플레이어의 UUID
     * @return 닉네임
     */
    public static String getName(UUID uuid) {
        return playerInfoManager.getOrLoad(uuid).getName();
    }

    /**
     * 플레이어의 UUID로 가상닉네임을 불러옵니다.
     * @param uuid 가상닉네임을 불러올 플레이어의 UUID
     * @return 가상닉네임
     */
    public static String getDisplayName(UUID uuid) {
        return playerInfoManager.getOrLoad(uuid).getDisplayName();
    }

    /**
     * 해당 플레이어의 가상닉네임을 불러옵니다.
     * @param player 가상닉네임을 불러올 플레이어
     * @return 가상닉네임
     */
    public static String getDisplayName(org.bukkit.entity.Player player) {
        return getDisplayName(player.getUniqueId());
    }

    /**
     * 해당 플레이어의 가상닉네임을 불러옵니다.
     * @param player 가상닉네임을 불러올 플레이어
     * @return 가상닉네임
     */
    public static String getDisplayName(net.md_5.bungee.api.connection.ProxiedPlayer player) {
        return getDisplayName(player.getUniqueId());
    }

    /**
     * 해당 UUID를 가진 플레이어를 반환합니다.
     * @param uuid 찾을 플레이어의 UUID
     * @return 해당 UUID를 가진 플레이어
     */
    public static CommonPlayer getPlayer(UUID uuid) {
        return commonSenderAdapter.getPlayer(uuid);
    }

    /**
     * 해당 닉네임을 가진 플레이어를 반환합니다.
     * @param name 찾을 플레이어의 닉네임
     * @return 해당 닉네임을 가진 플레이어
     */
    public static CommonPlayer getPlayerByName(String name) {
        return commonSenderAdapter.getPlayerByName(name);
    }

    /**
     * 해당 가상닉네임을 가진 플레이어를 반환합니다.
     * @param displayName 찾을 플레이어의 가상닉네임
     * @return 해당 가상닉네임을 가진 플레이어
     */
    public static CommonPlayer getPlayerByDisplayName(String displayName) {
        return commonSenderAdapter.getPlayerByDisplayName(displayName);
    }

    /**
     * 플랫폼에 맞는 Player를 CommonPlayer로 변환하여 반환합니다.
     * @param platformSender 플랫폼에 맞는 Player
     * @return 해당 Player에 맞는 CommonPlayer
     */
    public static CommonPlayer getCommonPlayerByPlatformSender(Object platformSender) {
        return commonSenderAdapter.getCommonPlayerByPlatformSender(platformSender);
    }

    /**
     * 플랫폼에 맞는 Sender를 CommonCommandSender로 변환하여 반환합니다.
     * @param platformSender 플랫폼에 맞는 Sender
     * @return 해당 Sender에 맞는 CommonCommandSender
     */
    public static CommonCommandSender getCommonCommandSenderByPlatformSender(Object platformSender) {
        return commonSenderAdapter.getCommonCommandSenderByPlatformSender(platformSender);
    }

}
