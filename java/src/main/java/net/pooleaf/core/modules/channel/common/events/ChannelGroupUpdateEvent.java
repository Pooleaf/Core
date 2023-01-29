package net.pooleaf.core.modules.channel.common.events;

import net.pooleaf.core.modules.commonevent.common.CommonEvent;
import lombok.Data;
import net.pooleaf.core.modules.channel.common.channelgroup.ChannelGroup;

@Data
public class ChannelGroupUpdateEvent extends CommonEvent {

  private final ChannelGroup channelGroup;

}
