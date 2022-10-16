package cn.ponystar.universal;

import cn.ponystar.Utils.ThreadID;

public class WFUniversal extends Thread{
    private static int threadNum = CASConsensus.threadNum;//线程个数

    private Node[] head = new Node[threadNum];
    private Node[] announce = new Node[threadNum];
    private Node tail;
    private int counter;

    public WFUniversal(Node tail){
        this.tail = tail;
        counter = 0;
        for(int i = 0; i < threadNum; i++){
            head[i] = tail;
            announce[i] = tail;
        }
    }

    public void apply(){
        int i = ThreadID.get();
        announce[i] = new Node(null);
        head[i] = Node.max(head);
        while(announce[i].seq == 0){
            Node before = Node.max(head);
            Node prefer = new Node(null);
            Node help = announce[(before.seq + 1) % threadNum];
            if(help.seq == 0)
                prefer = help;
            else
                prefer = announce[i];
            Node after = before.decideNext.decide(prefer);
            before.next = after;
            after.seq = before.seq + 1;
            head[i] = after;
        }
        Node current = tail.next;
        
        while(current != announce[i]){
            counter = counter + 1;
            current = current.next;
        }
        head[i] = announce[i];
        counter = counter + 1;
        tail = current;//保存每次的运行结束链表的状态，不然每次会重复加
    }
    @Override
    public void run() {
        long start = System.currentTimeMillis();
        for(int i = 0; i < 1000000/threadNum; i++){
            apply();
        }
        long end = System.currentTimeMillis();
        long exeTime = end - start;
        System.out.println("消耗的时间为（毫秒）：" + exeTime + " " + counter);
    }
    public static void main(String[] args) {
        Node tail = new Node(1);
        WFUniversal[] lfu = new WFUniversal[threadNum];//问题：新建了四个不同的tail，但他们必须维护同一个共识，不然无法保证唯一的结果
        for(int i = 0; i < threadNum; i++){
            lfu[i] = new WFUniversal(tail);
            lfu[i].start();
        }
    }
}

