package com.google.api.client.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public final class Lists {
    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList();
    }

    public static <E> ArrayList<E> newArrayListWithCapacity(int initialArraySize) {
        return new ArrayList(initialArraySize);
    }

    public static <E> ArrayList<E> newArrayList(Iterable<? extends E> elements) {
        return elements instanceof Collection ? new ArrayList(Collections2.cast(elements)) : newArrayList(elements.iterator());
    }

    public static <E> ArrayList<E> newArrayList(Iterator<? extends E> elements) {
        ArrayList<E> list = newArrayList();
        while (elements.hasNext()) {
            list.add(elements.next());
        }
        return list;
    }

    private Lists() {
    }
}
