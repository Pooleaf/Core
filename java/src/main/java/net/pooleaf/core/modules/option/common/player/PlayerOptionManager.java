package net.pooleaf.core.modules.option.common.player;

import net.pooleaf.core.modules.option.OptionModule;
import net.pooleaf.core.modules.option.common.Option;
import net.pooleaf.core.modules.support.common.manager.AbstractManager;

import java.util.UUID;

public class PlayerOptionManager extends AbstractManager<UUID, Option> {

    @Override
    public Option loadNoCache(UUID key) {
        Option option = get(key);
        if (option == null) {
            option = new Option(OptionModule.getRedisManager().PLAYER_OPTION_PREFIX + key.toString());
        }

        option.load();

        return option;
    }

    public void save(UUID key) {
        if (exists(key)) {
            get(key).save();
        }
    }

}