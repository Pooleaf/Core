package net.pooleaf.core.modules.commonsender.common;

import net.pooleaf.core.modules.commonsender.CommonSenderModule;
import net.pooleaf.core.modules.support.common.CommonChatColor;
import net.pooleaf.core.modules.support.common.manager.AbstractManager;

import java.util.UUID;

public class CommonPlayerManager extends AbstractManager<UUID, CommonPlayer> {

    @Override
    public CommonPlayer load(UUID key) {
        return CommonSenderModule.getPlayerInfoDao().selectPlayerInfoByUuid(key);
    }

    public CommonPlayer getByName(String name) {
        for (CommonPlayer playerInfo : datas.values()) {
            if (playerInfo.getName().equalsIgnoreCase(name)) {
                return playerInfo;
            }
        }

        return null;
    }

    public CommonPlayer getOrLoadByName(String name) {
        CommonPlayer playerInfo = getByName(name);

        if (playerInfo == null) {
            CommonSenderModule.getPlayerInfoDao().selectPlayerInfoByName(name);
        }

        return playerInfo;
    }

    public CommonPlayer getByDisplayName(String displayName) {
        displayName = CommonChatColor.stripColor(displayName);

        for (CommonPlayer playerInfo : datas.values()) {
            if (CommonChatColor.stripColor(playerInfo.getDisplayName()).equalsIgnoreCase(displayName)) {
                return playerInfo;
            }
        }

        return null;
    }

    public CommonPlayer getOrLoadByDisplayName(String displayName) {
        CommonPlayer playerInfo = getByDisplayName(displayName);

        if (playerInfo == null) {
            CommonSenderModule.getPlayerInfoDao().selectPlayerInfoByDisplayName(displayName);
        }

        return playerInfo;
    }

}
