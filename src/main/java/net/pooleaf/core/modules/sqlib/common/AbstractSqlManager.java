package net.pooleaf.core.modules.sqlib.common;

import com.zaxxer.hikari.HikariDataSource;
import lombok.AccessLevel;
import lombok.Cleanup;
import lombok.Getter;
import lombok.SneakyThrows;
import net.pooleaf.core.modules.sqlib.common.config.SqlConfig;
import net.pooleaf.core.modules.support.common.debugger.Debugger;
import net.pooleaf.core.modules.support.common.logger.Logger;
import net.pooleaf.core.modules.support.common.util.StringUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

public class AbstractSqlManager {

    @Getter
    private SqlConfig config = new SqlConfig();

    @Getter
    private DataSource dataSource;

    @Getter(AccessLevel.PROTECTED)
    private Set<SqlDao> daos = new HashSet<>();


    public void loadConfig() {
        try {
            config.load();
            config.save();
            Logger.log(getClass().getSimpleName() + ": DB 설정을 불러왔습니다.");
        } catch (Exception e) {
            Logger.warning(getClass().getSimpleName() + ": DB 설정을 불러올 수 없습니다.");
            e.printStackTrace();
        }
    }

    public void onConnected() {}

    public boolean connect() {
        try {
            // 설정 불러오기
            loadConfig();

            // DB에 연결
            if (config.getUseCorePluginDataSource() != null && config.getUseCorePluginDataSource()) {
                Logger.log(getClass().getSimpleName() + ": Core 플러그인의 DataSource를 사용합니다.");
            } else {
                dataSource = new HikariDataSource(config.getHikariConfig());
                Logger.log(getClass().getSimpleName() + ": " + config.getSqlType().name() + "에 연결되었습니다.");
            }

            onConnected();

            // DAO onConnected 메소드 호출
            daos.forEach(SqlDao::onConnected);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Logger.warning(getClass().getSimpleName() + ": " + config.getSqlType().name() + "에 연결할 수 없습니다.");

            return false;
        }
    }

    @SneakyThrows
    public void close() {
        if (config.getUseCorePluginDataSource()) return;
        if (dataSource == null || ((HikariDataSource) dataSource).isClosed()) return;

        ((HikariDataSource) dataSource).close();
        Logger.log(getClass().getSimpleName() + ": " + config.getSqlType().name() + " 연결을 종료했습니다.");
    }

    @SneakyThrows
    public Connection getConnection() {
        return dataSource.getConnection();
    }


    @SneakyThrows
    public PreparedStatement preparedStatement(String sql) {
        return new CPreparedStatement(getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS));
    }

    @SneakyThrows
    public int update(String sql, Object... params) {
        long startTime = System.currentTimeMillis();

        Debugger.log("[SQLib] update SQL: " + sql);
        Debugger.log("[SQLib] update Parameters: " + StringUtil.joinArray(", ", params));

        @Cleanup PreparedStatement statement = preparedStatement(sql);
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i + 1, params[i]);
        }

        int result = statement.executeUpdate();
        Debugger.log("[SQLib] update Result: " + result);

        Debugger.log("[SQLib] update Executed in: " + (System.currentTimeMillis() - startTime));

        return result;
    }

    @SneakyThrows
    public CachedResult getResult(String sql, Object... params) {
        long startTime = System.currentTimeMillis();

        Debugger.log("[SQLib] getResult SQL: " + sql);
        Debugger.log("[SQLib] getResult Parameters: " + StringUtil.joinArray(", ", params));

        @Cleanup PreparedStatement statement = preparedStatement(sql);
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i + 1, params[i]);
        }
        @Cleanup ResultSet resultSet = statement.executeQuery();
        CachedResult cachedResult = new CachedResult(resultSet);

        Debugger.log("[SQLib] getResult Executed in: " + (System.currentTimeMillis() - startTime));

        return cachedResult;
    }

    public void createTable(String tableName, String... columnString) {
        update("CREATE TABLE IF NOT EXISTS " + tableName + " (" + columnString + ")");
    }

    public void dropTable(String tableName) {
        update("DROP TABLE " + tableName);
    }

    public void truncateTable(String tableName) {
        update("TRUNCATE TABLE " + tableName);
    }

}
