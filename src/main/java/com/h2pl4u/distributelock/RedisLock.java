package com.h2pl4u.distributelock;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * redis分布式锁，实现简单，但最大缺陷不可重入
 */
public class RedisLock {
    //锁键
    private String lock_key = "redis_lock";
    //锁过期世界
    protected long internalLockLeaseTime = 30000;
    //获取锁的超时时间
    private long timeout = 999999;
    //SET命令参数
    SetParams params = SetParams.setParams().nx().px(internalLockLeaseTime);

    /**
     * 加锁
     * @param sid
     * @return
     */
    public boolean lock(String sid) {
        Jedis jedis = new Jedis("localhost", 6379);
        long start = System.currentTimeMillis();
        try {
            while (true) {
                //SET命令返回ok，则证明获取锁成功
                String lock = jedis.set(lock_key, sid, params);
                if ("OK".equals(lock)) {
                    return true;
                }
                //否则循环等待，再timeout时间内任未获取到锁，则获取失败
                long l = System.currentTimeMillis() - start;
                if (l >= timeout) {
                    return false;
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            jedis.close();
        }
    }

    /**
     * 解锁 分布式
     * @param sid
     * @return
     */
    public boolean unlock(String sid) {
        Jedis jedis = new Jedis("localhost", 6379);
        String script =
                "if redis.call('get',KEYS[1]) == ARGV[1] then" +
                        "   return redis.call('del',KEYS[1]) " +
                        "else" +
                        "   return 0 " +
                        "end";
        try {
            Object result = jedis.eval(script, Collections.singletonList(lock_key), Collections.singletonList(sid));
            if ("1".equals(result.toString())) {
                return true;
            }
            return false;
        } finally {
            jedis.close();
        }
    }
}
