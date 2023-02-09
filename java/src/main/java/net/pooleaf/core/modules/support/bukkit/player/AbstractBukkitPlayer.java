package net.pooleaf.core.modules.support.bukkit.player;

import com.cryptomorin.xseries.XSound;
import net.md_5.bungee.api.chat.BaseComponent;
import net.pooleaf.core.modules.gui.bukkit.actionbar.ActionBar;
import net.pooleaf.core.modules.gui.bukkit.title.DefaultTitleBuilder;
import net.pooleaf.core.modules.gui.bukkit.title.Title;
import net.pooleaf.core.modules.support.common.player.AbstractPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AbstractBukkitPlayer extends AbstractPlayer<Player> {

    public AbstractBukkitPlayer(UUID uuid) {
        super(uuid);
    }


    public void sendMessageSafely(String message) {
        if (isOnline()) {
            getCommonPlayer().sendMessageSafely(message);
        }
    }

    public void sendMessageSafely(BaseComponent... baseComponents) {
        if (isOnline()) {
            getCommonPlayer().sendMessage(baseComponents);
        }
    }

    public void sendWarningSafely(String message) {
        if (isOnline()) {
            getCommonPlayer().sendWarning(message);
        }
    }

    public void sendWarningSafely(BaseComponent... baseComponents) {
        if (isOnline()) {
            getCommonPlayer().sendWarning(baseComponents);
        }
    }

    public void sendTitleSafely(Title title) {
        title.sendSafely((Player) getCommonPlayer().getPlatformSender());
    }

    public void sendTitleSafely(String title) {
        new DefaultTitleBuilder()
                .title(title)
                .build()
                .sendSafely((Player) getCommonPlayer().getPlatformSender());
    }

    public void sendTitleSafely(String title, String subtitle) {
        new DefaultTitleBuilder()
                .title(title)
                .subtitle(subtitle)
                .build()
                .sendSafely((Player) getCommonPlayer().getPlatformSender());
    }

    public void playSoundSafely(XSound sound) {
        if (isOnline()) {
            sound.play((Player) getCommonPlayer().getPlatformSender(), 1F, 1F);
        }
    }

    public void showActionBarSafely(String message) {
        if (isOnline()) {
            ActionBar.show((Player) getCommonPlayer().getPlatformSender(), message);
        }
    }

    public void showActionBar(String message, int seconds) {
        ActionBar.show((Player) getCommonPlayer().getPlatformSender(), message, seconds);
    }

    public void showActionBarForever(String message) {
        ActionBar.showForever((Player) getCommonPlayer().getPlatformSender(), message);
    }

    public void playSoundSafely(XSound sound, Float volume) {
        if (isOnline()) {
            sound.play((Entity) getCommonPlayer().getPlatformSender(), volume, 1F);
        }
    }

    public void playSoundSafely(XSound sound, Float volume, Float pitch) {
        if (isOnline()) {
            sound.play((Entity) getCommonPlayer().getPlatformSender(), volume, pitch);
        }
    }

}