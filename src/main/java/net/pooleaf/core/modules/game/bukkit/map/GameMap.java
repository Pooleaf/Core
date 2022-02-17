package net.pooleaf.core.modules.game.bukkit.map;

import lombok.Data;
import org.bukkit.Location;

@Data
public class GameMap {

    protected String name;
    protected Location location;

    protected MapLimiter limiter;

}
