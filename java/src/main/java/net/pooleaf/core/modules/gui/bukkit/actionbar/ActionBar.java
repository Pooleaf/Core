package net.pooleaf.core.modules.gui.bukkit.actionbar;

import com.google.common.base.Preconditions;
import net.pooleaf.core.modules.support.bukkit.nms.NmsVersion;
import net.pooleaf.core.modules.support.bukkit.util.BukkitReflectionUtil;
import lombok.Data;
import lombok.SneakyThrows;
import net.pooleaf.core.BukkitCoreBootstrapPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ActionBar {

    private static Map<UUID, BukkitTask> showTasks = new ConcurrentHashMap<>(); // ActionBar를 계속해서 보여주는 Task


    /**
     * 플레이어에게 ActionBar를 보여줍니다.
     * @param player 대상 플레이어
     * @param message 메시지
     */
    @SneakyThrows
    public static void show(Player player, String message) {
        // 기존 Task가 있을 경우 종료
        cancelShowTask(player);

        Preconditions.checkNotNull(player);
        Preconditions.checkNotNull(message);

        message = ChatColor.translateAlternateColorCodes('&', message);

        Class<?> chatComponentClass = BukkitReflectionUtil.getNmsClass("IChatBaseComponent");
        Class<?> chatPacketClass = BukkitReflectionUtil.getNmsClass("PacketPlayOutChat");

        Object chatComponentText = BukkitReflectionUtil.getNmsClass("ChatComponentText").getConstructor(String.class).newInstance(message);
        Object chatPacket;
        if (NmsVersion.getCurrentVersion().isBefore(NmsVersion.v1_11_R1)) {
            chatPacket = chatPacketClass
                    .getConstructor(chatComponentClass, Byte.TYPE)
                    .newInstance(chatComponentText, (byte) 2);
        } else {
            Class<?> chatMessageTypeClass = BukkitReflectionUtil.getNmsClass("ChatMessageType");

            chatPacket = chatPacketClass
                    .getConstructor(chatComponentClass, chatMessageTypeClass)
                    .newInstance(chatComponentText, chatMessageTypeClass.getField("GAME_INFO").get(null));
        }

        BukkitReflectionUtil.sendPacket(player, chatPacket);
    }

    /**
     * 플레이어가 온라인인지 확인한 후 ActionBar를 보여줍니다.
     * @param player 대상 플레이어
     * @param message 메시지
     */
    public static void showSafely(Player player, String message) {
        Preconditions.checkNotNull(player);

        if (player.isOnline()) {
            show(player, message);
        }
    }

    /**
     * 플레이어에게 ActionBar를 seconds초 동안 보여줍니다.
     * 플레이어가 접속을 종료할 경우 중단됩니다.
     * @param player 대상 플레이어
     * @param message 메시지
     * @param seconds 보여줄 시간(초)
     */
    public static void show(Player player, String message, int seconds) {
      synchronized (ActionBar.class) {
          // 기존 Task가 있을 경우 종료
          cancelShowTask(player);

          // ActionBar 보여주기
          ActionBar.show(player, message);

          // 1초마다 ActionBar를 보여주는 Task 등록
          showTasks.put(player.getUniqueId(), new ShowTask(player, message, seconds).runTaskTimerAsynchronously(BukkitCoreBootstrapPlugin.getInstance(), 20L, 20L));
      }
    }

    /**
     * 플레이어에게 ActionBar를 항상 보여줍니다.
     * 플레이어가 접속을 종료할 경우 중단됩니다.
     * @param player 대상 플레이어
     * @param message 메시지
     */
    public static void showForever(Player player, String message) {
       synchronized (ActionBar.class) {
           // 기존 Task가 있을 경우 종료
           cancelShowTask(player);

           // 1초마다 ActionBar를 보여주는 Task 등록
           showTasks.put(player.getUniqueId(), new ShowTask(player, message, -1).runTaskTimerAsynchronously(BukkitCoreBootstrapPlugin.getInstance(), 0, 20L));
       }
    }

    /**
     * 플레이어의 ActionBar를 제거합니다.
     * @param player 대상 플레이어
     */
    public static void remove(Player player) {
        cancelShowTask(player);

        show(player, "");
    }

    /**
     * ActionBar을 보여주는 Task를 취소합니다.
     */
    private static void cancelShowTask(Player player) {
        if (showTasks.containsKey(player.getUniqueId())) {
            BukkitTask beforeTask = showTasks.get(player.getUniqueId());

            Bukkit.getScheduler().cancelTask(beforeTask.getTaskId());

            showTasks.remove(player.getUniqueId());
        }
    }


    /**
     * ActionBar를 계속해서 보여주는 Task 입니다.
     *
     * seconds초동안 반복됩니다.
     * seconds가 -1일 경우 무한으로 반복됩니다.
     *
     * {@link Player}가 오프라인이 될 경우 중단됩니다.
     */
    @Data
    public static class ShowTask extends BukkitRunnable {

        private final Player player;
        private final String message;
        private final int seconds; // 반복할 시간(초)

        private int currentSeconds; // 현재 시간(초)

        @Override
        public void run() {
            // 플레이어가 오프라인일 경우 중단
            if (!player.isOnline()) {
                cancel();
                return;
            }

            ActionBar.show(player, message);

            // seconds가 -1일 경우 무한 반복
            if (seconds == -1) {
                return;
            }

            currentSeconds++;
            // 시간이 끝날 경우 ActionBar 제거
            if (currentSeconds >= seconds) {
                remove(player);
            }
        }

    }

}
