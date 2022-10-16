package cn.ponystar.universal;

public interface Consensus<T>{
    T decide(T value);
}
