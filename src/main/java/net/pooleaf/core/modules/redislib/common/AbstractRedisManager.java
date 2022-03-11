package net.pooleaf.core.modules.redislib.common;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.async.RedisPubSubAsyncCommands;
import lombok.Getter;
import lombok.SneakyThrows;
import net.pooleaf.core.Core;
import net.pooleaf.core.modules.redislib.common.config.RedisConfig;
import net.pooleaf.core.modules.redislib.common.listener.RedisKeySpaceEventListener;
import net.pooleaf.core.modules.redislib.common.listener.RedisMessageListener;
import net.pooleaf.core.modules.support.common.logger.Logger;
import net.pooleaf.core.modules.support.common.platform.Platform;

import java.util.HashSet;
import java.util.Set;
import net.pooleaf.core.modules.support.common.util.GsonUtil;

@Getter
public class AbstractRedisManager {

    public static final String SUBSCRIBE_PREFIX = "core:";
    public static final String BUNGEECORD_CHANNEL = SUBSCRIBE_PREFIX + "bungeecord";
    public static final String BROADCAST_CHANNEL = SUBSCRIBE_PREFIX + "broadcast";


    private RedisConfig config = new RedisConfig();
    private Set<RedisDao> daos = new HashSet<>();


    private RedisClient client;

    private StatefulRedisConnection<String, String> connection;
    private StatefulRedisPubSubConnection<String, String> pubSubConnection;

    private RedisAsyncCommands<String, String> asyncCommands;
    private RedisPubSubAsyncCommands<String, String> pubSubAsyncCommands;


    public void loadConfig() {
        try {
            if (Platform.getCurrentPlatform() == Platform.BUNGEECORD) {
                config.setServerName(null);
            } else {
                config.setServerName(Core.getServerFolderName().toLowerCase());
            }

            config.load();
            config.save();
            Logger.log(getClass().getSimpleName() + ": Redis 설정을 불러왔습니다.");
        } catch (Exception e) {
            Logger.warning(getClass().getSimpleName() + ": Redis 설정을 불러올 수 없습니다.");
            e.printStackTrace();
        }
    }

    public void onConnected() {}

    public boolean connect() {
        try {
            // 설정 불러오기
            loadConfig();

            if (config.getUseCorePluginRedisManager() != null && config.getUseCorePluginRedisManager()) {
                Logger.log(getClass().getSimpleName() + ": Core 플러그인의 RedisManager를 사용합니다.");
            } else {
                RedisURI uri = RedisURI.Builder
                        .redis(config.getAddress(), config.getPort())
                        .withPassword(config.getPassword())
                        .build();
                client = RedisClient.create(uri);

                connection = client.connect();
                pubSubConnection = client.connectPubSub();

                asyncCommands = connection.async();
                pubSubAsyncCommands = pubSubConnection.async();

                asyncCommands.configSet("notify-keyspace-events", "KEA");
                pubSubAsyncCommands.psubscribe("__keyspace@0__:*");
                pubSubAsyncCommands.subscribe(SUBSCRIBE_PREFIX + config.getServerName());
                pubSubAsyncCommands.subscribe(BROADCAST_CHANNEL);

                pubSubConnection.addListener(new RedisKeySpaceEventListener());
                pubSubConnection.addListener(new RedisMessageListener());

                Logger.log(getClass().getSimpleName() + ": Redis에 연결되었습니다.");
            }

            onConnected();

            // DAO onConnected 메소드 호출
            for (RedisDao dao : daos) {
                dao.onConnected();
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Logger.warning(getClass().getSimpleName() + ": Redis에 연결할 수 없습니다.");

            return false;
        }
    }

    @SneakyThrows
    public void close() {
        if (config.getUseCorePluginRedisManager()) {
            return;
        }

        pubSubAsyncCommands.punsubscribe("__keyspace@0__:*");
        pubSubAsyncCommands.unsubscribe(SUBSCRIBE_PREFIX + config.getServerName());
        pubSubAsyncCommands.unsubscribe(BROADCAST_CHANNEL);

        connection.close();
        pubSubConnection.close();

        client.shutdown();

        Logger.log(getClass().getSimpleName() + ": Redis 연결을 종료했습니다.");
    }

    private void send(String serverName, Object... datas) {
        asyncCommands.publish(serverName, GsonUtil.getGson().toJson(datas));
    }

    public void send(String serverName, String messageChannel, Object... datas) {
        send(serverName, messageChannel, datas);
    }

    public void sendToBungeeCord(String messageChannel, Object... datas) {
        send(BUNGEECORD_CHANNEL, messageChannel, datas);
    }

    public void broadcast(String messageChannel, Object... datas) {
        send(BROADCAST_CHANNEL, datas);
    }

}
