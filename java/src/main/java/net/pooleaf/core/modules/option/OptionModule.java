package net.pooleaf.core.modules.option;

import net.pooleaf.core.modules.commonsender.CommonSenderModule;
import net.pooleaf.core.modules.option.common.Option;
import net.pooleaf.core.modules.option.common.player.PlayerOptionManager;
import net.pooleaf.core.modules.option.common.redis.OptionRedisManager;
import lombok.Getter;
import net.pooleaf.core.module.CoreModule;
import net.pooleaf.core.plugin.CorePlugin;

import java.util.UUID;

public class OptionModule extends CoreModule {

    @Getter
    private static PlayerOptionManager playerOptionManager = new PlayerOptionManager();

    @Getter
    private static OptionRedisManager redisManager;

    @Getter
    private static Option serverOption = new Option(redisManager.SERVER_OPTION_NAME);


    @Override
    public String getName() {
        return "Option";
    }

    @Override
    public String[] getDepends() {
        return new String[] { "AnnoConfig", "RedisLib", "CommonEvent" };
    }

    @Override
    public void onEnable(CorePlugin plugin) {
        redisManager = new OptionRedisManager(plugin);
        redisManager.connect();

        serverOption.load();
    }

    @Override
    public void onDisable(CorePlugin plugin) {
        redisManager.close();
    }


    public static Option getPlayerOption(UUID uuid) {
        return playerOptionManager.loadNoCache(uuid);
    }

    public static Option getPlayerOptionByName(String name) {
        UUID uuid = CommonSenderModule.getUuidByName(name);
        if (uuid == null) {
            return null;
        }

        return playerOptionManager.loadNoCache(uuid);
    }

}