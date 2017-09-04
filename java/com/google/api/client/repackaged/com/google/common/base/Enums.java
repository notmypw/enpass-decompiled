package com.google.api.client.repackaged.com.google.common.base;

import com.google.api.client.repackaged.com.google.common.annotations.Beta;
import com.google.api.client.repackaged.com.google.common.annotations.GwtCompatible;
import com.google.api.client.repackaged.com.google.common.annotations.GwtIncompatible;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
@Beta
public final class Enums {
    @GwtIncompatible("java.lang.ref.WeakReference")
    private static final Map<Class<? extends Enum<?>>, Map<String, WeakReference<? extends Enum<?>>>> enumConstantCache = new WeakHashMap();

    private static final class StringConverter<T extends Enum<T>> extends Converter<String, T> implements Serializable {
        private static final long serialVersionUID = 0;
        private final Class<T> enumClass;

        StringConverter(Class<T> enumClass) {
            this.enumClass = (Class) Preconditions.checkNotNull(enumClass);
        }

        protected T doForward(String value) {
            return Enum.valueOf(this.enumClass, value);
        }

        protected String doBackward(T enumValue) {
            return enumValue.name();
        }

        public boolean equals(@Nullable Object object) {
            if (!(object instanceof StringConverter)) {
                return false;
            }
            return this.enumClass.equals(((StringConverter) object).enumClass);
        }

        public int hashCode() {
            return this.enumClass.hashCode();
        }

        public String toString() {
            return "Enums.stringConverter(" + this.enumClass.getName() + ".class)";
        }
    }

    private static final class ValueOfFunction<T extends Enum<T>> implements Function<String, T>, Serializable {
        private static final long serialVersionUID = 0;
        private final Class<T> enumClass;

        private ValueOfFunction(Class<T> enumClass) {
            this.enumClass = (Class) Preconditions.checkNotNull(enumClass);
        }

        public T apply(String value) {
            try {
                return Enum.valueOf(this.enumClass, value);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }

        public boolean equals(@Nullable Object obj) {
            return (obj instanceof ValueOfFunction) && this.enumClass.equals(((ValueOfFunction) obj).enumClass);
        }

        public int hashCode() {
            return this.enumClass.hashCode();
        }

        public String toString() {
            return "Enums.valueOf(" + this.enumClass + ")";
        }
    }

    private Enums() {
    }

    @GwtIncompatible("reflection")
    public static Field getField(Enum<?> enumValue) {
        try {
            return enumValue.getDeclaringClass().getDeclaredField(enumValue.name());
        } catch (NoSuchFieldException impossible) {
            throw new AssertionError(impossible);
        }
    }

    @Deprecated
    public static <T extends Enum<T>> Function<String, T> valueOfFunction(Class<T> enumClass) {
        return new ValueOfFunction(enumClass);
    }

    public static <T extends Enum<T>> Optional<T> getIfPresent(Class<T> enumClass, String value) {
        Preconditions.checkNotNull(enumClass);
        Preconditions.checkNotNull(value);
        return Platform.getEnumIfPresent(enumClass, value);
    }

    @GwtIncompatible("java.lang.ref.WeakReference")
    private static <T extends Enum<T>> Map<String, WeakReference<? extends Enum<?>>> populateCache(Class<T> enumClass) {
        Map<String, WeakReference<? extends Enum<?>>> result = new HashMap();
        Iterator i$ = EnumSet.allOf(enumClass).iterator();
        while (i$.hasNext()) {
            Enum enumInstance = (Enum) i$.next();
            result.put(enumInstance.name(), new WeakReference(enumInstance));
        }
        enumConstantCache.put(enumClass, result);
        return result;
    }

    @GwtIncompatible("java.lang.ref.WeakReference")
    static <T extends Enum<T>> Map<String, WeakReference<? extends Enum<?>>> getEnumConstants(Class<T> enumClass) {
        Map<String, WeakReference<? extends Enum<?>>> constants;
        synchronized (enumConstantCache) {
            constants = (Map) enumConstantCache.get(enumClass);
            if (constants == null) {
                constants = populateCache(enumClass);
            }
        }
        return constants;
    }

    public static <T extends Enum<T>> Converter<String, T> stringConverter(Class<T> enumClass) {
        return new StringConverter(enumClass);
    }
}
