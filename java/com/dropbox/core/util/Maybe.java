package com.dropbox.core.util;

public abstract class Maybe<T> {
    private static final Maybe<Object> Nothing = new Nothing();

    private static final class Just<T> extends Maybe<T> {
        private final T value;

        private Just(T value) {
            super();
            this.value = value;
        }

        public boolean isNothing() {
            return false;
        }

        public boolean isJust() {
            return true;
        }

        public T getJust() {
            return this.value;
        }

        public T get(T t) {
            return this.value;
        }

        public String toString() {
            return "Just(" + this.value + ")";
        }

        public int hashCode() {
            return LangUtil.nullableHashCode(this.value) + 1;
        }

        public boolean equals(Maybe<T> other) {
            if (other instanceof Just) {
                return LangUtil.nullableEquals(this.value, ((Just) other).value);
            } else if (other instanceof Nothing) {
                return false;
            } else {
                throw LangUtil.badType(other);
            }
        }
    }

    private static final class Nothing<T> extends Maybe<T> {
        private Nothing() {
            super();
        }

        public boolean isNothing() {
            return true;
        }

        public boolean isJust() {
            return false;
        }

        public T getJust() {
            throw new IllegalStateException("can't call getJust() on a Nothing");
        }

        public T get(T def) {
            return def;
        }

        public String toString() {
            return "Nothing";
        }

        public int hashCode() {
            return 0;
        }

        public boolean equals(Maybe<T> other) {
            return other == this;
        }
    }

    public abstract boolean equals(Maybe<T> maybe);

    public abstract T get(T t);

    public abstract T getJust();

    public abstract int hashCode();

    public abstract boolean isJust();

    public abstract boolean isNothing();

    public abstract String toString();

    private Maybe() {
    }

    public static <T> Maybe<T> Just(T value) {
        return new Just(value);
    }

    public static <T> Maybe<T> Nothing() {
        return Nothing;
    }
}
