package cn.ponystar.universal;

public class Node {
    public Invoc invoc;
    public Consensus<Node> decideNext;
    public Node next;
    public int seq;
    public Node(Invoc invoc){
        this.invoc = invoc;
        this.decideNext = new CASConsensus<Node>();//每个节点都有一个一致性协议，每个节点都选出一个线程来进行执行    
        this.seq = 0;
    }
    public Node(int seq){
        this.invoc = null;
        this.decideNext = new CASConsensus<Node>();//每个节点都有一个一致性协议，每个节点都选出一个线程来进行执行    
        this.seq = 0;
    }
    //输出seq序号最大的节点
    public static Node max(Node[] array){
        Node max = array[0];
        for(int i = 0; i < array.length; i++){
            if(max.seq < array[i].seq)
                max = array[i];
        }
        return max;
    }
}
