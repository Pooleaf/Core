package net.pooleaf.core.modules.sqlib;

import net.pooleaf.core.module.CoreModule;

public class SqlibModule extends CoreModule {

    @Override
    public String getName() {
        return "SqlContext";
    }

    @Override
    public String[] getDepends() {
        return new String[] { "AnnoConfig" };
    }

}