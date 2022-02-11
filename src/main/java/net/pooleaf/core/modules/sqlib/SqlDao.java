package net.pooleaf.core.modules.sqlib;

public abstract class SqlDao {

    protected AbstractSqlManager sqlManager;


    public SqlDao(AbstractSqlManager sqlManager) {
        this.sqlManager = sqlManager;
        sqlManager.getDaos().add(this);
    }

    public void onConnected() {}

}
