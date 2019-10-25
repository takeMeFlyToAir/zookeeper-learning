package com.zzr.curator;

import com.zzr.operation.ZKCons;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * Created by zhaozhirong on 2019/10/23.
 */
public class NodeCache_Sample {

    static String path = "/zk111/nodecache";
    static String oldData = "init";
    static ExponentialBackoffRetry exponentialBackoffRetry = new ExponentialBackoffRetry(1000, 3);
    static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString(ZKCons.HOST)
            .sessionTimeoutMs(5000)
            .retryPolicy(exponentialBackoffRetry)
            .build();

    public static void main(String[] args) throws Exception {
        client.start();

        final NodeCache nodeCache = new NodeCache(client, path, false);

        nodeCache.start(true);
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("Node data update, new data:" +
                new String(nodeCache.getCurrentData().getData()));
            }
        });
        client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path,oldData.getBytes());

//        client.setData().forPath(path,"new".getBytes());
//        Thread.sleep(1000);
//        client.setData().forPath(path,"new".getBytes());
//        Thread.sleep(1000);
//        client.setData().forPath(path,"new".getBytes());
        Thread.sleep(2000);
        client.setData().forPath(path,"dsfsdf".getBytes());
        client.delete().guaranteed().deletingChildrenIfNeeded().forPath(path);
        System.out.println(33);
        Thread.sleep(Integer.MAX_VALUE);

    }


}
