package net.pooleaf.core.modules.sqllib.common.dslcontext;

import lombok.SneakyThrows;
import net.pooleaf.core.modules.sqllib.common.AbstractSqlManager;
import net.pooleaf.core.modules.sqllib.common.CachedResult;
import net.pooleaf.core.modules.sqllib.common.CachedResultRow;
import net.pooleaf.core.modules.sqllib.common.SqlTable;
import net.pooleaf.core.modules.support.common.util.StringUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SelectContext extends DslContext<SelectContext> {

    public SelectContext(AbstractSqlManager sqlManager, SqlTable sqlTable) {
        super(sqlManager, sqlTable);

        sqls.put("FROM", sqlTable.getName());
    }


    @Override
    public String buildSql() {
        StringBuilder builder = new StringBuilder();

        builder.append(getSql("MAIN"))
                .append(getSql("FROM"))
                .append(getSql("WHERE"))
                .append(getSql("GROUP BY"))
                .append(getSql("HAVING"))
                .append(getSql("ORDER BY"))
                .append(getSql("LIMIT"))
                .append(getSql("SQL"));

        return builder.toString();
    }


    public SelectContext select(String columns) {
        sqls.put("MAIN", "SELECT " + columns);
        return this;
    }

    public SelectContext where(String conditions) {
        sqls.put("WHERE", conditions);
        return this;
    }

    public SelectContext groupBy(String columns) {
        sqls.put("GROUP BY", columns);
        return this;
    }

    public SelectContext having(String conditions) {
        sqls.put("HAVING", conditions);
        return this;
    }

    public SelectContext orderBy(String columns) {
        sqls.put("ORDER BY", columns);
        return this;
    }

    public SelectContext orderBy(String columns, boolean asc) {
        sqls.put("ORDER BY", columns + " " + (asc ? "ASC" : "DESC"));
        return this;
    }

    public SelectContext limit(int count) {
        sqls.put("LIMIT", count + "");
        return this;
    }

    public SelectContext limit(int offset, int count) {
        sqls.put("LIMIT", offset + ", " + count);
        return this;
    }

    public SelectContext parameters(Object... parameters) {
        this.values = parameters;
        return this;
    }

    /**
     * SQL문을 실행하여 결과값을 반환합니다.
     * @return SQL문 실행 결과값
     */
    public CachedResult execute() {
        String sql = buildSql();

        if (values == null) {
            return sqlManager.getResult(sql);
        } else {
            return sqlManager.getResult(sql, values);
        }
    }

    /**
     * SQL문을 실행하여 결과값을 해당 클래스 객체로 반환합니다.
     * @return SQL문 실행 결과 객체
     */
    @SneakyThrows
    public List<Object> executeList(Class objectClass) {
        List<Object> resultObjects = new ArrayList<>();

        CachedResult result = execute();
        for (CachedResultRow row : result.getRows()) {
            Object object = objectClass.newInstance();

            for (String key : row.getDatas().keySet()) {
                String targetFieldName = StringUtil.convertSnakeCaseToLowerCamelCase(key);
                Field targetField = object.getClass().getDeclaredField(targetFieldName);
                if (targetField != null) {
                    targetField.setAccessible(true);
                    targetField.set(object, row.get(key));
                }
            }
        }

        return resultObjects;
    }

    public Object execute(Class objectClass) {
        List<Object> objects = executeList(objectClass);

        if (objects.isEmpty()) {
            return null;
        }

        return objects.get(0);
    }

}
