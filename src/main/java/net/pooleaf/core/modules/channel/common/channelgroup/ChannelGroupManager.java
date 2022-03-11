package net.pooleaf.core.modules.channel.common.channelgroup;

import java.util.UUID;
import net.pooleaf.core.modules.support.common.CommonChatColor;
import net.pooleaf.core.modules.support.common.manager.AbstractManager;

import java.util.TreeMap;

public class ChannelGroupManager extends AbstractManager<String, ChannelGroup> {

    public ChannelGroupManager() {
        super(new TreeMap<String, ChannelGroup>(String.CASE_INSENSITIVE_ORDER));
    }


    public ChannelGroup getByDisplayName(String displayName) {
        displayName = CommonChatColor.stripColor(displayName);

        for (ChannelGroup channelGroup : datas.values()) {
            if (channelGroup.hasDisplayName()
                && CommonChatColor.stripColor(channelGroup.getDisplayName()).equals(displayName)) {
                return channelGroup;
            }
        }

        return null;
    }

    public ChannelGroup getHasPlayer(String playerName) {
        for (ChannelGroup channelGroup : datas.values()) {
            if (channelGroup.hasPlayer(playerName)) {
                return channelGroup;
            }
        }

        return null;
    }

    public ChannelGroup getHasPlayer(UUID uuid) {
        for (ChannelGroup channelGroup : datas.values()) {
            if (channelGroup.hasPlayer(uuid)) {
                return channelGroup;
            }
        }

        return null;
    }

}