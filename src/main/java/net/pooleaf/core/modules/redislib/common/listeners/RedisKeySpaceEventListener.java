package net.pooleaf.core.modules.redislib.common.listeners;

import io.lettuce.core.pubsub.RedisPubSubListener;
import net.pooleaf.core.modules.commonevent.CommonEventModule;
import net.pooleaf.core.modules.redislib.common.events.RedisKeySpaceEvent;
import net.pooleaf.core.modules.support.common.debugger.Debugger;
import net.pooleaf.core.modules.support.common.logger.Logger;

public class RedisKeySpaceEventListener implements RedisPubSubListener<String, String> {

    @Override
    public void message(String s, String s2) {
    }

    @Override
    public void message(String s, String k1, String s2) {
        if (!k1.startsWith("__keyspace@")) return;

        RedisKeySpaceEvent event = new RedisKeySpaceEvent(k1.substring("__keyspace@0__:".length()), s2);
        Debugger.log(event);

        CommonEventModule.callEvent(event);
    }

    @Override
    public void subscribed(String s, long l) {
    }

    @Override
    public void psubscribed(String s, long l) {
        Logger.log(s + " 채널을 구독했습니다.");
    }

    @Override
    public void unsubscribed(String s, long l) {
    }

    @Override
    public void punsubscribed(String s, long l) {
        Logger.log(s + " 채널 구독을 해제했습니다.");
    }

}
