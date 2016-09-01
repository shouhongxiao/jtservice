package jtservice.register;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by shouh on 2016/8/31.
 */
public class JedisContext {
    private static JedisPool pool;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(10);
        config.setMaxTotal(30);
        config.setMaxWaitMillis(3 * 1000);
        // 在应用初始化的时候生成连接池
        pool = new JedisPool(config, "192.168.66.140", 6611);
    }

    public static Jedis getJedis() {
        return pool.getResource();
    }

    public static void closeJedis(Jedis jedis) {
        if (null != jedis)
            pool.returnResource(jedis);
    }
}
