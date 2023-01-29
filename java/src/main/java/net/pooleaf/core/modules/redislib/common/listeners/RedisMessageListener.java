package net.pooleaf.core.modules.redislib.common.listeners;

import net.pooleaf.core.modules.redislib.common.events.RedisMessageEvent;
import net.pooleaf.core.modules.support.common.logger.Logger;
import io.lettuce.core.pubsub.RedisPubSubListener;
import net.pooleaf.core.modules.commonevent.CommonEventModule;
import net.pooleaf.core.modules.support.common.debugger.Debugger;

public class RedisMessageListener implements RedisPubSubListener<String, String> {

    @Override
    public void message(String s, String s2) {
        RedisMessageEvent event = new RedisMessageEvent(s, s2);
        Debugger.log(event);

        CommonEventModule.callEvent(event);
    }

    @Override
    public void message(String s, String k1, String s2) {
    }

    @Override
    public void subscribed(String s, long l) {
        Logger.log(s + " 채널을 구독했습니다.");
    }

    @Override
    public void psubscribed(String s, long l) {
    }

    @Override
    public void unsubscribed(String s, long l) {
        Logger.log(s + " 채널 구독을 해제했습니다.");
    }

    @Override
    public void punsubscribed(String s, long l) {
    }

}
