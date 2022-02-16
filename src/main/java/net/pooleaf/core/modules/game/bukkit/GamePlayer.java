package net.pooleaf.core.modules.game.bukkit;

import lombok.Data;
import net.pooleaf.core.modules.commonsender.CommonSenderModule;

import java.util.UUID;

@Data
public class GamePlayer {

    private UUID uuid;
    private String name;

    private Game joinedGame;


    public String getDisplayName() {
        return CommonSenderModule.getDisplayName(uuid);
    }

    public boolean hasJoinedGame() {
        return joinedGame != null;
    }

}
