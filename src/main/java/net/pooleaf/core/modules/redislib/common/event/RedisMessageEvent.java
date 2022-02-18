package net.pooleaf.core.modules.redislib.common.event;

import com.google.gson.Gson;
import lombok.Data;
import net.pooleaf.core.Core;
import net.pooleaf.core.modules.commonevent.common.CommonEvent;

import java.util.List;

@Data
public class RedisMessageEvent extends CommonEvent {

    private final String channel;

    private final String dataJson;
    private final String messageChannel;
    private final List<Object> datas;


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
