package cn.ponystar.locks;

import java.util.concurrent.atomic.AtomicReference;

public class MCSLock implements Lock{
    AtomicReference<QNode> tail;
    ThreadLocal<QNode> myNode;
    public MCSLock(){
        tail = new AtomicReference<QNode>(null);
        myNode = new ThreadLocal<QNode>(){
            protected QNode initialValue() {
                return new QNode();
            }
        };
    }
    @Override
    public void lock() {
        QNode qNode = myNode.get();
        QNode pred = tail.getAndSet(qNode);
        if(pred != null){
            qNode.locked = true;
            pred.next = qNode;
            while(qNode.locked) {}
        }
    }
    @Override
    public void unlock() {
        QNode qNode = myNode.get();
        if(qNode.next == null){
            if(tail.compareAndSet(qNode, null))
                return;
            while(qNode.next == null) {}//此时下一个线程已经挂上了tail但是还没加到节点链表里面
        }
        qNode.next.locked = false;
        qNode.next = null;//当前节点脱离链表
    }
    class QNode {
        volatile boolean locked = false;
        volatile QNode next = null;
    }
}
