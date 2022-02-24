package net.pooleaf.core.modules.game.bukkit.vote.map;

import lombok.Getter;
import net.pooleaf.core.modules.game.bukkit.game.Game;
import net.pooleaf.core.modules.game.bukkit.map.GameMap;
import net.pooleaf.core.modules.game.bukkit.player.GamePlayer;
import net.pooleaf.core.modules.game.bukkit.vote.Vote;
import net.pooleaf.core.modules.support.common.messager.Messager;

import java.util.*;
import java.util.stream.Collectors;

@Getter
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
        game.getWaitQuickBar().updateAsynchronously();
        gui.updateAsynchronously();
    }

    @Override
    protected void onUnvoted(GamePlayer gamePlayer) {
        game.broadcast("§f게임 시작 투표§e가 진행 중입니다. ( §a참여자: §f" + votedTo.size() + "§a명 §e)");
        game.getWaitQuickBar().updateAsynchronously();
        gui.updateAsynchronously();
    }

    @Override
    protected void onCancel() {
        game.setMap(getMostVotedMap());
    }

    /**
     * 투표를 가장 많이 받은 맵을 반환 합니다.
     * @return 투표를 가장 많이 받은 맵. null = 랜덤
     */
    public GameMap getMostVotedMap() {
        if (votedTo.isEmpty()) {
            return null;
        }

        Map<GameMap, Integer> amounts = new HashMap<>();
        for (Map.Entry<UUID, GameMap> entry : votedTo.entrySet()) {
            amounts.put(entry.getValue(), amounts.computeIfAbsent(entry.getValue(), (map) -> 0) + 1);
        }

        List<Map.Entry<GameMap, Integer>> list = amounts.entrySet().stream().sorted(new Comparator<Map.Entry<GameMap, Integer>>() {
            @Override
            public int compare(Map.Entry<GameMap, Integer> o1, Map.Entry<GameMap, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        }).collect(Collectors.toList());

        GameMap mostVotedMap = list.get(0).getKey();
        int mostVotedAmount = list.get(0).getValue();

        if (mostVotedAmount > getRandomCount()) { // 랜덤 투표 수보다 투표를 많이 받았을 경우
            return mostVotedMap;
        } else {
            return null;
        }
    }

    /**
     * 랜덤 투표 수를 반환합니다.
     * @return 랜덤 투표 수
     */
    public int getRandomCount() {
        int playerCount = game.getOnlinePlayingPlayers().size();
        int votedCount = votedTo.size();

        return playerCount - votedCount;
    }

    /**
     * 맵 투표 수를 반환합니다.
     * @return 맵이 투표 수
     */
    public int getVotedCount(GameMap map) {
        return (int) votedTo.values().stream()
                .filter(m -> m.equals(map))
                .count();
    }

}
