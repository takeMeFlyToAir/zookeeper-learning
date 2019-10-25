package com.zzr.operation;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by zhaozhirong on 2019/10/23.
 */
public class Zookeeper_Constructor_Usage_With_SID_PASSWD implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper(ZKCons.HOST, 5000, new Zookeeper_Constructor_Usage_With_SID_PASSWD());
        connectedSemaphore.await();

        long sessionId = zooKeeper.getSessionId();
        byte[] sessionPasswd = zooKeeper.getSessionPasswd();

        //使用非法的sessionId 和 sessionPasswd连接
        zooKeeper = new ZooKeeper(ZKCons.HOST, 5000, new Zookeeper_Constructor_Usage_With_SID_PASSWD(), 1L, "test".getBytes());

        //使用正确的sessionId 和 sessionPasswd连接
        zooKeeper = new ZooKeeper(ZKCons.HOST, 5000, new Zookeeper_Constructor_Usage_With_SID_PASSWD(), sessionId, sessionPasswd);

        Thread.sleep(Integer.MAX_VALUE);
    }


    @Override
    public void process(WatchedEvent event) {
        System.out.println("Receive watched event: "+ event);
        if(Event.KeeperState.SyncConnected == event.getState()){
            connectedSemaphore.countDown();
        }
    }
}
