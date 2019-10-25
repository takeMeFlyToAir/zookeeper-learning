package com.zzr.zkClient;

import com.zzr.operation.ZKCons;
import org.I0Itec.zkclient.ZkClient;

/**
 * Created by zhaozhirong on 2019/10/23.
 */
public class Delete_Node_Sample {

    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient(ZKCons.HOST, 5000);
        zkClient.deleteRecursive("/ds/sd/sd/d");
    }

}
