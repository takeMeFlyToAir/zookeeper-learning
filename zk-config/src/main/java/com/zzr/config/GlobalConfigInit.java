package com.zzr.config;

import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
public class GlobalConfigInit implements ApplicationContextInitializer<ConfigurableApplicationContext> {



    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        ZkClient zkClient = new ZkClient(ZKCons.HOST, 5000);
        zkClient.setZkSerializer(new MyZkSerializer());

        System.out.println("dfdsfsdf");
        zkClient.subscribeDataChanges("/aaa", new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println(dataPath+"========"+data);
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println(dataPath);
            }
        });
    }

}