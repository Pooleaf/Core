package net.pooleaf.core.modules.game.bukkit.player;

import lombok.Data;
import net.pooleaf.core.modules.commonsender.CommonSenderModule;
import net.pooleaf.core.modules.game.bukkit.Game;
import org.bukkit.Bukkit;

import java.util.UUID;

@Data
public class GamePlayer {

    private final UUID uuid;
    private final String name;
    private final Game joinedGame; // 참여한 게임

    private boolean observer; // 관전
    private boolean allowTeleportForObserver; // 관전자에게 텔레포트 허용


    public String getDisplayName() {
        return CommonSenderModule.getDisplayName(uuid);
    }

    public boolean isOnline() {
        return Bukkit.getPlayer(uuid) != null;
    }

    public boolean hasJoinedGame() {
        return joinedGame != null;
    }

}
