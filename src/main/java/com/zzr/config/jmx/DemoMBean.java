package com.zzr.config.jmx;

// 接口
public interface DemoMBean {

    public String getName();
    public int getAge();
    public void setName(String name);
    public void setAge(int age);
    void init();
}
