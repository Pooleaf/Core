package net.pooleaf.core.modules.channel.common.events;

import java.util.List;

import net.pooleaf.core.modules.commonevent.common.CommonEvent;
import lombok.Data;

@Data
public class ChannelMessageEvent extends CommonEvent {

  private final String task;
  private final List<Object> datas;

}