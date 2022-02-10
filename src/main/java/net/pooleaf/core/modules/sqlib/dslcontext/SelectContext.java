package net.pooleaf.core.modules.sqlib.dslcontext;

import lombok.SneakyThrows;
import net.pooleaf.core.modules.sqlib.AbstractSqlManager;
import net.pooleaf.core.modules.sqlib.CachedResult;
import net.pooleaf.core.modules.sqlib.CachedResultRow;
import net.pooleaf.core.modules.support.common.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class SelectContext extends DslContext<SelectContext> {

    public SelectContext(AbstractSqlManager sqlManager) {
        super(sqlManager);
    }


    public SelectContext select(String columns) {
        sqls.put("MAIN", columns);
        return this;
    }

    public CachedResult execute() {
        String sql = buildSql();

        if (parameters == null) {
            return sqlManager.getResult(sql);
        } else {
            return sqlManager.getResult(sql, parameters);
        }
    }

    @SneakyThrows
    public List<Object> execute(Class objectClass) {
        List<Object> resultObjects = new ArrayList<>();

        CachedResult result = execute();
        for (CachedResultRow row : result.getRows()) {
            Object object = objectClass.newInstance();

            for (String key : row.getDatas().keySet()) {
                key = StringUtil.convertSnakeCaseToLowerCamelCase(key);

                // TODO
            }
        }

        return resultObjects;
    }

}
