package net.pooleaf.core;

import lombok.Getter;
import net.pooleaf.core.modules.commonconfig.bukkit.BukkitConfigUtil;
import net.pooleaf.core.modules.support.common.CommonChatColor;
import net.pooleaf.core.modules.support.common.logger.Logger;
import net.pooleaf.core.plugin.BukkitCorePlugin;

public class BukkitCoreBootstrapPlugin extends BukkitCorePlugin {

    @Getter
    private static BukkitCoreBootstrapPlugin instance;


    @Override
    public void onStart() {
        instance = this;

        setPrefix("§e[ Core ]");
        setColor(CommonChatColor.YELLOW);
        registerLoggerPrefix();

        BukkitConfigUtil.enableUtf8Config();
        getConfig().addDefault("서버 이름", Core.getServerName());
        getConfig().options().copyDefaults(true);
        saveConfig();

        String serverName = getConfig().getString("서버 이름", Core.getServerName());

        Core.init(this, serverName);

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
