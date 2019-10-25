package com.zzr.operation;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by zhaozhirong on 2019/10/23.
 */
public class Zookeeper_Create_API_Sync_Usage implements Watcher {
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZooKeeper zooKeeper = new ZooKeeper(ZKCons.HOST, 5000, new Zookeeper_Create_API_Sync_Usage());
        connectedSemaphore.await();

        String s = zooKeeper.create("/zk-test-ephemeral1-", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("success create znode: "+ s);

        s = zooKeeper.create("/zk-test-ephemeral1-", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("success create znode: "+ s);
    }


    @Override
    public void process(WatchedEvent event) {
        System.out.println("Receive watched event: "+ event);
        if(Event.KeeperState.SyncConnected == event.getState()){
            connectedSemaphore.countDown();
        }
    }
}
