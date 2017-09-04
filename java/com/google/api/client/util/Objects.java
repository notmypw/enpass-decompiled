package com.google.api.client.util;

import com.github.clans.fab.BuildConfig;

public final class Objects {

    public static final class ToStringHelper {
        private final String className;
        private ValueHolder holderHead = new ValueHolder();
        private ValueHolder holderTail = this.holderHead;
        private boolean omitNullValues;

        private static final class ValueHolder {
            String name;
            ValueHolder next;
            Object value;

            private ValueHolder() {
            }
        }

        ToStringHelper(String className) {
            this.className = className;
        }

        public ToStringHelper omitNullValues() {
            this.omitNullValues = true;
            return this;
        }

        public ToStringHelper add(String name, Object value) {
            return addHolder(name, value);
        }

        public String toString() {
            boolean omitNullValuesSnapshot = this.omitNullValues;
            String nextSeparator = BuildConfig.FLAVOR;
            StringBuilder builder = new StringBuilder(32).append(this.className).append('{');
            ValueHolder valueHolder = this.holderHead.next;
            while (valueHolder != null) {
                if (!omitNullValuesSnapshot || valueHolder.value != null) {
                    builder.append(nextSeparator);
                    nextSeparator = ", ";
                    if (valueHolder.name != null) {
                        builder.append(valueHolder.name).append('=');
                    }
                    builder.append(valueHolder.value);
                }
                valueHolder = valueHolder.next;
            }
            return builder.append('}').toString();
        }

        private ValueHolder addHolder() {
            ValueHolder valueHolder = new ValueHolder();
            this.holderTail.next = valueHolder;
            this.holderTail = valueHolder;
            return valueHolder;
        }

        private ToStringHelper addHolder(String name, Object value) {
            ValueHolder valueHolder = addHolder();
            valueHolder.value = value;
            valueHolder.name = (String) Preconditions.checkNotNull(name);
            return this;
        }
    }

    public static boolean equal(Object a, Object b) {
        return com.google.api.client.repackaged.com.google.common.base.Objects.equal(a, b);
    }

    public static ToStringHelper toStringHelper(Object self) {
        return new ToStringHelper(self.getClass().getSimpleName());
    }

    private Objects() {
    }
}
