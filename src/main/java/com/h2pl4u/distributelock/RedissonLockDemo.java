package com.h2pl4u.distributelock;

import io.netty.util.concurrent.FutureListener;
import org.redisson.Redisson;
import org.redisson.RedissonLockEntry;
import org.redisson.api.RFuture;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.LongCodec;
import org.redisson.client.protocol.RedisCommands;
import org.redisson.client.protocol.RedisStrictCommand;
import org.redisson.config.Config;
import org.redisson.misc.RPromise;
import org.redisson.misc.RedissonPromise;
import org.redisson.pubsub.LockPubSub;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * Redis分布式锁其实不具有可重入性,Redisson中如何调用可重入锁
 */
public class RedissonLockDemo {

    /**
     * 通过配置获取RedissonClient客户端的实例，然后getLock获取锁的实例，进行操作即可
     *
     * @param args
     */
    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");

        final RedissonClient client = Redisson.create(config);
        RLock lock = client.getLock("lock1");
        try {
            lock.lock();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 加锁源码分析
     *
     * @param leaseTime
     * @param unit
     * @throws InterruptedException
     */
//    public void lockInterruptibly(long leaseTime, TimeUnit unit) throws InterruptedException {
//        //当前线程ID
//        long threadId = Thread.currentThread().getId();
//        //尝试获取锁
//        Long ttl = tryAcquire(leaseTime, unit, threadId);
//        // 如果ttl为空，则证明获取锁成功
//        if (ttl == null) {
//            return;
//        }
//        //如果获取锁失败，则订阅到对应这个锁的channel
//        RFuture<RedissonLockEntry> future = subscribe(threadId);
//        commandExecutor.syncSubscription(future);
//
//        try {
//            while (true) {
//                //再次尝试获取锁
//                ttl = tryAcquire(leaseTime, unit, threadId);
//                //ttl为空，说明成功获取锁，返回
//                if (ttl == null) {
//                    break;
//                }
//                //ttl大于0 则等待ttl时间后继续尝试获取
//                if (ttl >= 0) {
//                    getEntry(threadId).getLatch().tryAcquire(ttl, TimeUnit.MILLISECONDS);
//                } else {
//                    getEntry(threadId).getLatch().acquire();
//                }
//            }
//        } finally {
//            //取消对channel的订阅
//            unsubscribe(future, threadId);
//        }
//        //get(lockAsync(leaseTime, unit));
//    }

//    private <T> RFuture<Long> tryAcquireAsync(long leaseTime, TimeUnit unit, final long threadId) {
//
//        //如果带有过期时间，则按照普通方式获取锁
//        if (leaseTime != -1) {
//            return tryLockInnerAsync(leaseTime, unit, threadId, RedisCommands.EVAL_LONG);
//        }
//
//        //先按照30秒的过期时间来执行获取锁的方法
//        RFuture<Long> ttlRemainingFuture = tryLockInnerAsync(
//                commandExecutor.getConnectionManager().getCfg().getLockWatchdogTimeout(),
//                TimeUnit.MILLISECONDS, threadId, RedisCommands.EVAL_LONG);
//
//        //如果还持有这个锁，则开启定时任务不断刷新该锁的过期时间
//        ttlRemainingFuture.addListener(new FutureListener<Long>() {
//            @Override
//            public void operationComplete(Future<Long> future) throws Exception {
//                if (!future.isSuccess()) {
//                    return;
//                }
//
//                Long ttlRemaining = future.getNow();
//                // lock acquired
//                if (ttlRemaining == null) {
//                    scheduleExpirationRenewal(threadId);
//                }
//            }
//        });
//        return ttlRemainingFuture;
//    }


//    <T> RFuture<T> tryLockInnerAsync(long leaseTime, TimeUnit unit,
//                                     long threadId, RedisStrictCommand<T> command) {
//
//        //过期时间
//        internalLockLeaseTime = unit.toMillis(leaseTime);
//
//        return commandExecutor.evalWriteAsync(getName(), LongCodec.INSTANCE, command,
//                //如果锁不存在，则通过hset设置它的值，并设置过期时间
//                "if (redis.call('exists', KEYS[1]) == 0) then " +
//                        "redis.call('hset', KEYS[1], ARGV[2], 1); " +
//                        "redis.call('pexpire', KEYS[1], ARGV[1]); " +
//                        "return nil; " +
//                        "end; " +
//                        //如果锁已存在，并且锁的是当前线程，则通过hincrby给数值递增1
//                        "if (redis.call('hexists', KEYS[1], ARGV[2]) == 1) then " +
//                        "redis.call('hincrby', KEYS[1], ARGV[2], 1); " +
//                        "redis.call('pexpire', KEYS[1], ARGV[1]); " +
//                        "return nil; " +
//                        "end; " +
//                        //如果锁已存在，但并非本线程，则返回过期时间ttl
//                        "return redis.call('pttl', KEYS[1]);",
//                Collections.<Object>singletonList(getName()),
//                internalLockLeaseTime, getLockName(threadId));
//    }


    /**
     * 解锁 源码分析
     * @param threadId
     * @return
     */
//    public RFuture<Void> unlockAsync(final long threadId) {
//        final RPromise<Void> result = new RedissonPromise<Void>();
//
//        //解锁方法
//        RFuture<Boolean> future = unlockInnerAsync(threadId);
//
//        future.addListener(new FutureListener<Boolean>() {
//            @Override
//            public void operationComplete(Future<Boolean> future) throws Exception {
//                if (!future.isSuccess()) {
//                    cancelExpirationRenewal(threadId);
//                    result.tryFailure(future.cause());
//                    return;
//                }
//                //获取返回值
//                Boolean opStatus = future.getNow();
//                //如果返回空，则证明解锁的线程和当前锁不是同一个线程，抛出异常
//                if (opStatus == null) {
//                    IllegalMonitorStateException cause =
//                            new IllegalMonitorStateException(
//                                    " attempt to unlock lock, not locked by current thread by node id: "
//                            + id + " thread-id: " + threadId);
//                    result.tryFailure(cause);
//                    return;
//                }
//                //解锁成功，取消刷新过期时间的那个定时任务
//                if (opStatus) {
//                    cancelExpirationRenewal(null);
//                }
//                result.trySuccess(null);
//            }
//        });
//
//        return result;
//    }

//    protected RFuture<Boolean> unlockInnerAsync(long threadId) {
//        return commandExecutor.evalWriteAsync(getName(), LongCodec.INSTANCE, EVAL,
//
//                //如果锁已经不存在， 发布锁释放的消息
//                "if (redis.call('exists', KEYS[1]) == 0) then " +
//                        "redis.call('publish', KEYS[2], ARGV[1]); " +
//                        "return 1; " +
//                        "end;" +
//                        //如果释放锁的线程和已存在锁的线程不是同一个线程，返回null
//                        "if (redis.call('hexists', KEYS[1], ARGV[3]) == 0) then " +
//                        "return nil;" +
//                        "end; " +
//                        //通过hincrby递减1的方式，释放一次锁
//                        //若剩余次数大于0 ，则刷新过期时间
//                        "local counter = redis.call('hincrby', KEYS[1], ARGV[3], -1); " +
//                        "if (counter > 0) then " +
//                        "redis.call('pexpire', KEYS[1], ARGV[2]); " +
//                        "return 0; " +
//                        //否则证明锁已经释放，删除key并发布锁释放的消息
//                        "else " +
//                        "redis.call('del', KEYS[1]); " +
//                        "redis.call('publish', KEYS[2], ARGV[1]); " +
//                        "return 1; "+
//                        "end; " +
//                        "return nil;",
//                Arrays.<Object>asList(getName(), getChannelName()),
//                LockPubSub.unlockMessage, internalLockLeaseTime, getLockName(threadId));
//
//    }

}
