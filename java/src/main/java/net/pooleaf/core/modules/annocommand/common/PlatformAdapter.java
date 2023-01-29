package net.pooleaf.core.modules.annocommand.common;

import net.md_5.bungee.api.chat.BaseComponent;
import net.pooleaf.core.plugin.CorePlugin;

public interface PlatformAdapter {

    void init(CorePlugin plugin);

    boolean registerCommand(AnnoCommand command);

    boolean hasPermission(Object sender, String permission);

    boolean isPlayer(Object sender);

    boolean isConsole(Object sender);

    void sendMessage(Object sender, String message);

    void sendMessage(Object sender, BaseComponent... component);

}
