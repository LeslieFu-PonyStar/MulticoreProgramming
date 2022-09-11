package cn.ponystar.locks;

import cn.ponystar.Utils;

public class Peterson implements Lock{
    private boolean[] flags = new boolean[2];
    private int victim;

    @Override
    public void lock() {
        int i = Utils.ThreadID.get();
        int j = 1 - i;
        flags[i] = true;
        victim = i;
        while(flags[j] && victim == i){
            Utils.pass();
        }
    }

    @Override
    public void unlock() {
        int i = Utils.ThreadID.get();
        flags[i] = false;
    }
}
