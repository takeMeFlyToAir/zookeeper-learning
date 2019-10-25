package com.zzr.operation;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by zhaozhirong on 2019/10/23.
 */
public class Zookeeper_GetData_API_Sync_Usage implements Watcher {
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zooKeeper;

    private static Stat stat = new Stat();

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        String path = "/5zk-book";
        zooKeeper = new ZooKeeper(ZKCons.HOST, 5000, new Zookeeper_GetData_API_Sync_Usage());
        connectedSemaphore.await();

        zooKeeper.create(path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        System.out.println(new String(zooKeeper.getData(path, true, stat)));

        System.out.println(stat.getCzxid() +"," + stat.getMzxid() +"," + stat.getVersion());
        zooKeeper.setData(path,"234".getBytes(), -1);
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
           }else if(event.getType() == Event.EventType.NodeDataChanged){
               try {
                   System.out.println(new String(zooKeeper.getData(event.getPath(), true, stat)));
                   System.out.println(stat.getCzxid() +"," + stat.getMzxid() +"," + stat.getVersion());
               }catch (Exception e){

               }
           }
        }
    }
}
