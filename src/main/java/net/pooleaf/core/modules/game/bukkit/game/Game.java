package net.pooleaf.core.modules.game.bukkit.game;

import com.mojang.brigadier.Message;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import net.pooleaf.core.modules.game.GameModule;
import net.pooleaf.core.modules.game.bukkit.exception.GameException;
import net.pooleaf.core.modules.game.bukkit.map.GameMap;
import net.pooleaf.core.modules.game.bukkit.player.GamePlayer;
import net.pooleaf.core.modules.game.bukkit.quickbar.ObserverQuickBar;
import net.pooleaf.core.modules.game.bukkit.quickbar.WaitQuickBar;
import net.pooleaf.core.modules.game.bukkit.vote.map.MapVote;
import net.pooleaf.core.modules.game.bukkit.vote.start.StartVote;
import net.pooleaf.core.modules.support.bukkit.util.TeleportUtil;
import net.pooleaf.core.modules.support.common.messager.Messager;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public abstract class Game<T extends GamePlayer> {

    private UUID uuid = UUID.randomUUID(); // 게임 ID
    private String name; // 게임 이름

    private GameConfig config = new GameConfig(); // 게임 설정

    @Getter(AccessLevel.NONE)
    private List<T> joinedPlayers = new ArrayList<>(); // 게임에 참여한 플레이어

    private Location spawnLocation; // 스폰 위치
    private GameMap map; // 게임 맵

    private boolean prepareStarted; // 게임 카운트 시작 여부
    private boolean started; // 게임 시작 여부
    private LocalDateTime gameStartTime; // 게임 시작 시간
    private LocalDateTime gameEndTime; // 게임 종료 시간

    private StartVote startVote = new StartVote(this); // 시작 투표
    private MapVote mapVote = new MapVote(this); // 맵 투표

    private WaitQuickBar waitQuickBar = new WaitQuickBar(this);
    private ObserverQuickBar observerQuickBar = new ObserverQuickBar(this);


    public abstract void onStart(CommandSender sender) throws GameException;

    public abstract void onCancel(CommandSender sender) throws GameException;

    public abstract void onEnd();

    public abstract void sendToLobbyChannel(Player player);


    public boolean start(CommandSender sender) { // TODO 게임 준비 prepare 만들어야함
        try {
            onStart(sender);

            // 투표 중단
            startVote.cancel();
            mapVote.cancel();

            // 게임 시작 기록
            started = true;
            gameStartTime = LocalDateTime.now();

            // 투표한 맵으로 설정
            map = mapVote.getMostVotedMap();
            if (map == null) { // 투표한 맵이 없거나 랜덤일 경우
                map = GameModule.getGameMapManager().getRandomMap(); // 랜덤 맵 설정
            }

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

        // 오프라인 플레이어 제거
        joinedPlayers.removeIf(gamePlayer -> !gamePlayer.isOnline());

        // 플레이어 초기화
        for (T joinedPlayer : joinedPlayers) {
            // 탈락 해제
            joinedPlayer.setDropout(false);

            // 관전 해제
            joinedPlayer.setObserver(false);

            // 스폰으로 이동
            if (spawnLocation != null) {
                TeleportUtil.teleport(joinedPlayer.getPlayer(), spawnLocation);
            } else {
                Messager.broadcast("§c[경고] 스폰 장소가 설정되지 않았습니다.");
            }

            // 대기 퀵바
            if (config.isUseWaitQuickBar()) {
                waitQuickBar.setTo(joinedPlayer.getPlayer());
            }
        }

        // 맵 초기화
        map.reset();

        // 맵 제한기 정지
        if (map.getLimiter() != null) {
            map.getLimiter().stop();
        }
        map = null;
    }

    /**
     * @return true일 경우 참여 허용, false일 경우 참여 불가
     */
    public boolean onJoin(GamePlayer gamePlayer) {
        return true;
    }

    public void join(Player player) {
        if (joinedPlayers.contains(player.getUniqueId())) {
            return;
        }

        GamePlayer gamePlayer = GameModule.getGamePlayerManager().get(player.getUniqueId());
        if (onJoin(gamePlayer)) {
            joinedPlayers.add((T) gamePlayer);

            // 대기 중일 경우
            if (!started) {
                // 대기 퀵바
                if (config.isUseWaitQuickBar()) {
                    waitQuickBar.setTo(gamePlayer.getPlayer());
                }
            }
            // 게임 중일 경우
            else {
                // 관전 퀵바
                if (config.isUseObserverQuickBar()) {
                    observerQuickBar.setTo(gamePlayer.getPlayer());
                }
            }
        }
    }

    public void left(Player player) {
        // TODO
    }

    /**
     * 플레이어의 게임 참여 여부를 반환합니다.
     * @return 플레이어의 게임 참여 여부
     */
    public boolean isJoined(Player player) {
        GamePlayer gamePlayer = GameModule.getGamePlayerManager().get(player.getUniqueId());
        return isJoined(gamePlayer);
    }

    public boolean isJoined(GamePlayer gamePlayer) {
        return joinedPlayers.contains(gamePlayer);
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
