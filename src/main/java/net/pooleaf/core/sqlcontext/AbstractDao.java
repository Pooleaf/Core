package net.pooleaf.core.sqlcontext;

import org.jooq.DSLContext;

public abstract class AbstractDao {

    private AbstractSqlContextManager sqlContextManager;


    public AbstractDao(AbstractSqlContextManager sqlContextManager) {
        this.sqlContextManager = sqlContextManager;
        sqlContextManager.getDaos().add(this);
    }

    protected DSLContext getContext() {
        return sqlContextManager.getContext();
    }

    public void onConnect() {}

}
