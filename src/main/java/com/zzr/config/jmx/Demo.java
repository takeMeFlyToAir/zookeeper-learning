package com.zzr.config.jmx;

// 具体实现
public class Demo implements DemoMBean {

    private String name;
    private int age;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public void init() {
        this.age = 10;
        this.name = "hehehehehe";
    }
}