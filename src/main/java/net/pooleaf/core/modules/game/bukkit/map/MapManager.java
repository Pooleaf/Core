package net.pooleaf.core.modules.game.bukkit.map;

import net.pooleaf.core.modules.support.common.manager.AbstractManager;
import net.pooleaf.core.modules.support.common.util.NumberUtil;

public class MapManager<T extends GameMap> extends AbstractManager<String, T> {

    public T getRandomMap() {
        return (T) datas.values().toArray()[NumberUtil.random(datas.size()) - 1];
    }

}
