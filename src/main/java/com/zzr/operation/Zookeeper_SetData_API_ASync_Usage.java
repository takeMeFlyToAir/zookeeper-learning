package com.zzr.operation;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * Created by zhaozhirong on 2019/10/23.
 */
public class Zookeeper_SetData_API_ASync_Usage implements Watcher {
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zooKeeper;


    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        String path = "/"+UUID.randomUUID().toString();
        zooKeeper = new ZooKeeper(ZKCons.HOST, 5000, new Zookeeper_SetData_API_ASync_Usage());
        connectedSemaphore.await();

        zooKeeper.create(path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        zooKeeper.getData(path, true, null);

        zooKeeper.setData(path, "234".getBytes(), -1,new IStatCallBack(),null);

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

class IStatCallBack implements AsyncCallback.StatCallback{

    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        if(rc == 0){
            System.out.println("success");
        }
    }
}
