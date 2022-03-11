package net.pooleaf.core.modules.channel.common.event;

import lombok.Data;
import net.pooleaf.core.modules.channel.common.channel.Channel;
import net.pooleaf.core.modules.commonevent.common.CommonEvent;

@Data
public class ChannelDeleteEvent extends CommonEvent {

  private final Channel channel;

}
