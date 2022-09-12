package cn.ponystar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Utils {
    /**
     * 判断一个数是否为质数的函数
     * @param num the number ready to judge
     * @return num is prime or not
     */
    public static boolean isPrime(int num){
        int frontier = (int)Math.sqrt(num);
        for(int i = 2; i < frontier + 1; i++){
            if(num % i == 0)
                return false;
        }
        return true;
    }

    /**
     * 线程ID工具类，使用的是线程安全的hashmap和integer，在后续的学习再尝试自己造轮子
     */
    public static class ThreadID{
        private static final Map<Long, Integer> threadIDs = new ConcurrentHashMap<>();
        private static final AtomicInteger counter = new AtomicInteger();
        public static int get(){
            long id = Thread.currentThread().getId();
            return threadIDs.computeIfAbsent(id, key -> counter.getAndIncrement());
        }
    }

    /**
     * 空操作，否则peterson算法会出现未知错误
     */
    public static void pass(){
        System.out.println();
    }

    private void isPrimeTest(){
        ArrayList<Integer> primes = new ArrayList<>();
        for(int i = 2; i < 100; i++){
            if(isPrime(i)){
                primes.add(i);
            }
        }
        System.out.println(primes);
    }
}
