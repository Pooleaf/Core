package net.pooleaf.core.modules.sqlcontext;

import net.pooleaf.core.module.CoreModule;

public class SqlContextModule extends CoreModule {

    @Override
    public String getName() {
        return "SqlContext";
    }

    @Override
    public String[] getDepends() {
        return new String[] { "AnnoConfig" };
    }

}