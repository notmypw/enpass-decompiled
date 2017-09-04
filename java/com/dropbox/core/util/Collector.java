package com.dropbox.core.util;

import java.util.ArrayList;

public abstract class Collector<E, L> {

    public static final class ArrayListCollector<E> extends Collector<E, ArrayList<E>> {
        private ArrayList<E> list = new ArrayList();

        public void add(E element) {
            if (this.list == null) {
                throw new IllegalStateException("already called finish()");
            }
            this.list.add(element);
        }

        public ArrayList<E> finish() {
            ArrayList<E> list = this.list;
            if (list == null) {
                throw new IllegalStateException("already called finish()");
            }
            this.list = null;
            return list;
        }
    }

    public static final class NullSkipper<E, L> extends Collector<E, L> {
        private final Collector<E, L> underlying;

        public NullSkipper(Collector<E, L> underlying) {
            this.underlying = underlying;
        }

        public static <E, L> Collector<E, L> mk(Collector<E, L> underlying) {
            return new NullSkipper(underlying);
        }

        public void add(E element) {
            if (element != null) {
                this.underlying.add(element);
            }
        }

        public L finish() {
            return this.underlying.finish();
        }
    }

    public abstract void add(E e);

    public abstract L finish();
}
