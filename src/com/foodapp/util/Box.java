package com.foodapp.util;
public class Box<T extends Number> {
    private T value;
    public Box(T v){ this.value = v; }
    public T get(){ return value; }
}
