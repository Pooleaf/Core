package net.pooleaf.core.sql;

import net.pooleaf.core.Core;
import net.pooleaf.core.modules.sqllib.common.AbstractSqlManager;

public class CoreSqlManager extends AbstractSqlManager {

    public CoreSqlManager() {
        super(Core.getPlugin());

        // Core 플러그인 SqlManager 사용 Config 제거
        getConfig().setUseCorePluginSqlManager(null);
    }

}
