package cn.ponystar;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

import cn.ponystar.locks.TASLock;
import cn.ponystar.locks.TTASLock;

public class MyThread extends Thread{
    private volatile Counter counter;
    private static final int threadNum = 10;
    private long threadID;
    private ThreadMXBean threadMXBean =  ManagementFactory.getThreadMXBean();
    public MyThread(Counter counter){
        this.counter = counter;
        this.threadID = super.getId();
    }
    /**
     * 线程执行代码，每个对counter加100万/n次
     */
    @Override
    public void run() {
        int i = 0;
        for(i = 0; i < 100000; i++){
            counter.getAndIncrement();
        }
        System.out.println(threadMXBean.getThreadCpuTime(threadID) + " " + counter.getValue());
    }

    public static void main(String[] args) {
        //Peterson lock = new Peterson();
        TASLock tasLock = new TASLock();
        Counter counter = new Counter(0, tasLock);
        MyThread[] myThreads = new MyThread[10]; 
        for(int i = 0; i < threadNum; i++){
            myThreads[i] = new MyThread(counter);
            myThreads[i].start();
        }
    }

}
