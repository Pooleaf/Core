package net.pooleaf.core.modules.game.bukkit.player;

import lombok.Data;
import net.pooleaf.core.modules.commonsender.CommonSenderModule;
import net.pooleaf.core.modules.game.bukkit.game.Game;
import net.pooleaf.core.modules.support.common.player.AbstractPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Data
public class GamePlayer extends AbstractPlayer<Player> {

    private final Game joinedGame; // 참여한 게임

    private boolean observer; // 관전
    private boolean allowTeleportForObserver; // 관전자에게 텔레포트 허용

    private boolean dropout; // 탈락


    public GamePlayer(UUID uuid, Game joinedGame) {
        super(uuid);

        this.joinedGame = joinedGame;
    }


    public boolean hasJoinedGame() {
        return joinedGame != null;
    }

}
