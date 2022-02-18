package net.pooleaf.core.modules.sqllib;

import net.pooleaf.core.module.CoreModule;

public class SqlLibModule extends CoreModule {

    @Override
    public String getName() {
        return "SqlLib";
    }

    @Override
    public String[] getDepends() {
        return new String[] { "AnnoConfig" };
    }

}