package net.pooleaf.core.modules.redislib.common.events;

import lombok.Data;
import net.pooleaf.core.modules.commonevent.common.CommonEvent;

@Data
public class RedisKeySpaceEvent extends CommonEvent {

    private final String key;
    private final String task;

}
