package com.util.collections.list;

public interface List<T> extends Iterable<T> {

    boolean add(T val);

    boolean add(T val, int index);

    T get(int index);

    boolean remove(T val);

    boolean contains(T val);

    int size();

    boolean isEmpty();

}
