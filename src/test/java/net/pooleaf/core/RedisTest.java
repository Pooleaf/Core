package net.pooleaf.core;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.async.RedisPubSubAsyncCommands;
import org.junit.jupiter.api.Test;

public class RedisTest {

    private RedisClient client;

    private StatefulRedisConnection<String, String> connection;
    private RedisAsyncCommands<String, String> asyncCommands;

    private StatefulRedisPubSubConnection<String, String> pubSubConnection;
    private RedisPubSubAsyncCommands<String, String> pubSubAsyncCommands;

    @Test
    public void test() {
        String address = "c.s8u.kr";
        int port = 6379;

        RedisURI uri = RedisURI.Builder
                .redis(address, port)
                .withPassword("Suh209070!")
                .build();
        client = RedisClient.create(uri);

        connection = client.connect();
        asyncCommands = connection.async();

        pubSubConnection = client.connectPubSub();
        pubSubAsyncCommands = pubSubConnection.async();

        System.out.println("connection: " + connection.isOpen());
    }
}
