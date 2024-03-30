package io.github.ljun51.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierCase1 {

    public static void main(String[] args) {
        final int totalWorker = 5; // 工人总数
        CyclicBarrier cyclicBarrier = new CyclicBarrier(totalWorker); // 创建一个CyclicBarrier实例，并指定总工作线程数

        for (int i = 0; i < totalWorker; i++) {
            new Thread(() -> {
                System.out.println("工人" + Thread.currentThread().getId() + "已准备就绪");
                try {
                    // 线程在此等待，直到所有线程都达到这个屏障点
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println("工人" + Thread.currentThread().getId() + "开始下一阶段的工作");
            }).start();
        }
    }
}
