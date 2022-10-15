package cn.ponystar.locks;

import java.util.concurrent.atomic.AtomicBoolean;

public class BackoffLock implements Lock{
    private AtomicBoolean state = new AtomicBoolean(false);
    private static final int MIN_DELAY = 100;
    private static final int MAX_DELAY = 1000;

    @Override
    public void lock() {
        // TODO Auto-generated method stub
        Backoff backoff = new Backoff(MIN_DELAY, MAX_DELAY);
        while(true){
            while(state.get() == true){
            }
        if(!state.getAndSet(true)){
            return;
        }else{
            try {
                backoff.backoff();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } 
        }
    }

    @Override
    public void unlock() {
        // TODO Auto-generated method stub
        state.set(false);
    }

}
