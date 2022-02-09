package net.pooleaf.core.modules.support.common.debugger;

import net.pooleaf.core.Core;
import net.pooleaf.core.modules.support.common.messager.Messager;
import net.pooleaf.core.plugin.CorePlugin;

import java.util.HashSet;
import java.util.Set;

public class Debugger {

    private static Set<Object> listeners = new HashSet<>();


    /**
     * CommandSender에게 Debug 메시지를 들려주게 합니다.
     * @param commandSender Debug 메시지를 듣게 할 CommandSender
     * @return 듣게 성공 시 true, 이미 듣고 있을경우 false
     */
    public static boolean addListener(Object commandSender) {
        if (listeners.contains(commandSender)) {
            return false;
        }

        listeners.add(commandSender);
        return true;
    }

    /**
     * CommandSender에게 Debug 메시지를 들려주지 않게 합니다.
     * @param commandSender Debug 메시지를 안듣게 할 CommandSender
     * @return 안듣게 성공 시 true, 이미 안듣고 있을경우 false
     */
    public static boolean removeListener(Object commandSender) {
        if (!listeners.contains(commandSender)) {
            return false;
        }

        listeners.remove(commandSender);
        return true;
    }

    /**
     * CommandSender가 Debug 메시지를 듣고있는지 여부를 반환합니다.
     * @param commandSender Debug 메시지를 듣고있는지 확인할 CommandSender
     * @return Debug 메시지를 듣고있으면 true, 아닐경우 false
     */
    public static boolean isListening(Object commandSender) {
        return listeners.contains(commandSender);
    }


    /**
     * Debug 메시지를 듣고있는 CommandSender들에게 메시지를 보냅니다.
     * @param message 보낼 메시지
     */
    public static void log(Object message) {
        String prefix = "§e[DEBUG]§f";

        CorePlugin plugin = Core.getCorePluginManager().getCurrentPlugin();
        if (plugin != null) {
            prefix += plugin.getPrefix() + "§f";
        }

        for (Object listener : listeners) {
            Messager.message(listener, message);
        }
    }

}
