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


    public static void nmessage(Object sender, Object message) {
        Preconditions.checkNotNull(messagerAdapter, "messeageAdapter가 초기화되지 않았습니다.");
        Preconditions.checkNotNull(sender, "sender가 null 입니다.");

        // CommonCommandSender를 Platform에 맞는 CommandSender로 변경
        if (sender instanceof CommonCommandSender) {
            sender = ((CommonCommandSender) sender).getPlatformSender();
        }

        messagerAdapter.message(sender, message);
    }

    public static void nmessageFormat(Object sender, Object message, Object... params) {
        nmessage(sender, String.format((String) message, params));
    }


    public static void message(Object sender, Object message) {
        // BaseComponent
        if (message instanceof BaseComponent) {
            nmessage(sender, new SimpleComponentBuilder(getCurrentPluginPrefix(" §f"))
                    .addExtra((BaseComponent) message)
                    .build());
        }
        // BaseComponent[]
        else if (message instanceof BaseComponent[]) {
            SimpleComponentBuilder builder = new SimpleComponentBuilder(getCurrentPluginPrefix(" §f"));
            for (BaseComponent component : ((BaseComponent[]) message)) {
                builder.addExtra(component);
            }
            nmessage(sender, builder.build());
        }
        // String
        else {
            nmessage(sender, getCurrentPluginPrefix(" §f") + message);
        }
    }

    public static void messageFormat(Object sender, Object message, Object... params) {
        message(sender, String.format((String) message, params));
    }


    public static void nwarning(Object sender, Object message) {
        // BaseComponent
        if (message instanceof BaseComponent) {
            nmessage(sender, new SimpleComponentBuilder("§c")
                    .addExtra((BaseComponent) message)
                    .build());
        }
        // BaseComponent[]
        else if (message instanceof BaseComponent[]) {
            SimpleComponentBuilder builder = new SimpleComponentBuilder("§c");
            for (BaseComponent component : ((BaseComponent[]) message)) {
                builder.addExtra(component);
            }
            nmessage(sender, builder.build());
        }
        // String
        else {
            nmessage(sender, "§c" + message);
        }
    }

    public static void nwarning(Object sender, BaseComponent... components) {
        nwarning(sender, components);
    }

    public static void nwarningFormat(Object sender, Object message, Object... params) {
        nwarning(sender, String.format((String) message, params));
    }


    public static void warning(Object sender, Object message) {
        // BaseComponent
        if (message instanceof BaseComponent) {
            nmessage(sender, new SimpleComponentBuilder(getCurrentPluginPrefix(" §c"))
                    .addExtra((BaseComponent) message)
                    .build());
        }
        // BaseComponent[]
        else if (message instanceof BaseComponent[]) {
            SimpleComponentBuilder builder = new SimpleComponentBuilder(getCurrentPluginPrefix(" §c"));
            for (BaseComponent component : ((BaseComponent[]) message)) {
                builder.addExtra(component);
            }
            nmessage(sender, builder.build());
        }
        // String
        else {
            nmessage(sender, getCurrentPluginPrefix(" §c") + message);
        }
    }

    public static void warning(Object sender, BaseComponent... components) {
        warning(sender, components);
    }

    public static void warningFormat(Object sender, Object message, Object... params) {
        warning(sender, String.format((String) message, params));
    }


    public static void nbroadcast(Object message) {
        messagerAdapter.broadcast(message);
    }

    public static void nbroadcast(BaseComponent... components) {
        nbroadcast(components);
    }

    public static void nbroadcastFormat(Object message, Object... params) {
         nbroadcast(String.format((String) message, params));
    }

    public static void broadcast(Object message) {
        // BaseComponent
        if (message instanceof BaseComponent) {
            nbroadcast(new SimpleComponentBuilder(getCurrentPluginPrefix(" §f"))
                    .addExtra((BaseComponent) message)
                    .build());
        }
        // BaseComponent[]
        else if (message instanceof BaseComponent[]) {
            SimpleComponentBuilder builder = new SimpleComponentBuilder(getCurrentPluginPrefix(" §f"));
            for (BaseComponent component : ((BaseComponent[]) message)) {
                builder.addExtra(component);
            }
            nbroadcast(builder.build());
        }
        // String
        else {
            nbroadcast(getCurrentPluginPrefix(" §f") + message);
        }
    }

    public static void broadcast(BaseComponent... components) {
        broadcast(components);
    }

    public static void broadcastFormat(Object message, Object... params) {
        broadcast(String.format((String) message, params));
    }

}
