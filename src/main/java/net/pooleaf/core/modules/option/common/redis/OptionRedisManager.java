package net.pooleaf.core.modules.option.common.redis;

import net.pooleaf.core.Core;
import net.pooleaf.core.modules.redislib.common.AbstractRedisManager;
import net.pooleaf.core.plugin.CorePlugin;

import java.io.File;

public class OptionRedisManager extends AbstractRedisManager {

    public static final String PLAYER_OPTION_PREFIX = "player_option:";
    public static final String SERVER_OPTION_NAME = "server_option";

    private OptionDao optionDao = new OptionDao(this);


    public OptionRedisManager(CorePlugin plugin) {
        super(plugin);

        getConfig().setFile(new File(Core.getPlugin().getDataFolder(), "option-redis-config.yml"));
    }


    public OptionDao option() {
        return optionDao;
    }

}