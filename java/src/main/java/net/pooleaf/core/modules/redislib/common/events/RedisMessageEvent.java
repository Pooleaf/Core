package net.pooleaf.core.modules.redislib.common.events;

import com.google.gson.Gson;
import net.pooleaf.core.modules.commonevent.common.CommonEvent;
import lombok.Data;
import net.pooleaf.core.Core;

import java.util.List;

@Data
public class RedisMessageEvent extends CommonEvent {

    private final String channel; // Redis PubSub 채널

    private final String dataJson; // 가공 전 데이터 Json
    private final String messageChannel; // 플러그인을 구별하기 위한 메시지 채널
    private final List<Object> datas; // 데이터


    public RedisMessageEvent(String channel, String dataJson) {
        this.channel = channel;
        this.dataJson = dataJson;

        Object[] temp = new Gson().fromJson(dataJson, Object[].class);
        this.messageChannel = (String) temp[0];
        this.datas = (List<Object>) temp[1];
    }


    public boolean isBungeeCordChannel() {
        return channel.equals(Core.getRedisManager().BUNGEECORD_CHANNEL);
    }

    public boolean isBroadcastChannel() {
        return channel.equals(Core.getRedisManager().BROADCAST_CHANNEL);
    }

}
