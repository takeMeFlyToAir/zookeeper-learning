package com.zzr.curator;

import com.zzr.operation.ZKCons;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Created by zhaozhirong on 2019/10/23.
 */
public class Create_Session_Sample_Fluent {

    public static void main(String[] args) {
        ExponentialBackoffRetry exponentialBackoffRetry = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(ZKCons.HOST)
                .sessionTimeoutMs(5000)
                .retryPolicy(exponentialBackoffRetry)
                .namespace("base")//基于某一个命名空间
                .build();

        client.start();

    }

}
