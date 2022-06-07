package com.h2pl4u.distributelock;

import org.apache.curator.CuratorZookeeperClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.atomic.AtomicInteger;

public class ZookeeperLock {
    //ZkLock的节点链接
    private static final String ZK_PATH = "/test/lock";
    private static final String LOCK_PREFIX = ZK_PATH + "/";
    private static final long WAIT_TIME = 1000L;
    //zk客户端
    CuratorFramework client = null;

    private String locked_short_path = null;
    private String locked_path = null;
    private String prior_path = null;
    final AtomicInteger lockCount = new AtomicInteger(0);
    private Thread thread;
    private CuratorZookeeperClient zkClient;

    public ZookeeperLock() throws Exception {
        synchronized (client) {
            if (zkClient.isConnected()) {
                ZooKeeper zooKeeper = zkClient.getZooKeeper();
            }
        }
    }
}
