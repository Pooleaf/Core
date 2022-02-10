package net.pooleaf.core.modules.sqlib;

import java.sql.PreparedStatement;

public class SqlBatch {

    protected AbstractSqlManager sqlManager;

    protected PreparedStatement statement;


    public SqlBatch(AbstractSqlManager sqlManager) {
        this.sqlManager = sqlManager;
    }





}
