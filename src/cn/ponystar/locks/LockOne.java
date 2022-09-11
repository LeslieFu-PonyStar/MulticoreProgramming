package cn.ponystar.locks;

import cn.ponystar.Utils;

public class LockOne implements Lock{
    private boolean[] flags = new boolean[2];

    @Override
    public void lock() {
        int i = Utils.ThreadID.get();
        int j = 1 - i;
        flags[i] = true;
        while(flags[j]){

        }
    }

    @Override
    public void unlock() {
        int i = Utils.ThreadID.get();
        flags[i] = false;
    }
}
