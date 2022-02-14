package net.pooleaf.core.modules.commonplayer;

import com.google.common.base.Preconditions;
import lombok.Getter;
import net.pooleaf.core.module.CoreModule;
import net.pooleaf.core.modules.commonplayer.bukkit.BukkitPlayerAdapter;
import net.pooleaf.core.modules.commonplayer.bungee.BungeePlayerAdapter;
import net.pooleaf.core.modules.commonplayer.common.CommonPlayer;
import net.pooleaf.core.modules.commonplayer.common.CommonPlayerManager;
import net.pooleaf.core.modules.commonplayer.common.sql.CommonPlayerDao;
import net.pooleaf.core.modules.support.common.platform.Platform;
import net.pooleaf.core.plugin.CorePlugin;

import java.util.UUID;

/**
 * 플랫폼에 상관없이 사용할 수 있는 플레이어 객체를 제공하고,
 * 플레이어 정보를 저장하여 관리합니다.
 */
public class CommonPlayerModule extends CoreModule {

    @Getter
    private static CommonPlayerManager playerInfoManager = new CommonPlayerManager();

    @Getter
    private static CommonPlayerDao playerInfoDao;


    private static CommonPlayerAdapter commonPlayerAdapter;
    private static BukkitPlayerAdapter bukkitPlayerAdapter;
    private static BungeePlayerAdapter bungeePlayerAdapter;


    @Override
    public String getName() {
        return "Player";
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
                bukkitPlayerAdapter = new BukkitPlayerAdapter();
                commonPlayerAdapter = bukkitPlayerAdapter;
                break;
            case BUNGEECORD:
                bungeePlayerAdapter = new BungeePlayerAdapter();
                commonPlayerAdapter = bungeePlayerAdapter;
                break;
        }

        // 접속 불러오기, 퇴장 메모리 해제 Listener
        commonPlayerAdapter.registerListeners();
    }


    public static BukkitPlayerAdapter bukkit() {
        Preconditions.checkNotNull(bungeePlayerAdapter, "Bukkit에서만 사용할 수 있습니다.");
        return bukkitPlayerAdapter;
    }

    public static BungeePlayerAdapter bungee() {
        Preconditions.checkNotNull(bungeePlayerAdapter, "BungeeCord에서만 사용할 수 있습니다.");
        return bungeePlayerAdapter;
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
        return commonPlayerAdapter.getPlayer(uuid);
    }

    /**
     * 해당 닉네임을 가진 플레이어를 반환합니다.
     * @param name 찾을 플레이어의 닉네임
     * @return 해당 닉네임을 가진 플레이어
     */
    public static CommonPlayer getPlayerByName(String name) {
        return commonPlayerAdapter.getPlayerByName(name);
    }

    /**
     * 해당 가상닉네임을 가진 플레이어를 반환합니다.
     * @param displayName 찾을 플레이어의 가상닉네임
     * @return 해당 가상닉네임을 가진 플레이어
     */
    public static CommonPlayer getPlayerByDisplayName(String displayName) {
        return commonPlayerAdapter.getPlayerByDisplayName(displayName);
    }

}
