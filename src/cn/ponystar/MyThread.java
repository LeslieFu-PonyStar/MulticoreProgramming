package cn.ponystar;

import cn.ponystar.locks.Peterson;

public class MyThread extends Thread{
    Counter counter;
    public MyThread(Counter counter){
        this.counter = counter;
    }

    /**
     * 线程执行代码，从counter中获取数字并判断是否为质数，如果是质数输出数字和处理线程的名字到控制台
     */
    @Override
    public void run() {
        while(counter.getValue() < 10000){
            int num = counter.getAndIncrement();
            if(Utils.isPrime(num))
                System.out.println(num + " " + Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {
        Peterson lock = new Peterson();
        Counter counter = new Counter(1, lock);
        MyThread thread1 = new MyThread(counter);
        MyThread thread2 = new MyThread(counter);
        thread1.start();
        thread2.start();
    }

}
