package cn.ponystar.universal;

import cn.ponystar.Utils.ThreadID;

public class LFUniversal extends Thread{
    private static int threadNum = CASConsensus.threadNum;//线程个数

    private Node[] head = new Node[threadNum];
    private Node tail;
    private int counter;

    public LFUniversal(Node tail){
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
        for(int i = 0; i < 100000; i++){
            apply();
        }
        System.out.println(counter);
    }
    public static void main(String[] args) {
        Node tail = new Node(1);
        LFUniversal[] lfu = new LFUniversal[threadNum];//问题：新建了四个不同的tail，但他们必须维护同一个共识，不然无法保证唯一的结果
        for(int i = 0; i < threadNum; i++){
            lfu[i] = new LFUniversal(tail);
            lfu[i].start();
        }
    }
}