package net.pooleaf.core.modules.displayname;

import lombok.Getter;
import net.pooleaf.core.module.CoreModule;
import net.pooleaf.core.plugin.CorePlugin;

import java.util.UUID;

public class DisplayNameModule extends CoreModule {

    @Getter
    private static DisplayNameManager displayNameManager = new DisplayNameManager();


    @Override
    public String getName() {
        return "DisplayName";
    }

    @Override
    public String[] getDepends() {
        return new String[] { "Support" };
    }

    @Override
    public void onEnable(CorePlugin plugin) {
        // TODO Listener 등록
        // TODO Listener에서 플레이어 닉네임 저장 or 불러오기
    }


    /**
     * 플레이어의 UUID로 가상닉네임을 불러옵니다.
     * @param uuid 가상닉네임을 불러올 플레이어의 UUID
     * @return 가상닉네임
     */
    public static String getDisplayName(UUID uuid) {
        return displayNameManager.getOrLoad(uuid);
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
