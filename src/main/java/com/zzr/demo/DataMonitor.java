package com.zzr.demo;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by zhaozhirong on 2019/10/22.
 */
public class DataMonitor {

    ZooKeeperConnection zooKeeperConnection;
    ZooKeeper connect;
    public static CountDownLatch countDownLatch = new CountDownLatch(5);


    public DataMonitor() throws IOException, InterruptedException {
        this.zooKeeperConnection = new ZooKeeperConnection();
        this.connect = zooKeeperConnection.connect("127.0.0.1:2181");
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
      DataMonitor dataMonitor = new DataMonitor();
      dataMonitor.execute();
      countDownLatch.await();
    }

    private void execute() throws KeeperException, InterruptedException {
        connect.getChildren("/root",new MyWatcher());
    }


    private class MyWatcher implements Watcher{
        @Override
        public void process(WatchedEvent watchedEvent) {
            try {
                System.out.println("获得监听事件,path：" + watchedEvent.getPath() + ";state：" + watchedEvent.getState() + ";type：" + watchedEvent.getType());
                connect.getChildren("/root",new MyWatcher());
                countDownLatch.countDown();
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
