package cn.ponystar.locks;

import java.util.concurrent.atomic.AtomicInteger;

public class ALock implements Lock{
    ThreadLocal<Integer> mySlotIndex = new ThreadLocal<Integer>(){
        protected Integer initialValue(){
            return 0;
        }
    };
    protected AtomicInteger tail;
    volatile boolean[] flag;
    protected int size;
    public ALock(int capacity){
        this.size = capacity;
        this.tail = new AtomicInteger(0);
        this.flag = new boolean[capacity];
        flag[0] = true;
    }
    
    @Override
    public void lock() {
        //线程先占取slot等待前一个线程激活
        int slot = tail.getAndIncrement() % size;
        mySlotIndex.set(slot);
        while(!flag[slot]){};//这里是读本地的副本
    }
    @Override
    public void unlock() {
        //重置slot标志位为非占用临界区状态并激活下一个槽的线程
        int slot = mySlotIndex.get();
        //只有下面两行导致其它线程的副本失效
        flag[slot] = false;
        flag[(slot + 1) % size] = true;
    }
    
}
