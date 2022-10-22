package net.pooleaf.core.modules.sqllib.common.configs;

import com.zaxxer.hikari.HikariConfig;
import lombok.Data;
import net.pooleaf.core.Core;
import net.pooleaf.core.modules.annoconfig.common.SimpleAnnoConfig;
import net.pooleaf.core.modules.annoconfig.common.anno.ConfigAes256;
import net.pooleaf.core.modules.annoconfig.common.anno.ConfigName;
import net.pooleaf.core.modules.annoconfig.common.anno.ConfigSerialize;

import java.io.File;
import net.pooleaf.core.plugin.CorePlugin;

@Data
public class SqlConfig extends SimpleAnnoConfig {

    @ConfigName("Core 플러그인 SqlManager 사용")
    private Boolean useCorePluginSqlManager = true;

    @ConfigSerialize(SqlTypeSerializer.class)
    @ConfigName("종류")
    private SqlType sqlType = SqlType.SQLITE;

    // SQLite
    @ConfigName("파일 이름")
    private String fileName = "data.db";

    // MySQL & MariaDB
    @ConfigName("주소")
    private String address = "localhost";

    @ConfigName("포트")
    private int port = 3306;

    @ConfigName("데이터베이스")
    private String database = "database";

    @ConfigName("사용자")
    private String user = "user";

    @ConfigName("비밀번호")
    @ConfigAes256("sqlconfig passwd")
    private String password = "password";

    @ConfigName("속성")
    private String properties = "?autoReconnect=true&useUnicode=true&characterEncoding=utf8";


    public SqlConfig(CorePlugin plugin) {
        super(new File(plugin.getDataFolder(), "sql-config.yml"));
    }

    public HikariConfig getHikariConfig() {
        HikariConfig config = new HikariConfig();

        switch (sqlType) {
            case SQLITE:
                config.setJdbcUrl(String.format("jdbc:sqlite:%s/%s", Core.getPlugin().getDataFolder(), fileName));
                break;

            case MYSQL:
                config.setJdbcUrl(String.format("jdbc:mysql://%s:%d/%s%s", address, port, database, properties));
                config.setUsername(user);
                config.setPassword(password);
                break;

            case MARIADB:
                config.setJdbcUrl(String.format("jdbc:mariadb://%s:%d/%s%s", address, port, database, properties));
                config.setUsername(user);
                config.setPassword(password);
                break;
        }

        return config;
    }

}
