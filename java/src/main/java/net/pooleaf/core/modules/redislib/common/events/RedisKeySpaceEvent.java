package net.pooleaf.core.modules.redislib.common.events;

import net.pooleaf.core.modules.commonevent.common.CommonEvent;
import lombok.Data;

@Data
public class RedisKeySpaceEvent extends CommonEvent {

    private final String key;
    private final String task;

}
