package com.zzr.curator.barrier;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhaozhirong on 2019/10/24.
 */
public class Recipes_CyclicBarrier {

    public static CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(new Runner("zzr1"));
        executorService.submit(new Runner("zzr2"));
        executorService.submit(new Runner("zzr3"));
        executorService.shutdown();
    }

}

class Runner implements Runnable{

    private String name;

    public Runner(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(name + "准备好了");
        try {
            Recipes_CyclicBarrier.cyclicBarrier.await();
        }catch (Exception e){

        }
        System.out.println(name + "起跑！");
    }
}
