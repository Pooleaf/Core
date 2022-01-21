package net.pooleaf.core.modules.sqlcontext.config;

import net.pooleaf.core.modules.annoconfig.anno.ConfigSerialize;

public class SqlTypeSerializer implements ConfigSerialize.ConfigSerializer<SqlType> {

    @Override
    public String serialize(SqlType value) {
        return value.name();
    }

    @Override
    public SqlType deserialize(String value) {
        for (SqlType sqlType : SqlType.values()) {
            if (sqlType.name().equalsIgnoreCase(value)) return sqlType;
        }

        return SqlType.SQLITE;
    }

}
