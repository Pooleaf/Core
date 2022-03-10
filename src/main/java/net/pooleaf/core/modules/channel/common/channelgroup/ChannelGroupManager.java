package net.pooleaf.core.modules.channel.common.channelgroup;

import net.pooleaf.core.modules.support.common.manager.AbstractManager;

import java.util.TreeMap;

public class ChannelGroupManager extends AbstractManager<String, ChannelGroup> {

    public ChannelGroupManager() {
        super(new TreeMap<String, ChannelGroup>(String.CASE_INSENSITIVE_ORDER));
    }


    public ChannelGroup getByDisplayName(String displayName) {
        for (ChannelGroup channelGroup : datas.values()) {
            if (channelGroup.)
        }
    }

}