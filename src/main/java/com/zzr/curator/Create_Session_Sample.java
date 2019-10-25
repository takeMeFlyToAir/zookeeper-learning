package com.zzr.curator;

import com.zzr.operation.ZKCons;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Created by zhaozhirong on 2019/10/23.
 */
public class Create_Session_Sample {

    public static void main(String[] args) {
        ExponentialBackoffRetry exponentialBackoffRetry = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(ZKCons.HOST, 5000, 3000, exponentialBackoffRetry);

        client.start();

    }

}
