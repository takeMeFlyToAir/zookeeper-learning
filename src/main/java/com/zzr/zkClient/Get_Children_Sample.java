package com.zzr.zkClient;

import com.zzr.operation.ZKCons;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;
import java.util.UUID;

/**
 * Created by zhaozhirong on 2019/10/23.
 */
public class Get_Children_Sample {

    public static void main(String[] args) throws InterruptedException {
       ZkClient zkClient = new ZkClient(ZKCons.HOST, 5000);
       String path = "/"+ UUID.randomUUID().toString();

       zkClient.subscribeChildChanges(path, new IZkChildListener() {
           @Override
           public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
               System.out.println(parentPath + " 's child changed, currentChilds: "+ currentChilds);
           }
       });

       zkClient.createPersistent(path);
       Thread.sleep(1000);
        System.out.println(zkClient.getChildren(path));
        Thread.sleep(1000);
        zkClient.createPersistent(path +"/c1");
        Thread.sleep(1000);
        zkClient.delete(path+"/c1");
        Thread.sleep(1000);
        zkClient.delete(path);
        Thread.sleep(Integer.MAX_VALUE);
    }

}
