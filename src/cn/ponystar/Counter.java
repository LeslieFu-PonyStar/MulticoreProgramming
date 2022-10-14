package cn.ponystar;

import cn.ponystar.locks.Lock;

public class Counter {
    private int value;
    private final Lock lock;
    public Counter(int initValue, Lock lock){
        this.value = initValue;
        this.lock = lock;
    }

    public int getValue() {
        return value;
    }

    public int getAndIncrement(){
        lock.lock();
        try{
            value = value + 1;
            //System.out.println(value);
            return value;
        }finally {
            lock.unlock();
        }
    }
}
