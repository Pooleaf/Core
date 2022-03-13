package net.pooleaf.core.modules.redislib;

import net.pooleaf.core.Core;
import net.pooleaf.core.module.CoreModule;
import net.pooleaf.core.plugin.CorePlugin;

public class RedisLibModule extends CoreModule {

    @Override
    public String getName() {
        return "RedisLib";
    }

    @Override
    public String[] getDepends() {
        return new String[] { "AnnoConfig", "CommonEvent" };
    }

    @Override
    public void onEnable(CorePlugin plugin) {
        Core.getRedisManager().connect();
    }

}