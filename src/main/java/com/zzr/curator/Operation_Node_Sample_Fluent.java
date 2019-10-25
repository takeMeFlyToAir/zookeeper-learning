package com.zzr.curator;

import com.zzr.operation.ZKCons;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * Created by zhaozhirong on 2019/10/23.
 */
public class Operation_Node_Sample_Fluent {
    static ExponentialBackoffRetry exponentialBackoffRetry = new ExponentialBackoffRetry(1000, 3);
    static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString(ZKCons.HOST)
            .sessionTimeoutMs(5000)
            .retryPolicy(exponentialBackoffRetry)
            .build();
    public static void main(String[] args) throws Exception {

        String path = "/base";
        String oldData = "base";
        String newData = "newbase";

        client.start();

        //创建
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path,oldData.getBytes());

        //获取数据
        Stat stat = new Stat();
        byte[] bytes = client.getData().storingStatIn(stat).forPath(path);
        System.out.println(new String(bytes));

        //删除
        client.delete().deletingChildrenIfNeeded().withVersion(stat.getVersion()).forPath(path);
        client.delete().guaranteed().deletingChildrenIfNeeded().forPath(path);

    }

}
