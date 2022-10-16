package cn.ponystar.universal;

import cn.ponystar.Utils.ThreadID;
/**
 * 该类测试共识协议是否能正常运行，构造了一个协议对象，如果各线程返回的winner是一致的，则协议有效
 */
public class ConsensusTest extends Thread{
    private CASConsensus cas;
    public ConsensusTest(CASConsensus cas){
        this.cas = cas;
    }
    @Override
    public void run() {
        int i = ThreadID.get();
        System.out.println(cas.decide(i));
    }

    public static void main(String[] args) {
        CASConsensus cas = new CASConsensus<Integer>();
        ConsensusTest[] ct = new ConsensusTest[4];
        for(int i = 0; i < 4; i++){
            ct[i] = new ConsensusTest(cas);
            ct[i].start();
        }

    }
}