package net.pooleaf.core.modules.game.bukkit.game;

import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import net.pooleaf.core.modules.game.GameModule;
import net.pooleaf.core.modules.game.bukkit.map.GameMap;
import net.pooleaf.core.modules.game.bukkit.phase.PhasePipeline;
import net.pooleaf.core.modules.game.bukkit.phases.StartCountPhase;
import net.pooleaf.core.modules.game.bukkit.player.GamePlayer;
import net.pooleaf.core.modules.game.bukkit.quickbar.ObserverQuickBar;
import net.pooleaf.core.modules.game.bukkit.quickbar.WaitQuickBar;
import net.pooleaf.core.modules.game.bukkit.vote.map.MapVote;
import net.pooleaf.core.modules.game.bukkit.vote.start.StartVote;
import net.pooleaf.core.modules.gui.bukkit.title.Title;
import net.pooleaf.core.modules.support.bukkit.util.TeleportUtil;
import net.pooleaf.core.modules.support.common.messager.Messager;
import net.pooleaf.core.plugin.CorePlugin;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public abstract class Game<T extends GamePlayer> {

    private final CorePlugin plugin;

    /**
     * 게임 기본 정보
     */

    private UUID uuid = UUID.randomUUID(); // 게임 ID
    private String name; // 게임 이름

    private GameConfig config = new GameConfig(); // 게임 설정

    @Getter(AccessLevel.NONE)
    private List<T> joinedPlayers = new ArrayList<>(); // 게임에 참여한 플레이어

    private Location spawnLocation; // 스폰 위치

    /**
     * 게임 상태
     */

    private GameMap map; // 게임 맵

    private boolean prepareStarted; // 게임 카운트 시작 여부
    private boolean started; // 게임 시작 여부
    private LocalDateTime gameStartTime; // 게임 시작 시간
    private LocalDateTime gameEndTime; // 게임 종료 시간

    private PhasePipeline phasePipeline; // 게임 단계 파이프라인

    /**
     * 투표
     */

    private StartVote startVote = new StartVote(this); // 시작 투표
    private MapVote mapVote = new MapVote(this); // 맵 투표

    /**
     * 퀵바
     */

    private WaitQuickBar waitQuickBar = new WaitQuickBar(this);
    private ObserverQuickBar observerQuickBar = new ObserverQuickBar(this);


    public Game(CorePlugin plugin) {
        this.plugin = plugin;

        this.phasePipeline = new PhasePipeline(plugin, this);
    }


    public abstract void onPrepareStart();

    public abstract void onStart();

    public abstract void onCancel();

    public abstract void onEnd();

    public void onJoin(GamePlayer gamePlayer) {
    }

    public void onLeft(GamePlayer gamePlayer) {
    }

    public abstract void sendToLobbyChannel(Player player);


    public boolean start() {
        if (prepareStarted || started) {
            return false;
        }

        // 투표 중단
        startVote.cancel();
        mapVote.cancel();

        // 카운트 단계가 있을 경우 게임 준비 시작
        if (phasePipeline != null && phasePipeline.existsPhase(StartCountPhase.class)) {
            prepareStarted = true;
            onPrepareStart();
        }
        // 카운트 단계 없으면 바로 시작
        else {
            startSetting();
        }

        // 단계 시작
        if (phasePipeline != null) {
            phasePipeline.nextPhase().start();
        }

        return true;
    }

    public void startSetting() {
        prepareStarted = false;
        started = true;
        gameStartTime = LocalDateTime.now();

        // 오프라인 플레이어 제거
        joinedPlayers.removeIf(gamePlayer -> !gamePlayer.isOnline()); // Game에서 삭제

        // 투표한 맵으로 설정
        map = mapVote.getMostVotedMap();
        if (map == null) { // 투표한 맵이 없거나 랜덤일 경우
            map = GameModule.getGameMapManager().getRandomMap(); // 랜덤 맵 설정
        }

        // 맵 제한기 시작
        if (map.getLimiter() != null) {
            map.getLimiter().start();
        }

        // 맵으로 텔레포트
        teleportToMapAll();

        onStart();
    }

    public boolean cancel() {
        if (!(prepareStarted || started)) {
            return false;
        }

        onCancel();
        reset();

        return true;
    }

    public void end() {
        gameEndTime = LocalDateTime.now();
        onEnd();
    }

    public void reset() {
        // 게임 ID 변경
        uuid = UUID.randomUUID();
        prepareStarted = false;
        started = false;

        // 시간 초기화
        gameStartTime = null;
        gameEndTime = null;

        // 오프라인 플레이어 제거
        joinedPlayers.stream()
                .filter(gamePlayer -> !gamePlayer.isOnline())
                .forEach(gamePlayer -> GameModule.getGamePlayerManager().remove(gamePlayer.getUuid())); // GamePlayerManager에서 삭제

        joinedPlayers.removeIf(gamePlayer -> !gamePlayer.isOnline()); // Game에서 삭제

        // 플레이어 초기화
        for (T joinedPlayer : joinedPlayers) {
            // 탈락 해제
            joinedPlayer.setDropout(false);

            // 관전 해제
            joinedPlayer.setObserver(false);

            // 스폰으로 이동
            teleportToSpawnLocation(joinedPlayer.getPlayer());

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

    public void join(Player player) {
        if (joinedPlayers.contains(player.getUniqueId())) {
            return;
        }

        GamePlayer gamePlayer = GameModule.getGamePlayerManager().get(player.getUniqueId());

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

        onJoin(gamePlayer);
    }

    public void left(Player player) {
        GamePlayer gamePlayer = GameModule.getGamePlayerManager().get(player.getUniqueId());

        // TODO

        onLeft(gamePlayer);
    }

    public void teleportToSpawnLocation(Player player) {
        Preconditions.checkNotNull(spawnLocation, "스폰 위치가 설정되지 않았습니다.");

        TeleportUtil.teleport(player, spawnLocation);
    }

    public void teleportToMapAll() {
        for (T onlinePlayer : getOnlinePlayers()) {
            TeleportUtil.teleport(onlinePlayer.getPlayer(), map.getLocation());
        }
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

    public void broadcastTitle(Title title) {
        for (T onlinePlayer : getOnlinePlayers()) {
            title.send(onlinePlayer.getPlayer());
        }
    }

    public void broadcastSound(Sound sound, float volume, float pitch) {
        for (T onlinePlayer : getOnlinePlayers()) {
            onlinePlayer.getPlayer().playSound(onlinePlayer.getPlayer().getLocation(), sound, volume, pitch);
        }
    }

}