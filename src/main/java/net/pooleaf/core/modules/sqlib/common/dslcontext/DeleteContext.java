package net.pooleaf.core.modules.sqlib.common.dslcontext;

import net.pooleaf.core.modules.sqlib.common.AbstractSqlManager;
import net.pooleaf.core.modules.sqlib.common.SqlTable;

public class DeleteContext extends DslContext<DeleteContext> {

    public DeleteContext(AbstractSqlManager sqlManager, SqlTable sqlTable) {
        super(sqlManager, sqlTable);

        sqls.put("MAIN", "DELETE FROM " + sqlTable.getName());
    }


    @Override
    public String buildSql() {
        StringBuilder builder = new StringBuilder();

        builder.append(getSql("MAIN"))
                .append(getSql("WHERE"))
                .append(getSql("SQL"));

        return builder.toString();
    }


    public DslContext where(String conditions) {
        sqls.put("WHERE", conditions);
        return this;
    }

    public DslContext parameters(Object... parameters) {
        this.values = parameters;
        return this;
    }

    /**
     * SQL문을 실행하고 삭제 성공한 수를 반환합니다.
     * @return 삭제 성공한 수
     */
    public int execute() {
        String sql = buildSql();

        int result = sqlManager.update(sql, values);
        return result;
    }

}
