package net.pooleaf.core.modules.annocommand.bungee;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.pooleaf.core.modules.annocommand.common.AnnoCommand;
import net.pooleaf.core.modules.annocommand.common.PlatformAdapter;
import net.pooleaf.core.modules.commonsender.common.CommonCommandSender;
import net.pooleaf.core.modules.commonsender.common.CommonPlayer;
import net.pooleaf.core.plugin.CorePlugin;

public class BungeePlatformAdapter implements PlatformAdapter {

    private Plugin plugin;


    @Override
    public void init(CorePlugin plugin) {
        this.plugin = (Plugin) plugin;
    }

    @Override
    public boolean registerCommand(AnnoCommand command) {
        Class firstParameterType = command.getExecuteMethod().getParameterTypes()[0];

        /* Player Only */
        if (firstParameterType.equals(ProxiedPlayer.class) || CommonPlayer.class.isAssignableFrom(firstParameterType)) {
            command.setPlayerOnly(true);
        }
        /* Console Only */
        else if (firstParameterType.equals(CommandSender.class) || CommonCommandSender.class.isAssignableFrom(firstParameterType)) {
            command.setConsoleOnly(true);
        }
        /* Invalid Parameter Type */
        else if (!firstParameterType.isAssignableFrom(CommandSender.class)) return false;

        /* Register to BungeeCord */
        if (!command.hasParent()) {
            BungeeCommandExecutor bungeeCommand = new BungeeCommandExecutor(command.getName().get(0), command.getPermission(), command.getName().toArray(new String[0]));

            ProxyServer.getInstance().getPluginManager().registerCommand(plugin, bungeeCommand);
        }

        return true;
    }

    @Override
    public boolean hasPermission(Object sender, String permission) {
        return ((CommandSender) sender).hasPermission(permission);
    }

    @Override
    public boolean isPlayer(Object sender) {
        return sender instanceof ProxiedPlayer;
    }

    @Override
    public boolean isConsole(Object sender) {
        return (sender instanceof CommandSender) && !isPlayer(sender);
    }

    @Override
    public void sendMessage(Object sender, String message) {
        ((CommandSender) sender).sendMessage(message);
    }

    @Override
    public void sendMessage(Object sender, BaseComponent... component) {
        ((CommandSender) sender).sendMessage(component);
    }

}
