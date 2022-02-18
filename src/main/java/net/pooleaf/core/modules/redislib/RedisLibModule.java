package net.pooleaf.core.modules.redislib;

import net.pooleaf.core.module.CoreModule;

public class RedisLibModule extends CoreModule {

    @Override
    public String getName() {
        return "RedisLib";
    }

    @Override
    public String[] getDepends() {
        return new String[] { "AnnoConfig", "CommonEvent" };
    }

}