package net.pooleaf.core.modules.playerinfo;

import net.pooleaf.core.modules.support.common.CommonChatColor;
import net.pooleaf.core.modules.support.common.manager.AbstractManager;

import java.util.List;
import java.util.UUID;

public class PlayerInfoManager extends AbstractManager<UUID, PlayerInfo> {

    @Override
    public PlayerInfo load(UUID key) {
        return PlayerInfoModule.getPlayerInfoDao().selectPlayerInfoByUuid(key);
    }

    public PlayerInfo getByName(String name) {
        for (PlayerInfo playerInfo : datas.values()) {
            if (playerInfo.getName().equalsIgnoreCase(name)) {
                return playerInfo;
            }
        }

        return null;
    }

    public PlayerInfo getOrLoadByName(String name) {
        PlayerInfo playerInfo = getByName(name);

        if (playerInfo == null) {
            PlayerInfoModule.getPlayerInfoDao().selectPlayerInfoByName(name);
        }

        return playerInfo;
    }

    public PlayerInfo getByDisplayName(String displayName) {
        displayName = CommonChatColor.stripColor(displayName);

        for (PlayerInfo playerInfo : datas.values()) {
            if (CommonChatColor.stripColor(playerInfo.getDisplayName()).equalsIgnoreCase(displayName)) {
                return playerInfo;
            }
        }

        return null;
    }

    public PlayerInfo getOrLoadByDisplayName(String displayName) {
        PlayerInfo playerInfo = getByDisplayName(displayName);

        if (playerInfo == null) {
            PlayerInfoModule.getPlayerInfoDao().selectPlayerInfoByDisplayName(displayName);
        }

        return playerInfo;
    }

}
