package com.zzr.curator.count;

import com.zzr.operation.ZKCons;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * Created by zhaozhirong on 2019/10/24.
 */
public class Recipes_DistAtomicInt {

    static String distatomicint_path = "/curator_recipes_distatomicint_path";

    static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString(ZKCons.HOST)
            .retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();

    public static void main(String[] args) throws Exception {
        client.start();
        DistributedAtomicInteger distributedAtomicInteger = new DistributedAtomicInteger(client, distatomicint_path, new RetryNTimes(3, 1000));
        AtomicValue<Integer> add = distributedAtomicInteger.add(8);
        System.out.println(add.succeeded());
    }

}
