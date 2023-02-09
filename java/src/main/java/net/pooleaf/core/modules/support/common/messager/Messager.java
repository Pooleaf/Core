package net.pooleaf.core.modules.support.common.messager;

import com.google.common.base.Preconditions;
import net.pooleaf.core.modules.commonsender.common.CommonCommandSender;
import net.pooleaf.core.modules.support.common.component.SimpleComponentBuilder;
import lombok.AccessLevel;
import lombok.Setter;
import net.md_5.bungee.api.chat.BaseComponent;

public class Messager extends Prefixer {

    @Setter(AccessLevel.PROTECTED)
    private static MessagerAdapter messagerAdapter;


    public static void sendMessage(Object sender, Object message) {
        Preconditions.checkNotNull(messagerAdapter, "messeageAdapter가 초기화되지 않았습니다.");
        Preconditions.checkNotNull(sender, "sender가 null 입니다.");

        // CommonCommandSender를 Platform에 맞는 CommandSender로 변경
        if (sender instanceof CommonCommandSender) {
            sender = ((CommonCommandSender) sender).getPlatformSender();
        }

        messagerAdapter.message(sender, message);
    }

    public static void sendMessageFormat(Object sender, Object message, Object... params) {
        sendMessage(sender, String.format((String) message, params));
    }


    public static void sendMessageWithPrefix(Object sender, Object message) {
        // BaseComponent
        if (message instanceof BaseComponent) {
            sendMessage(sender, new SimpleComponentBuilder(getCurrentPluginPrefix(" §f"))
                    .addExtra((BaseComponent) message)
                    .build());
        }
        // BaseComponent[]
        else if (message instanceof BaseComponent[]) {
            SimpleComponentBuilder builder = new SimpleComponentBuilder(getCurrentPluginPrefix(" §f"));
            for (BaseComponent component : ((BaseComponent[]) message)) {
                builder.addExtra(component);
            }
            sendMessage(sender, builder.build());
        }
        // String
        else {
            sendMessage(sender, getCurrentPluginPrefix(" §f") + message);
        }
    }

    public static void sendMessageFormatWithPrefix(Object sender, Object message, Object... params) {
        sendMessageWithPrefix(sender, String.format((String) message, params));
    }


    public static void sendWarning(Object sender, Object message) {
        // BaseComponent
        if (message instanceof BaseComponent) {
            sendMessage(sender, new SimpleComponentBuilder("§c")
                    .addExtra((BaseComponent) message)
                    .build());
        }
        // BaseComponent[]
        else if (message instanceof BaseComponent[]) {
            SimpleComponentBuilder builder = new SimpleComponentBuilder("§c");
            for (BaseComponent component : ((BaseComponent[]) message)) {
                builder.addExtra(component);
            }
            sendMessage(sender, builder.build());
        }
        // String
        else {
            sendMessage(sender, "§c" + message);
        }
    }

    public static void sendWarning(Object sender, BaseComponent... components) {
        sendWarning(sender, components);
    }

    public static void sendWarningFormat(Object sender, Object message, Object... params) {
        sendWarning(sender, String.format((String) message, params));
    }


    public static void sendWarningWithPrefix(Object sender, Object message) {
        // BaseComponent
        if (message instanceof BaseComponent) {
            sendMessage(sender, new SimpleComponentBuilder(getCurrentPluginPrefix(" §c"))
                    .addExtra((BaseComponent) message)
                    .build());
        }
        // BaseComponent[]
        else if (message instanceof BaseComponent[]) {
            SimpleComponentBuilder builder = new SimpleComponentBuilder(getCurrentPluginPrefix(" §c"));
            for (BaseComponent component : ((BaseComponent[]) message)) {
                builder.addExtra(component);
            }
            sendMessage(sender, builder.build());
        }
        // String
        else {
            sendMessage(sender, getCurrentPluginPrefix(" §c") + message);
        }
    }

    public static void sendWarningWithPrefix(Object sender, BaseComponent... components) {
        sendWarningWithPrefix(sender, components);
    }

    public static void sendWarningFormatWithPrefix(Object sender, Object message, Object... params) {
        sendWarningWithPrefix(sender, String.format((String) message, params));
    }


    public static void broadcast(Object message) {
        messagerAdapter.broadcast(message);
    }

    public static void broadcast(BaseComponent... components) {
        broadcast(components);
    }

    public static void broadcastFormat(Object message, Object... params) {
         broadcast(String.format((String) message, params));
    }

    public static void broadcastWithPrefix(Object message) {
        // BaseComponent
        if (message instanceof BaseComponent) {
            broadcast(new SimpleComponentBuilder(getCurrentPluginPrefix(" §f"))
                    .addExtra((BaseComponent) message)
                    .build());
        }
        // BaseComponent[]
        else if (message instanceof BaseComponent[]) {
            SimpleComponentBuilder builder = new SimpleComponentBuilder(getCurrentPluginPrefix(" §f"));
            for (BaseComponent component : ((BaseComponent[]) message)) {
                builder.addExtra(component);
            }
            broadcast(builder.build());
        }
        // String
        else {
            broadcast(getCurrentPluginPrefix(" §f") + message);
        }
    }

    public static void broadcastWithPrefix(BaseComponent... components) {
        broadcastWithPrefix(components);
    }

    public static void broadcastFormatWithPrefix(Object message, Object... params) {
        broadcastWithPrefix(String.format((String) message, params));
    }

}
