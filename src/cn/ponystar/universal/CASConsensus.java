package cn.ponystar.universal;

import java.util.concurrent.atomic.AtomicInteger;

import cn.ponystar.Utils.ThreadID;

public class CASConsensus<T> implements Consensus<T>{
    private AtomicInteger r = new AtomicInteger(-1);
    public static int threadNum = 4;
    protected T[] proposed = (T[]) new Object[threadNum];
    @Override
    public T decide(T value) {
        // TODO Auto-generated method stub
        int i = ThreadID.get();
        proposed[i] = value;
        if(r.compareAndSet(-1, i))
            return proposed[i];
        else
            return proposed[r.get()];
        }
}
