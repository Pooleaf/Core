package net.pooleaf.core.modules.game.bukkit.vote.map;

import net.pooleaf.core.modules.game.bukkit.Game;
import net.pooleaf.core.modules.game.bukkit.map.GameMap;
import net.pooleaf.core.modules.game.bukkit.player.GamePlayer;
import net.pooleaf.core.modules.game.bukkit.vote.Vote;
import net.pooleaf.core.modules.support.common.messager.Messager;

public class MapVote extends Vote<GameMap> {

    private MapVoteGui gui = new MapVoteGui(this);


    public MapVote(Game game) {
        super(game);
    }


    @Override
    protected boolean onVote(GamePlayer gamePlayer, GameMap vote) {
        if (game.isStarted()) {
            Messager.warning(gamePlayer.getPlayer(), "게임 중에는 게임 시작 투표를 진행할 수 없습니다.");
            return false;
        }

        return true;
    }

    @Override
    protected void onVoted(GamePlayer gamePlayer, GameMap vote) {
        game.broadcast("§f게임 시작 투표§e가 진행 중입니다. ( §a참여자: §f" + votedTo.size() + "§a명 §e)");
        // TODO 대기 퀵바 업데이트
        // TODO GUI 업데이트
    }

    @Override
    public void onUnvoted(GamePlayer gamePlayer) {
        game.broadcast("§f게임 시작 투표§e가 진행 중입니다. ( §a참여자: §f" + votedTo.size() + "§a명 §e)");
        // TODO 대기 퀵바 업데이트
        // TODO GUI 업데이트
    }

    @Override
    protected void onCancel() {
        // TODO 투표한 맵으로 게임 맵 설정
    }

}
