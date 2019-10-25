package com.zzr.operation;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * Created by zhaozhirong on 2019/10/23.
 */
public class Zookeeper_SetData_API_Sync_Usage implements Watcher {
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zooKeeper;

    private static Stat stat = new Stat();

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        String path = "/"+UUID.randomUUID().toString();
        zooKeeper = new ZooKeeper(ZKCons.HOST, 5000, new Zookeeper_SetData_API_Sync_Usage());
        connectedSemaphore.await();

        zooKeeper.create(path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        zooKeeper.getData(path, true, null);

        Stat stat = zooKeeper.setData(path, "234".getBytes(), -1);
        System.out.println(stat.getCzxid() +"," + stat.getMzxid() +"," + stat.getVersion());

        Stat stat1 = zooKeeper.setData(path, "333".getBytes(), stat.getVersion());
        System.out.println(stat1.getCzxid() +"," + stat1.getMzxid() +"," + stat1.getVersion());


        try {
            zooKeeper.setData(path,"33333".getBytes(), stat1.getVersion());
        }catch (KeeperException e){
            System.out.println("Error: "+ e.code() +"," + e.getMessage());
        }

        Thread.sleep(Integer.MAX_VALUE);
    }


    @Override
    public void process(WatchedEvent event) {
        System.out.println("Receive watched event: "+ event);
        if(Event.KeeperState.SyncConnected == event.getState()){
           if(Event.EventType.None == event.getType() && null == event.getPath()){
               connectedSemaphore.countDown();
           }
        }
    }
}
