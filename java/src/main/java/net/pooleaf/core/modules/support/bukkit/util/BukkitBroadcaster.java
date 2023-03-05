package net.pooleaf.core.modules.support.bukkit.util;

import com.cryptomorin.xseries.XSound;
import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.chat.BaseComponent;
import net.pooleaf.core.modules.gui.bukkit.actionbar.ActionBar;
import net.pooleaf.core.modules.gui.bukkit.title.DefaultTitleBuilder;
import net.pooleaf.core.modules.gui.bukkit.title.Title;
import net.pooleaf.core.modules.gui.bukkit.title.TitleBuilder;
import org.bukkit.Bukkit;

@UtilityClass
public class BukkitBroadcaster {

    /**
     * Chat
     */

    public static void broadcast(String message) {
        Bukkit.broadcastMessage(message);
    }

    public static void broadcast(BaseComponent component) {
        Bukkit.broadcast(component);
    }

    public static void broadcastWarning(String message) {
        broadcast("§c" + message);
    }

    public static void broadcastTitle(Title title) {
        Bukkit.getOnlinePlayers().forEach(player -> title.send(player));
    }

    public static void broadcastTitle(String title) {
        TitleBuilder titleBuilder = new DefaultTitleBuilder().title(title);
        broadcastTitle(titleBuilder.build());
    }

    public static void broadcastTitle(String title, String subtitle) {
        TitleBuilder titleBuilder = new DefaultTitleBuilder().title(title).subtitle(subtitle);
        broadcastTitle(titleBuilder.build());
    }

    public static void broadcastTitle(String title, String subtitle, Integer stayTick) {
        TitleBuilder titleBuilder = new DefaultTitleBuilder().title(title).subtitle(subtitle);

        if (stayTick != null) {
            titleBuilder.stay(stayTick);
        }

        broadcastTitle(titleBuilder.build());
    }

    public static void broadcastTitle(String title, String subtitle, Integer stayTick, Integer fadeInTick) {
        TitleBuilder titleBuilder = new DefaultTitleBuilder().title(title).subtitle(subtitle);

        if (stayTick != null) {
            titleBuilder.stay(stayTick);
        }

        if (fadeInTick != null) {
            titleBuilder.fadeIn(fadeInTick);
        }

        broadcastTitle(titleBuilder.build());
    }

    public static void broadcastTitle(String title, String subtitle, Integer stayTick, Integer fadeInTick, Integer fadeOutTick) {
        TitleBuilder titleBuilder = new DefaultTitleBuilder().title(title).subtitle(subtitle);

        if (stayTick != null) {
            titleBuilder.stay(stayTick);
        }

        if (fadeInTick != null) {
            titleBuilder.fadeIn(fadeInTick);
        }

        if (fadeOutTick != null) {
            titleBuilder.fadeOut(fadeOutTick);
        }

        broadcastTitle(titleBuilder.build());
    }

    /**
     * ActionBar
     */

    public static void broadcastActionBar(String message) {
        Bukkit.getOnlinePlayers().forEach(player -> ActionBar.show(player, message));
    }

    public static void broadcastActionBar(String message, int seconds) {
        Bukkit.getOnlinePlayers().forEach(player -> ActionBar.show(player, message, seconds));
    }

    public static void broadcastActionBarForever(String message) {
        Bukkit.getOnlinePlayers().forEach(player -> ActionBar.showForever(player, message));
    }

    public static void broadcastWaitingActionBar(int currentJoinedCount, int startPlayerCount) {
        broadcastActionBarForever("§e다른 플레이어를 기다리는 중입니다. §f(" + currentJoinedCount + "/" + startPlayerCount + ")");
    }

    public static void removeActionBar() {
        Bukkit.getOnlinePlayers().forEach(player -> ActionBar.remove(player));
    }

    /**
     * Sound
     */

    public static void broadcastSound(XSound sound, float volume, float pitch) {
        Bukkit.getOnlinePlayers().forEach(player -> sound.play(player, volume, pitch));
    }

}