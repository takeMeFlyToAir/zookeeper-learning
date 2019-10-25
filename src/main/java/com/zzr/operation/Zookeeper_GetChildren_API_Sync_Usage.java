package com.zzr.operation;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by zhaozhirong on 2019/10/23.
 */
public class Zookeeper_GetChildren_API_Sync_Usage implements Watcher {
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        String path = "/zk-book";
        zooKeeper = new ZooKeeper(ZKCons.HOST, 5000, new Zookeeper_GetChildren_API_Sync_Usage());
        connectedSemaphore.await();

        zooKeeper.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        zooKeeper.create(path+"/c1", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        List<String> children = zooKeeper.getChildren(path, true);
        System.out.println(children);

        zooKeeper.create(path+"/c2", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zooKeeper.create(path+"/c3", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        Thread.sleep(Integer.MAX_VALUE);
    }


    @Override
    public void process(WatchedEvent event) {
        System.out.println("Receive watched event: "+ event);
        if(Event.KeeperState.SyncConnected == event.getState()){
           if(Event.EventType.None == event.getType() && null == event.getPath()){
               connectedSemaphore.countDown();
           }else if(event.getType() == Event.EventType.NodeChildrenChanged){
               try {
                   System.out.println("ReGetChild:" + zooKeeper.getChildren(event.getPath(), true));
               }catch (Exception e){

               }
           }
        }
    }
}
