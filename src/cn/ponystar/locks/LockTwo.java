package cn.ponystar.locks;

import cn.ponystar.Utils;

public class LockTwo implements Lock{
    private int victim;
    @Override
    public void lock() {
        int i = Utils.ThreadID.get();
        victim = i;
        while(victim == i){

        }
    }

    @Override
    public void unlock() {
    }
}
