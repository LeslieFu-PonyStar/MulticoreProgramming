package cn.ponystar;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

import cn.ponystar.locks.ALock;
import cn.ponystar.locks.BackoffLock;
import cn.ponystar.locks.CLHLock;
import cn.ponystar.locks.Lock;
import cn.ponystar.locks.MCSLock;
import cn.ponystar.locks.TASLock;
import cn.ponystar.locks.TTASLock;

public class MyThread extends Thread{
    private volatile Counter counter;
    private static final int threadNum = 2;//线程数目
    private long threadID;
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
        long start = System.currentTimeMillis();
        for(i = 0; i < 10000/threadNum; i++){
            counter.getAndIncrement();
        }
        System.out.println(System.currentTimeMillis() - start + " " + counter.getValue());
    }
    public static void threadRun(Lock lock){
        Counter counter = new Counter(0, lock);
        MyThread[] myThreads = new MyThread[threadNum]; 
        for(int i = 0; i < threadNum; i++){
            myThreads[i] = new MyThread(counter);
            myThreads[i].start();
        }
    }

    public static void main(String[] args) {
        //Peterson lock = new Peterson();
        TASLock tasLock = new TASLock();
        threadRun(tasLock);

        // TTASLock ttasLock = new TTASLock();
        // threadRun(ttasLock);

        // BackoffLock backoffLock = new BackoffLock();
        // threadRun(backoffLock);

        // ALock aLock = new ALock(threadNum);
        // threadRun(aLock);

        // ALock aLock = new ALock(threadNum);
        // threadRun(aLock);

        // CLHLock clhLock = new CLHLock();
        // threadRun(clhLock);

        // MCSLock mcsLock = new MCSLock();
        // threadRun(mcsLock);
    }
}
