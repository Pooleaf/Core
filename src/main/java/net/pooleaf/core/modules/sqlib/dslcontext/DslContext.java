package net.pooleaf.core.modules.sqlib.dslcontext;

import net.pooleaf.core.modules.sqlib.AbstractSqlManager;

import java.util.HashMap;
import java.util.Map;

public class DslContext<T extends DslContext> {

    protected AbstractSqlManager sqlManager;


    protected Map<String, String> sqls = new HashMap<>();
    protected Object[] parameters;


    public DslContext(AbstractSqlManager sqlManager) {
        this.sqlManager = sqlManager;
    }


    public T from(String tableName) {
        sqls.put("FROM", tableName);
        return (T) this;
    }

    public DslContext where(String conditions) {
        sqls.put("WHERE", conditions);
        return (T) this;
    }

    public DslContext groupBy(String columns) {
        sqls.put("GROUP BY", columns);
        return (T) this;
    }

    public DslContext having(String conditions) {
        sqls.put("HAVING", conditions);
        return (T) this;
    }

    public DslContext orderBy(String columns) {
        sqls.put("ORDER BY", columns);
        return (T) this;
    }

    public DslContext orderBy(String columns, boolean asc) {
        sqls.put("ORDER BY", columns + " " + (asc ? "ASC" : "DESC"));
        return (T) this;
    }

    public DslContext limit(int count) {
        sqls.put("LIMIT", count + "");
        return (T) this;
    }

    public DslContext limit(int offset, int count) {
        sqls.put("LIMIT", offset + ", " + count);
        return (T) this;
    }

    public DslContext parameters(Object... parameters) {
        this.parameters = parameters;
        return (T) this;
    }

    protected String getSql(String key) {
        String sql = "";

        if (sqls.containsKey(key)) {
            if (!key.equals("MAIN")) {
                sql += " ";
            }
            sql += key + " " + sqls.get(key);
        }

        return sql;
    }

    protected String buildSql() {
        String sql = "";

        sql += getSql("MAIN");
        sql += getSql("FROM");
        sql += getSql("WHERE");
        sql += getSql("GROUP BY");
        sql += getSql("HAVING");
        sql += getSql("ORDER BY");
        sql += getSql("LIMIT");

        return sql;
    }


}
