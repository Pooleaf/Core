package net.pooleaf.core;

import lombok.Getter;
import net.pooleaf.core.modules.support.common.CommonChatColor;
import net.pooleaf.core.modules.support.common.logger.Logger;
import net.pooleaf.core.plugin.BungeeCorePlugin;

public class BungeeCoreBootstrapPlugin extends BungeeCorePlugin {

    @Getter
    private BungeeCoreBootstrapPlugin instance;


    @Override
    public void onStart() {
        instance = this;

        setPrefix("§e[ Core ]");
        setColor(CommonChatColor.YELLOW);
        registerLoggerPrefix();

        Core.init(this, "bungeecord");

        registerEventListeners();
        Logger.log("EventListener가 등록되었습니다.");

        registerCommonEventListeners();
        Logger.log("CommonEventListener가 등록되었습니다.");

        registerCommands();
        Logger.log("명령어가 등록되었습니다.");
    }

    @Override
    public void onEnd() {
        Core.getModuleManager().endModules();
    }

}
