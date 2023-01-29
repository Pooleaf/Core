package net.pooleaf.core.modules.channel.common.events;

import net.pooleaf.core.modules.commonevent.common.CommonEvent;
import lombok.Data;
import net.pooleaf.core.modules.channel.common.channel.Channel;

@Data
public class ChannelDeleteEvent extends CommonEvent {

  private final Channel channel;

}
