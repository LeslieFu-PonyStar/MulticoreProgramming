package cn.ponystar.locks;

import cn.ponystar.Utils;

public class FastPath implements Lock{
    private int victim;
    private LockOne lock = new LockOne();
    @Override
    public void lock() {
        int i = Utils.ThreadID.get();
        victim = i;
        if(victim != i){
            lock.lock();
        }
    }

    @Override
    public void unlock() {
        lock.unlock();
    }
}