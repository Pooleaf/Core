package net.pooleaf.core.modules.game.bukkit;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import net.pooleaf.core.modules.game.bukkit.exception.GameException;
import net.pooleaf.core.modules.game.bukkit.map.GameMap;
import net.pooleaf.core.modules.game.bukkit.player.GamePlayer;
import net.pooleaf.core.modules.game.bukkit.vote.map.MapVote;
import net.pooleaf.core.modules.game.bukkit.vote.start.StartVote;
import net.pooleaf.core.modules.support.common.messager.Messager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public abstract class Game<T extends GamePlayer> {

    private UUID uuid = UUID.randomUUID();
    private String name;

    @Getter(AccessLevel.NONE)
    private List<T> joinedPlayers = new ArrayList<>();
    private GameMap map;

    private boolean started;
    private LocalDateTime gameStartTime;
    private LocalDateTime gameEndTime;

    private StartVote startVote = new StartVote(this);
    private MapVote mapVote = new MapVote(this);


    public abstract void onStart(CommandSender sender) throws GameException;

    public abstract void onCancel(CommandSender sender) throws GameException;

    public abstract void onEnd();


    public boolean start(CommandSender sender) {
        try {
            if (map == null) {
                throw new GameException("게임 맵이 지정되지 않았습니다.");
            }

            onStart(sender);

            // 게임 시작 기록
            started = true;
            gameStartTime = LocalDateTime.now();

            // 맵 제한기 시작
            if (map.getLimiter() != null) {
                map.getLimiter().start();
            }

            return true;
        } catch (GameException e) {
            Messager.warning(sender, e.getMessage());
            return false;
        }
    }

    public boolean cancel(CommandSender sender) {
        try {
            onCancel(sender);
            reset();

            return true;
        } catch (GameException e) {
            Messager.warning(sender, e.getMessage());
            return false;
        }
    }

    public void end() {
        onEnd();
    }

    public void reset() {
        // 게임 ID 변경
        uuid = UUID.randomUUID();
        started = false;

        // 시간 초기화
        gameStartTime = null;
        gameEndTime = null;

        // 관전 해제
        joinedPlayers.forEach(gamePlayer -> gamePlayer.setObserver(false));

        // 오프라인 플레이어 제거
        joinedPlayers.stream()
                .filter(gamePlayer -> !gamePlayer.isOnline())
                .forEach(gamePlayer -> joinedPlayers.remove(gamePlayer));

        // 맵 초기화
        map.reset();

        // 맵 제한기 정지
        if (map.getLimiter() != null) {
            map.getLimiter().stop();
        }
        map = null;
    }


    public void join(Player player) {
        if (joinedPlayers.contains(player.getUniqueId())) {
            return;
        }

        GamePlayer gamePlayer = new GamePlayer(player.getUniqueId(), player.getName(), this);
        joinedPlayers.add((T) gamePlayer);
    }

    public void left(Player player) {

    }

    public List<T> getObservers() {
        return joinedPlayers.stream()
                .filter(GamePlayer::isObserver)
                .collect(Collectors.toList());
    }

    public List<T> getOnlineObservers() {
        return joinedPlayers.stream()
                .filter(GamePlayer::isObserver)
                .filter(GamePlayer::isOnline)
                .collect(Collectors.toList());
    }

    public List<T> getPlayingPlayers() {
        return joinedPlayers.stream()
                .filter(gp -> !gp.isObserver())
                .collect(Collectors.toList());
    }

    public List<T> getOnlinePlayingPlayers() {
        return joinedPlayers.stream()
                .filter(gp -> !gp.isObserver())
                .filter(GamePlayer::isOnline)
                .collect(Collectors.toList());
    }

    public List<T> getPlayers() {
        return joinedPlayers;
    }

    public List<T> getOnlinePlayers() {
        return joinedPlayers.stream()
                .filter(GamePlayer::isOnline)
                .collect(Collectors.toList());
    }

    public void broadcast(Object message) {
        for (T onlinePlayer : getOnlinePlayers()) {
            Messager.message(onlinePlayer.getPlayer(), message);
        }
    }

}
