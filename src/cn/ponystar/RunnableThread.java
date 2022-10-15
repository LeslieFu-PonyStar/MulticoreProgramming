package cn.ponystar;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.CountDownLatch;

import cn.ponystar.locks.ALock;
import cn.ponystar.locks.BackoffLock;
import cn.ponystar.locks.CLHLock;
import cn.ponystar.locks.MCSLock;
import cn.ponystar.locks.TASLock;
import cn.ponystar.locks.TTASLock;

public class RunnableThread implements Runnable{
    //创建latch
    private CountDownLatch latch;
    private volatile Counter counter;
    //线程数目
    private static final int threadNum = 4;
    public RunnableThread(CountDownLatch latch, Counter counter){
        this.latch = latch;
        this.counter = counter;
    }
    @Override
    public void run() {
        //try块里是核心代码，finally是需要保证执行的
        try{
        	//每个线程均分给counter + 1
            for(int i = 0; i < 1000000/threadNum; i++){
                counter.getAndIncrement();
            }
        }finally {
        	//将该代码放入finally中，确保这段代码一定能执行，否则主线程无法结束
            latch.countDown();	//让CountDownLatch计数器减1
        }
    }
    public static void main(String[] args) {
        //TTASLock tasLock = new TTASLock();
        //ALock aLock = new ALock(threadNum);
        //CLHLock clhLock = new CLHLock();
        MCSLock mcsLock = new MCSLock();
        Counter counter = new Counter(0, mcsLock);
        CountDownLatch latch = new CountDownLatch(threadNum);
        RunnableThread runnableThread = new RunnableThread(latch, counter);
        long start = System.currentTimeMillis();
        for(int i = 0; i < threadNum; i++){
            new Thread(runnableThread).start();
        }
        try {
        	//调用await()方法，阻塞主线程，CountDownLatch计数器为0时后面的代码才能执行，以此来达到计时的效果
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //记录下结束时间
        long end = System.currentTimeMillis();
        long exeTime = end - start;
        System.out.println("消耗的时间为（毫秒）：" + exeTime + " " + counter.getValue());
    }
    
}
