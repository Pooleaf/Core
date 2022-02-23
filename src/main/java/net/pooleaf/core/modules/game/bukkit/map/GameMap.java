package net.pooleaf.core.modules.game.bukkit.map;

import lombok.Data;
import net.pooleaf.core.modules.annoconfig.common.anno.ConfigExclude;
import org.bukkit.Location;

@Data
public class GameMap {

    protected String name;
    protected Location location;

    @ConfigExclude
    protected GameMapLimiter limiter;


    public void reset() {
        // TODO 맵 초기화
    }

}
