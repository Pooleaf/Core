package net.pooleaf.core.modules.game.bukkit.map;

import lombok.Data;

@Data
public abstract class MapLimiter {

    private GameMap map;


    protected abstract void onStart();

    protected abstract void onStop();

    public void start() {
        onStart();
    }

    public void stop() {
        onStop();
    }

}
