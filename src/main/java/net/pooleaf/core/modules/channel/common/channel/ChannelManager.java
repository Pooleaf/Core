package net.pooleaf.core.modules.channel.common.channel;

import net.pooleaf.core.modules.channel.common.channelgroup.ChannelGroup;
import net.pooleaf.core.modules.support.common.manager.AbstractManager;

import java.util.TreeMap;

public class ChannelManager extends AbstractManager<String, Channel> {

    public ChannelManager() {
        super(new TreeMap<String, ChannelGroup>(String.CASE_INSENSITIVE_ORDER));
    }

}