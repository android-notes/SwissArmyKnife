package com.wanjian.sak.proxy;

import android.support.annotation.NonNull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ProxyArrayList<E> extends ArrayList<E> {
    private ArrayList origin;
    private Method removeRangeMethod;

    public ProxyArrayList(ArrayList origin) {
        super();
        this.origin = origin;
        if (origin == null) {
            throw new IllegalArgumentException("origin can not be null!");
        }
    }

    @Override
    public boolean add(E object) {
        return origin.add(object);
    }

    @Override
    public void add(int index, E object) {
        origin.add(index, object);
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        return origin.addAll(collection);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> collection) {
        return origin.addAll(index, collection);
    }

    @Override
    public void clear() {
        origin.clear();
    }

    @Override
    public Object clone() {
        return origin.clone();
    }

    @Override
    public void ensureCapacity(int minimumCapacity) {
        origin.ensureCapacity(minimumCapacity);
    }

    @Override
    public E get(int index) {
        return (E) origin.get(index);
    }

    @Override
    public int size() {
        return origin.size();
    }

    @Override
    public boolean isEmpty() {
        return origin.isEmpty();
    }

    @Override
    public boolean contains(Object object) {
        return origin.contains(object);
    }

    @Override
    public int indexOf(Object object) {
        return origin.indexOf(object);
    }

    @Override
    public int lastIndexOf(Object object) {
        return origin.lastIndexOf(object);
    }

    @Override
    public E remove(int index) {
        return (E) origin.remove(index);
    }

    @Override
    public boolean remove(Object object) {
        return origin.remove(object);
    }

    @Override
    public E set(int index, E object) {
        return (E) origin.set(index, object);
    }

    @Override
    public Object[] toArray() {
        return origin.toArray();
    }

    @Override
    public <T> T[] toArray(T[] contents) {
        return (T[]) origin.toArray(contents);
    }

    @Override
    public void trimToSize() {
        origin.trimToSize();
    }

    @NonNull
    @Override
    public Iterator<E> iterator() {
        return origin.iterator();
    }

    @Override
    public int hashCode() {
        return origin.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return origin.equals(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        return origin.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int location) {
        return origin.listIterator(location);
    }

    @Override
    public List<E> subList(int start, int end) {
        return origin.subList(start, end);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return origin.containsAll(collection);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return origin.removeAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return origin.retainAll(collection);
    }

    @Override
    public String toString() {
        return origin.toString();
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        if (removeRangeMethod == null) {
            try {
                removeRangeMethod = ArrayList.class.getDeclaredMethod("removeRange", int.class, int.class);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            removeRangeMethod.invoke(origin, fromIndex, toIndex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
