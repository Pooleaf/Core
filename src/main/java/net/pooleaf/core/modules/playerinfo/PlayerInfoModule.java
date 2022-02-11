package net.pooleaf.core.modules.playerinfo;

import lombok.Getter;
import net.md_5.bungee.api.ProxyServer;
import net.pooleaf.core.Core;
import net.pooleaf.core.module.CoreModule;
import net.pooleaf.core.modules.playerinfo.sql.PlayerInfoDao;
import net.pooleaf.core.modules.support.common.platform.Platform;
import net.pooleaf.core.plugin.CorePlugin;

import java.util.UUID;

public class PlayerInfoModule extends CoreModule {

    @Getter
    private static PlayerInfoManager playerInfoManager = new PlayerInfoManager();

    @Getter
    private static PlayerInfoDao playerInfoDao;


    @Override
    public String getName() {
        return "PlayerInfo";
    }

    @Override
    public String[] getDepends() {
        return new String[] { "Support", "Sqlib" };
    }

    @Override
    public void onEnable(CorePlugin plugin) {
        playerInfoDao = new PlayerInfoDao();

        // 접속 불러오기, 퇴장 메모리 해제 Listener
        switch (Platform.getCurrentPlatform()) {
            case BUKKIT:
                org.bukkit.Bukkit.getPluginManager().registerEvents(new net.pooleaf.core.modules.playerinfo.listener.BukkitPlayerInfoListener(), (org.bukkit.plugin.Plugin) Core.getPlugin());
                break;
            case BUNGEECORD:
                ProxyServer.getInstance().getPluginManager().registerListener((net.md_5.bungee.api.plugin.Plugin) Core.getPlugin(), new net.pooleaf.core.modules.playerinfo.listener.BungeePlayerInfoListener());
                break;
        }
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

}
