package com.zzr.operation;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;


/**
 * Created by zhaozhirong on 2019/10/23.
 */
public class AuthSample {

    final static String PATH = "/zk-book-auth_test";

    public static void main(String[] args) throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper(ZKCons.HOST, 5000, null);

        zooKeeper.addAuthInfo("digest","foo:true".getBytes());

        zooKeeper.create(PATH, "init".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);
        Thread.sleep(Integer.MAX_VALUE);
    }

}
