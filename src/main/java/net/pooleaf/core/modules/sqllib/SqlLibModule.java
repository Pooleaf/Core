package net.pooleaf.core.modules.sqllib;

import net.pooleaf.core.Core;
import net.pooleaf.core.module.CoreModule;
import net.pooleaf.core.plugin.CorePlugin;

public class SqlLibModule extends CoreModule {

    @Override
    public String getName() {
        return "SqlLib";
    }

    @Override
    public String[] getDepends() {
        return new String[] { "AnnoConfig" };
    }

    @Override
    public void onEnable(CorePlugin plugin) {
        Core.getSqlManager().connect();
    }

    @Override
    public void onDisable(CorePlugin plugin) {
        Core.getSqlManager().close();
    }

}