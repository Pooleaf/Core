package net.pooleaf.core;

import net.pooleaf.core.module.ModuleManager;
import net.pooleaf.core.plugin.CorePlugin;
import net.pooleaf.core.plugin.CorePluginManager;
import net.pooleaf.core.redis.CoreRedisManager;
import net.pooleaf.core.sql.CoreSqlManager;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.File;

public class Core {

    @Getter
    private static CorePlugin plugin;

    @Getter
    private static ModuleManager moduleManager = new ModuleManager();

    @Getter
    private static CorePluginManager pluginManager = new CorePluginManager();

    @Getter
    private static CoreSqlManager sqlManager;

    @Getter
    private static CoreRedisManager redisManager;

    @Getter
    private static String serverName;

    protected static void init(CorePlugin plugin, String serverName) {
        Core.plugin = plugin;

        // 서버 이름 설정
        Core.serverName = serverName;

        // Manager 초기화
        sqlManager = new CoreSqlManager();
        redisManager = new CoreRedisManager();

        // 모듈 자동 등록
        moduleManager.registerModules();

        // 모듈 초기화
        moduleManager.initModules();
    }

    /**
     * 마지막으로 사용한 클래스 이름을 반환합니다.
     *
     * @return 마지막으로 사용한 클래스 이름
     */
    public static String getLastClassName() {
        StackTraceElement[] ste = new Throwable().getStackTrace();

        for (int i = 0; i < ste.length; i++) {
            if (!ste[i].getClassName().startsWith(Core.class.getPackage().getName())) {
                return ste[i].getClassName();
            }
        }

        return null;
    }

    /**
     * 서버 이름을 반환합니다.
     * Bootstrap 플러그인에서 설정되며, 설정되지 않은 경우 서버 폴더 이름을 반환합니다.
     *
     * @return 서버 이름
     */
    @SneakyThrows
    public static String getServerName() {
        if (serverName != null) {
            return serverName;
        }
        String path = new File("").getCanonicalPath();
        return path.substring(path.lastIndexOf(File.separator) + 1, path.length());
    }

}