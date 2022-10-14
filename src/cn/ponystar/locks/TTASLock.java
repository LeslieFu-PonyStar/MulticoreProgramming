package cn.ponystar.locks;

import java.util.concurrent.atomic.AtomicBoolean;

public class TTASLock implements Lock{
    AtomicBoolean state = new AtomicBoolean(false);
    @Override
    public void lock() {
        // 将true值放入state中告诉其它线程我已经占用锁了，如果其它线程读到的是true证明锁被占用中还没释放，遂自旋
        while(true){
            while(state.get() == true){
            }
        if(!state.getAndSet(true))
            return;
    }
        
    }

    @Override
    public void unlock() {
        // 结束临界区的访问之后，将state置为false表示锁空闲，可占用
        state.set(false);
    }
}
