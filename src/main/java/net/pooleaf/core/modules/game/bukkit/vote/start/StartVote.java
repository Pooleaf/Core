package net.pooleaf.core.modules.game.bukkit.vote.start;

import net.pooleaf.core.modules.game.bukkit.Game;
import net.pooleaf.core.modules.game.bukkit.player.GamePlayer;
import net.pooleaf.core.modules.game.bukkit.vote.Vote;
import net.pooleaf.core.modules.support.common.messager.Messager;

public class StartVote extends Vote<Boolean> {

    private StartVoteGui gui = new StartVoteGui(this);


    public StartVote(Game game) {
        super(game);
    }


    @Override
    protected boolean onVote(GamePlayer gamePlayer, Boolean vote) {
        if (game.isStarted()) {
            Messager.warning(gamePlayer.getPlayer(), "게임 중에는 게임 시작 투표를 진행할 수 없습니다.");
            return false;
        }

        return true;
    }

    @Override
    protected void onVoted(GamePlayer gamePlayer, Boolean vote) {
        game.broadcast("§f게임 시작 투표§e가 진행 중입니다. ( §a찬성: §f" + agreeCount() + "§a명 §e| §c반대: §f" + disagreeCount() + "명§e )");
        // TODO 대기 퀵바 업데이트
        // TODO GUI 업데이트

        if (agreeCount() > Math.ceil(game.getOnlinePlayingPlayers().size() / 2)) {
            end();
        }
    }

    @Override
    public void onUnvoted(GamePlayer gamePlayer) {
        game.broadcast("§f게임 시작 투표§e가 진행 중입니다. ( §a찬성: §f" + agreeCount() + "§a명 §e| §c반대: §f" + disagreeCount() + "명§e )");
        // TODO 대기 퀵바 업데이트
        // TODO GUI 업데이트
    }

    @Override
    protected void onEnd() {
        if (!game.isStarted()) {
            game.start(null);
        }
    }

    public int agreeCount() {
        return (int) votedTo.values().stream().filter(agree -> agree).count();
    }

    public int disagreeCount() {
        return (int) votedTo.values().stream().filter(agree -> !agree).count();
    }

}
