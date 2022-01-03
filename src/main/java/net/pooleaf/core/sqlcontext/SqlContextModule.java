package net.pooleaf.core.sqlcontext;

import net.pooleaf.core.CoreModule;

public class SqlContextModule extends CoreModule {

    @Override
    public String getName() {
        return "Dao";
    }

    @Override
    public String[] getDepends() {
        return new String[] { "AnnoConfig" };
    }

}