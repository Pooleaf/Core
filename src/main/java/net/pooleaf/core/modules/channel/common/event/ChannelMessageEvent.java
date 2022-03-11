package net.pooleaf.core.modules.channel.common.event;

import java.util.List;
import lombok.Data;
import net.pooleaf.core.modules.commonevent.common.CommonEvent;

@Data
public class ChannelMessageEvent extends CommonEvent {

  private final String task;
  private final List<Object> datas;

}