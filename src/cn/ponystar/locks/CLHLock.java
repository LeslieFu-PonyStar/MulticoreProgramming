package cn.ponystar.locks;

import java.util.concurrent.atomic.AtomicReference;

public class CLHLock implements Lock{
    AtomicReference<QNode> tail;
    ThreadLocal<QNode> myPred;
    ThreadLocal<QNode> myNode;
    public CLHLock() {
        tail = new AtomicReference<QNode>(new QNode());
        myNode = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                return new QNode();
            }
        };
        myPred = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                return null;
            }
        };
    }
    @Override
    public void lock() {
        QNode qNode = myNode.get();
        qNode.locked = true;
        QNode pred = tail.getAndSet(qNode);
        myPred.set(pred);
        while(pred.locked){}
    }
    @Override
    public void unlock() {
        QNode qNode = myNode.get();
        qNode.locked = false;//线程释放锁只能使后面线程的cache无效
        myNode.set(myPred.get());
    }
    /**
     * QNode
     */ 
    class QNode {
        volatile boolean locked = false;
    }
}
