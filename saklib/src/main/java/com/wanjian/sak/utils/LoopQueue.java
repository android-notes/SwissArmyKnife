package com.wanjian.sak.utils;

import java.util.LinkedList;

public class LoopQueue<T> {
    private int size;
    private LinkedList<T> queue;
    private int cursor;

    public LoopQueue(int size) {
        if (size < 1) {
            throw new IllegalArgumentException();
        }
        this.size = size;
        queue = new LinkedList<T>();
    }

    public T take() {
        if (has() == false) {
            throw new IllegalStateException("end!");
        }
        return queue.get(cursor++);
    }

    public void append(T t) {
        if (queue.size() == size) {
            queue.remove(0);
            cursor--;
            if (cursor < 0) {
                cursor = 0;
            }
        }
        queue.add(t);
    }

    public boolean has() {
        return cursor != queue.size();
    }

    public int maxSize() {
        return size;
    }

    public int size() {
        return queue.size();
    }

    public void reset() {
        cursor = 0;
    }

    public void clean() {
        cursor = 0;
        queue.clear();
    }
}
