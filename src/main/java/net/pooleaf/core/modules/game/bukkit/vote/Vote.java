package net.pooleaf.core.modules.game.bukkit.vote;

import lombok.Data;
import net.pooleaf.core.modules.game.bukkit.game.Game;
import net.pooleaf.core.modules.game.bukkit.player.GamePlayer;
import net.pooleaf.core.modules.support.common.messager.Messager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
public abstract class Vote<T> {

    protected final Game game;

    protected Map<UUID, T> votedTo = new HashMap<>();


    protected boolean onVote(GamePlayer gamePlayer, T vote) {
        return true;
    }

    protected void onVoted(GamePlayer gamePlayer, T vote) {}

    protected boolean onUnvote(GamePlayer gamePlayer) {
        return true;
    }

    protected void onUnvoted(GamePlayer gamePlayer) {}

    protected void onEnd() {}

    protected void onCancel() {}

    protected void onReset() {}


    public final void vote(GamePlayer gamePlayer, T vote) {
        if (!game.isJoined(gamePlayer)) {
            Messager.message(gamePlayer.getPlayer(), "참여 중이 아닌 게임에는 투표할 수 없습니다.");
            return;
        }

        if (!votedTo.containsKey(gamePlayer.getUuid()) && onVote(gamePlayer, vote)) {
            votedTo.put(gamePlayer.getUuid(), vote);
            onVoted(gamePlayer, vote);
        }
    }

    public final void unvote(GamePlayer gamePlayer) {
        if (!game.isJoined(gamePlayer)) {
            Messager.message(gamePlayer.getPlayer(), "참여 중이 아닌 게임에는 투표할 수 없습니다.");
            return;
        }

        if (votedTo.containsKey(gamePlayer.getUuid()) && onUnvote(gamePlayer)) {
            votedTo.remove(gamePlayer.getUuid());
            onUnvoted(gamePlayer);
        }
    }

    public final boolean isVoted(GamePlayer gamePlayer) {
        return votedTo.containsKey(gamePlayer.getUuid());
    }

    public final void end() {
        onEnd();
        reset();
    }

    public final void cancel() {
        onCancel();
        reset();
    }

    public final void reset() {
        onReset();
        votedTo.clear();
    }

}
