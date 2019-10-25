package com.zzr.operation;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by zhaozhirong on 2019/10/23.
 */
public class Zookeeper_Delete_API_Usage_Simple implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZooKeeper zooKeeper = new ZooKeeper(ZKCons.HOST, 5000, new Zookeeper_Delete_API_Usage_Simple());
        System.out.println(zooKeeper.getState());
        connectedSemaphore.await();
        /**
         * version为-1，则为删除所有版本的节点
         */
        zooKeeper.create("/ccc", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zooKeeper.delete("/ccc",-1);
        zooKeeper.create("/ccc", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zooKeeper.delete("/ccc", -1, new AsyncCallback.VoidCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx) {
                System.out.println("delete Path result : [" + rc + ", " + path +", "+ ctx);
            }
        },"i am context zzr");
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
