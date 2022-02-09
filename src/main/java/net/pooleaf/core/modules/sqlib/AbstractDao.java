package net.pooleaf.core.modules.sqlib;

public abstract class AbstractDao {

    private AbstractSqlManager sqlContextManager;


    public AbstractDao(AbstractSqlManager sqlContextManager) {
        this.sqlContextManager = sqlContextManager;
        sqlContextManager.getDaos().add(this);
    }

    public void onConnect() {}

}
