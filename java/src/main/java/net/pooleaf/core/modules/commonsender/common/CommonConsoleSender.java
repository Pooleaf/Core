package net.pooleaf.core.modules.commonsender.common;

import net.pooleaf.core.modules.option.OptionModule;
import net.pooleaf.core.modules.option.common.Option;

public abstract class CommonConsoleSender<T> extends CommonCommandSender<T> {

    public CommonConsoleSender() {
        this.name = "콘솔";
        this.displayName = "콘솔";
    }

    @Override
    public String getId() {
        return "CONSOLE";
    }

    @Override
    public boolean isConsole() {
        return true;
    }

    @Override
    public boolean hasPermission(String permission) {
        return true;
    }

    @Override
    public Option option() {
        return OptionModule.getServerOption();
    }

}
