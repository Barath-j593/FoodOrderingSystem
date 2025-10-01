package com.foodapp.util;
import java.io.Serializable;
public class Pair<K,V> implements Serializable {
    private K key; private V value;
    public Pair(K k, V v){ this.key=k; this.value=v; }
    public K getKey(){ return key; } public V getValue(){ return value; }
    public static <T> void printData(T data){ System.out.println(data); }
}
