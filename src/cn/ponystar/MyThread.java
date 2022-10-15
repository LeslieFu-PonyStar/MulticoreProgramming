package cn.ponystar;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

import cn.ponystar.locks.BackoffLock;
import cn.ponystar.locks.Lock;
import cn.ponystar.locks.TASLock;
import cn.ponystar.locks.TTASLock;

public class MyThread extends Thread{
    private volatile Counter counter;
    private static final int threadNum = 10;//线程数目
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
        for(i = 0; i < 1000000/threadNum; i++){
            counter.getAndIncrement();
        }
        System.out.println(threadMXBean.getThreadCpuTime(threadID) + " " + counter.getValue());
    }
    public static void threadRun(Lock lock){
        Counter counter = new Counter(0, lock);
        MyThread[] myThreads = new MyThread[10]; 
        for(int i = 0; i < threadNum; i++){
            myThreads[i] = new MyThread(counter);
            myThreads[i].start();
        }
    }

    public static void main(String[] args) {
        //Peterson lock = new Peterson();
        BackoffLock tasLock = new BackoffLock();
        threadRun(tasLock);
    }
}
