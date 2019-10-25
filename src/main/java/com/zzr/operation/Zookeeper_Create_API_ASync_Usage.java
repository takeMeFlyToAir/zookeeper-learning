package com.zzr.operation;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by zhaozhirong on 2019/10/23.
 */
public class Zookeeper_Create_API_ASync_Usage implements Watcher {
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZooKeeper zooKeeper = new ZooKeeper(ZKCons.HOST, 5000, new Zookeeper_Create_API_ASync_Usage());
        connectedSemaphore.await();

        zooKeeper.create("/zk2-test-ephemeral1-", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,
                new IStringCallBack(),
                "I am context");

        zooKeeper.create("/zk2-test-ephemeral1-", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,
                new IStringCallBack(),
                "I am context");
        zooKeeper.create("/zk2-test-ephemeral1-", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL,
                new IStringCallBack(),
                "I am context");
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

class IStringCallBack implements AsyncCallback.StringCallback{

    /**
     *
     * @param rc   0（OK）: 接口调用成功；  -4（ConnectionLoss）客户端和服务端连接已断开；  -110（NodeExists）：指定节点已存在； -122（SessionExpired）：会话已过期
     * @param path
     * @param ctx
     * @param name
     */
    @Override
    public void processResult(int rc, String path, Object ctx, String name) {
        System.out.println("Create Path result : [" + rc + ", " + path +", "+ ctx + ", real path name: "+ name);
    }
}
