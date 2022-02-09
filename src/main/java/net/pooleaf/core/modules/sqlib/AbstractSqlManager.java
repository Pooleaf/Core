package net.pooleaf.core.modules.sqlib;

import com.zaxxer.hikari.HikariDataSource;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;
import net.pooleaf.core.modules.sqlib.config.SqlConfig;
import net.pooleaf.core.modules.support.common.logger.Logger;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.Set;

public class AbstractSqlManager {

    @Getter
    private static SqlConfig config = new SqlConfig();

    @Getter
    private DataSource dataSource;

    @Getter(AccessLevel.PROTECTED)
    private Set<AbstractDao> daos = new HashSet<>();


    public void loadConfig() {
        try {
            config.load();
            config.save();
            Logger.log("DB 설정을 불러왔습니다.");
        } catch (Exception e) {
            Logger.warning("DB 설정을 불러올 수 없습니다.");
            e.printStackTrace();
        }
    }

    public boolean connect() {
        try {
            loadConfig();

            dataSource = new HikariDataSource(config.getHikariConfig());
            Logger.log(config.getSqlType().name() + "에 연결되었습니다.");

            // DAO onConnect 메소드 호출
            daos.forEach(AbstractDao::onConnect);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Logger.warning(config.getSqlType().name() + "에 연결할 수 없습니다.");

            return false;
        }
    }

    @SneakyThrows
    public void close() {
        if (dataSource == null || ((HikariDataSource) dataSource).isClosed()) return;

        ((HikariDataSource) dataSource).close();
        Logger.log(config.getSqlType().name() + " 연결을 종료했습니다.");
    }


    @SneakyThrows
    public void update(String sql) {
        
    }

}
