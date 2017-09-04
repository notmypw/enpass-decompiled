package com.box.androidsdk.content.models;

import com.eclipsesource.json.JsonArray;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class BoxArray<E extends BoxJsonObject> implements Collection<E> {
    protected final Collection<E> collection = new ArrayList();

    public String toJson() {
        JsonArray array = new JsonArray();
        for (int i = 0; i < size(); i++) {
            array.add(get(i).toJsonObject());
        }
        return array.toString();
    }

    public boolean add(E e) {
        return this.collection.add(e);
    }

    public boolean addAll(Collection<? extends E> c) {
        return this.collection.addAll(c);
    }

    public void clear() {
        this.collection.clear();
    }

    public boolean contains(Object o) {
        return this.collection.contains(o);
    }

    public boolean containsAll(Collection<?> c) {
        return this.collection.containsAll(c);
    }

    public boolean equals(Object o) {
        return this.collection.equals(o);
    }

    public int hashCode() {
        return this.collection.hashCode();
    }

    public boolean isEmpty() {
        return this.collection.isEmpty();
    }

    public Iterator<E> iterator() {
        return this.collection.iterator();
    }

    public boolean remove(Object o) {
        return this.collection.remove(o);
    }

    public boolean removeAll(Collection<?> c) {
        return this.collection.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return this.collection.retainAll(c);
    }

    public int size() {
        return this.collection.size();
    }

    public Object[] toArray() {
        return this.collection.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return this.collection.toArray(a);
    }

    public E get(int index) {
        if (this.collection instanceof List) {
            return (BoxJsonObject) ((List) this.collection).get(index);
        }
        if (index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Iterator<E> iterator = iterator();
        while (iterator.hasNext()) {
            if (index == 0) {
                return (BoxJsonObject) iterator.next();
            }
            iterator.next();
        }
        throw new IndexOutOfBoundsException();
    }
}
