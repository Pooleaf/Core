package net.pooleaf.core.modules.option.common.listeners;

import net.pooleaf.core.modules.commonevent.common.CommonEventHandler;
import net.pooleaf.core.modules.commonevent.common.CommonEventListener;
import net.pooleaf.core.modules.option.OptionModule;
import net.pooleaf.core.modules.redislib.common.events.RedisKeySpaceEvent;

import java.util.UUID;

public class OptionUpdateListener implements CommonEventListener {

    @CommonEventHandler
    public void onChannelUpdate(RedisKeySpaceEvent event) {
        // 서버 Option 업데이트
        if (event.getKey().startsWith(OptionModule.getRedisManager().SERVER_OPTION_NAME)) {
            OptionModule.getServerOption().load();
        }

        // 플레이어 Option 업데이트
        else if (event.getKey().startsWith(OptionModule.getRedisManager().PLAYER_OPTION_PREFIX)) {
            String uuidString = event.getKey().substring(OptionModule.getRedisManager().PLAYER_OPTION_PREFIX.length());
            UUID uuid = UUID.fromString(uuidString);

            if (OptionModule.getPlayerOptionManager().exists(uuid)) {
                OptionModule.getPlayerOptionManager().load(uuid);
            }
        }
    }

}