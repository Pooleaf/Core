package net.pooleaf.core.sql;

import net.pooleaf.core.modules.sqllib.common.AbstractSqlManager;

public class CoreSqlManager extends AbstractSqlManager {

    public CoreSqlManager() {
        // Core 플러그인 DataSource 사용 Config 제거
        getConfig().setUseCorePluginSqlManager(null);
    }

}
