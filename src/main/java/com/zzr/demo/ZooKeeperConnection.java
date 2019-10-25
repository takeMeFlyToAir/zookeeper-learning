package com.zzr.demo;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by zhaozhirong on 2019/10/22.
 */
public class ZooKeeperConnection {

    private ZooKeeper zooKeeper;

    final CountDownLatch connectedSignal = new CountDownLatch(1);

    public ZooKeeper connect(String host) throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper(host, 5000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if(event.getState() == Event.KeeperState.SyncConnected){
                    connectedSignal.countDown();
                }
            }
        });
        connectedSignal.await();
        return zooKeeper;
    }


    // Method to disconnect from zookeeper server
    public void close() throws InterruptedException {
        zooKeeper.close();
    }
}
