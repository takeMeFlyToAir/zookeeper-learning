package com.zzr.operation;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * Created by zhaozhirong on 2019/10/23.
 */
public class Zookeeper_Exist_API_Sync_Usage implements Watcher {
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zooKeeper;


    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        String path = "/"+UUID.randomUUID().toString();
        zooKeeper = new ZooKeeper(ZKCons.HOST, 5000, new Zookeeper_Exist_API_Sync_Usage());
        connectedSemaphore.await();

        zooKeeper.exists(path, true);
        zooKeeper.create(path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zooKeeper.setData(path, "234".getBytes(), -1);


        zooKeeper.create(path+"/c1", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        zooKeeper.delete(path+"/c1", -1);
        zooKeeper.delete(path,-1);
        Thread.sleep(Integer.MAX_VALUE);
    }


    @Override
    public void process(WatchedEvent event) {
       try {
           if(Event.KeeperState.SyncConnected == event.getState()){
               if(Event.EventType.None == event.getType() && null == event.getPath()){
                   connectedSemaphore.countDown();
               }else if(Event.EventType.NodeCreated == event.getType()){
                   System.out.println("Node(" + event.getPath() +")Created");
                   zooKeeper.exists(event.getPath(), true);
               }else if(Event.EventType.NodeDeleted == event.getType()){
                   System.out.println("Node(" + event.getPath() +")Deleted");
                   zooKeeper.exists(event.getPath(), true);
               }else if(Event.EventType.NodeDataChanged == event.getType()){
                   System.out.println("Node(" + event.getPath() +")DataChanged");
                   zooKeeper.exists(event.getPath(), true);
               }
           }
       }catch (Exception e){

       }
    }
}
