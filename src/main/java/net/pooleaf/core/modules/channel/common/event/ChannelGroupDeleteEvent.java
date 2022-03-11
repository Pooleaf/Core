package net.pooleaf.core.modules.channel.common.event;

import lombok.Data;
import net.pooleaf.core.modules.channel.common.channelgroup.ChannelGroup;
import net.pooleaf.core.modules.commonevent.common.CommonEvent;

@Data
public class ChannelGroupDeleteEvent extends CommonEvent {

  private final ChannelGroup channelGroup;

}
