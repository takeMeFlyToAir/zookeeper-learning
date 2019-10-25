package com.zzr.config.jmx;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

// agent
public class DemoAgent {

    public static void main(String[] args)  {
        try {
            MBeanServer server = ManagementFactory.getPlatformMBeanServer();
            String domainName = "com.zzr.config.jmx.DemoMBean";
            // 为MBean（下面的new Hello()）创建ObjectName实例
            ObjectName demo = new ObjectName(domainName + ":name=Demo");

            // 将demo对象注册到MBeanServer上去
            Demo demo1 = new Demo();
            server.registerMBean(demo1, demo);

            while (true){
                Thread.sleep(1000);
                System.out.println(demo1.getName());
                System.out.println(demo1.getAge());
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

