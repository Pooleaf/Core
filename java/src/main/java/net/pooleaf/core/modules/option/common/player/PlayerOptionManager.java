package net.pooleaf.core.modules.option.common.player;

import net.pooleaf.core.modules.commonsender.CommonSenderModule;
import net.pooleaf.core.modules.commonsender.common.CommonPlayer;
import net.pooleaf.core.modules.option.OptionModule;
import net.pooleaf.core.modules.option.common.Option;
import net.pooleaf.core.modules.support.common.manager.AbstractEhcacheManager;
import net.pooleaf.core.modules.support.common.manager.LoadableManager;

import java.util.UUID;

public class PlayerOptionManager extends AbstractEhcacheManager<UUID, Option> implements LoadableManager<UUID, Option> {

    // 오프라인 플레이어 미접근 시 남아있을 시간
    private static final int IDLE_SECONDS = 60;


    @Override
    public Option loadWithoutCache(UUID key) {
        Option option = get(key);

        if (option == null) {
            option = new Option(OptionModule.getRedisManager().PLAYER_OPTION_PREFIX + key.toString());
        }

        option.load();

        return option;
    }

    @Override
    public Option load(UUID key) {
        Option option = LoadableManager.super.load(key);

        if (option != null) {
            CommonPlayer commonPlayer = CommonSenderModule.getOnlinePlayer(key);
            if (commonPlayer == null || !commonPlayer.isOnline()) {
                setTimeToLive(key, IDLE_SECONDS);
            }
        }

        return option;
    }

    public void save(UUID key) {
        if (exists(key)) {
            get(key).save();
        }
    }

}