package net.pooleaf.core.modules.channel.common.channel;

import java.util.UUID;
import net.pooleaf.core.modules.channel.common.channelgroup.ChannelGroup;
import net.pooleaf.core.modules.support.common.CommonChatColor;
import net.pooleaf.core.modules.support.common.manager.AbstractManager;

import java.util.TreeMap;

public class ChannelManager extends AbstractManager<String, Channel> {

    public ChannelManager() {
        super(new TreeMap<String, ChannelGroup>(String.CASE_INSENSITIVE_ORDER));
    }


    public Channel getByDisplayName(String displayName) {
        displayName = CommonChatColor.stripColor(displayName);

        for (Channel channel : datas.values()) {
            if (channel.hasDisplayName()
                && CommonChatColor.stripColor(channel.getDisplayName()).equals(displayName)) {
                return channel;
            }
        }

        return null;
    }

    public Channel getHasPlayer(String playerName) {
        for (Channel channel : datas.values()) {
            if (channel.hasPlayer(playerName)) {
                return channel;
            }
        }

        return null;
    }

    public Channel getHasPlayer(UUID uuid) {
        for (Channel channel : datas.values()) {
            if (channel.hasPlayer(uuid)) {
                return channel;
            }
        }

        return null;
    }

}