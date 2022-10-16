package cn.ponystar.universal;

import java.util.concurrent.CountDownLatch;

import cn.ponystar.Utils.ThreadID;

public class RunnableThread implements Runnable{
    private CountDownLatch latch;
    private static int threadNum = CASConsensus.threadNum;//线程个数

    private Node[] head = new Node[threadNum];
    private Node tail;
    private int counter;

    public RunnableThread(CountDownLatch latch, Node tail){
        this.latch = latch;
        this.tail = tail;
        counter = 0;
        for(int i = 0; i < threadNum; i++){
            head[i] = tail;
        }
    }

    public void apply(){
        int i = ThreadID.get();
        Node prefer = new Node(null);
        while(prefer.seq == 0){
            Node before = Node.max(head);
            Node after = before.decideNext.decide(prefer);
            before.next = after;
            after.seq = before.seq + 1;
            head[i] = after;
        }
        Node current = tail.next;
        while(current != prefer){
            counter = counter + 1;
            if(current == null)
                return;
            current = current.next;
        }
        counter = counter + 1;
        tail = current;//保存每次的运行结束链表的状态，不然每次会重复加
    }

    @Override
    public void run() {
        //try块里是核心代码，finally是需要保证执行的
        try{
        	//每个线程均分给counter + 1
            for(int i = 0; i < 1000000/threadNum; i++){
                apply();
            }
            System.out.println(counter);
        }finally {
        	//将该代码放入finally中，确保这段代码一定能执行，否则主线程无法结束
            latch.countDown();	//让CountDownLatch计数器减1
        }
        
    }
    public static void main(String[] args) {
        Node tail = new Node(1);
        CountDownLatch latch = new CountDownLatch(threadNum);
        RunnableThread runnableThread = new RunnableThread(latch, tail);
        long start = System.currentTimeMillis();
        for(int i = 0; i < threadNum; i++){
            new Thread(runnableThread).start();
        }
        try {
        	//调用await()方法，阻塞主线程，CountDownLatch计数器为0时后面的代码才能执行，以此来达到计时的效果
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //记录下结束时间
        long end = System.currentTimeMillis();
        long exeTime = end - start;
        System.out.println("消耗的时间为（毫秒）：" + exeTime);
    }
}

