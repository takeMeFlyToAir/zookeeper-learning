package com.zzr.demo;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

/**
 * Created by zhaozhirong on 2019/10/22.
 */
public class ZKUtil {

    private static ZooKeeper zooKeeper;

    private static ZooKeeperConnection connection;

    static {
        try {
            connection = new ZooKeeperConnection();
            zooKeeper = connection.connect("127.0.0.1:2181");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void create(String path, byte[] data) throws KeeperException, InterruptedException {
        zooKeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
    }

    public static Stat exists(String path) throws KeeperException, InterruptedException {
        return zooKeeper.exists(path, true);
    }

    public static byte[] get(String path) throws KeeperException, InterruptedException {
        return zooKeeper.getData(path,true, null);
    }

    public static void update(String path, byte[] data) throws
            KeeperException,InterruptedException {
        zooKeeper.setData(path, data, zooKeeper.exists(path,true).getVersion());
    }

    public static List<String> children(String path) throws KeeperException, InterruptedException {
        return zooKeeper.getChildren(path, true);
    }

    public static void delete(String path) throws KeeperException,InterruptedException {
        zooKeeper.delete(path,zooKeeper.exists(path,true).getVersion());
    }

    public static void main(String[] args) throws KeeperException, InterruptedException {

        String rootPath = "/root";
        String zzrPath = "/root/zzr";
        String hyyPath = "/root/zzr";
        String rootValue = "zzrHyy";
        String zzrValue = "zzr";
        String hyyValue = "hyy";

        create("/root/path6",zzrValue.getBytes());
        create("/root/path7",zzrValue.getBytes());
//
//        if(exists(zzrPath) == null){
//            create(zzrPath,zzrValue.getBytes());
//        }
//        if(exists(hyyPath) == null){
//            create(hyyPath,hyyValue.getBytes());
//        }
//        if(exists(rootPath) == null){
//            create(rootPath,rootValue.getBytes());
//        }
//
//
//        List<String> children = children(rootPath);
//        System.out.println(rootPath+" children is ======");
//        children.forEach(value -> System.out.println(value));
//
//        if(exists(zzrPath) != null){
//            byte[] bytes = get(zzrPath);
//            System.out.println(zzrPath+" is exist," +" data is 【"+new String(bytes)+"】");
//            System.out.println("delete node 【"+zzrPath+"】");
//            delete(zzrPath);
//            System.out.println("create node 【"+zzrPath+"】");
//            create(zzrPath, zzrValue.getBytes());
//            System.out.println("update node 【"+zzrPath+"】");
//            update(zzrPath,hyyValue.getBytes());
//            bytes = get(zzrPath);
//            System.out.println(zzrPath + "update after, data is 【"+new String(bytes)+"】");
//        }
//
        connection.close();
    }

}
