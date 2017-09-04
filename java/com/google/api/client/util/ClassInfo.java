package com.google.api.client.util;

import com.github.clans.fab.BuildConfig;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;
import java.util.WeakHashMap;

public final class ClassInfo {
    private static final Map<Class<?>, ClassInfo> CACHE = new WeakHashMap();
    private static final Map<Class<?>, ClassInfo> CACHE_IGNORE_CASE = new WeakHashMap();
    private final Class<?> clazz;
    private final boolean ignoreCase;
    private final IdentityHashMap<String, FieldInfo> nameToFieldInfoMap = new IdentityHashMap();
    final List<String> names;

    public static ClassInfo of(Class<?> underlyingClass) {
        return of(underlyingClass, false);
    }

    public static ClassInfo of(Class<?> underlyingClass, boolean ignoreCase) {
        if (underlyingClass == null) {
            return null;
        }
        ClassInfo classInfo;
        Map<Class<?>, ClassInfo> cache = ignoreCase ? CACHE_IGNORE_CASE : CACHE;
        synchronized (cache) {
            classInfo = (ClassInfo) cache.get(underlyingClass);
            if (classInfo == null) {
                classInfo = new ClassInfo(underlyingClass, ignoreCase);
                cache.put(underlyingClass, classInfo);
            }
        }
        return classInfo;
    }

    public Class<?> getUnderlyingClass() {
        return this.clazz;
    }

    public final boolean getIgnoreCase() {
        return this.ignoreCase;
    }

    public FieldInfo getFieldInfo(String name) {
        if (name != null) {
            if (this.ignoreCase) {
                name = name.toLowerCase();
            }
            name = name.intern();
        }
        return (FieldInfo) this.nameToFieldInfoMap.get(name);
    }

    public Field getField(String name) {
        FieldInfo fieldInfo = getFieldInfo(name);
        return fieldInfo == null ? null : fieldInfo.getField();
    }

    public boolean isEnum() {
        return this.clazz.isEnum();
    }

    public Collection<String> getNames() {
        return this.names;
    }

    private ClassInfo(Class<?> srcClass, boolean ignoreCase) {
        this.clazz = srcClass;
        this.ignoreCase = ignoreCase;
        boolean z = (ignoreCase && srcClass.isEnum()) ? false : true;
        Preconditions.checkArgument(z, "cannot ignore case on an enum: " + srcClass);
        TreeSet<String> nameSet = new TreeSet(new Comparator<String>() {
            public int compare(String s0, String s1) {
                if (s0 == s1) {
                    return 0;
                }
                if (s0 == null) {
                    return -1;
                }
                return s1 == null ? 1 : s0.compareTo(s1);
            }
        });
        for (Field field : srcClass.getDeclaredFields()) {
            FieldInfo fieldInfo = FieldInfo.of(field);
            if (fieldInfo != null) {
                String fieldName = fieldInfo.getName();
                if (ignoreCase) {
                    fieldName = fieldName.toLowerCase().intern();
                }
                FieldInfo conflictingFieldInfo = (FieldInfo) this.nameToFieldInfoMap.get(fieldName);
                z = conflictingFieldInfo == null;
                String str = "two fields have the same %sname <%s>: %s and %s";
                String[] strArr = new Object[4];
                strArr[0] = ignoreCase ? "case-insensitive " : BuildConfig.FLAVOR;
                strArr[1] = fieldName;
                strArr[2] = field;
                strArr[3] = conflictingFieldInfo == null ? null : conflictingFieldInfo.getField();
                Preconditions.checkArgument(z, str, strArr);
                this.nameToFieldInfoMap.put(fieldName, fieldInfo);
                nameSet.add(fieldName);
            }
        }
        Class<?> superClass = srcClass.getSuperclass();
        if (superClass != null) {
            ClassInfo superClassInfo = of(superClass, ignoreCase);
            nameSet.addAll(superClassInfo.names);
            for (Entry<String, FieldInfo> e : superClassInfo.nameToFieldInfoMap.entrySet()) {
                String name = (String) e.getKey();
                if (!this.nameToFieldInfoMap.containsKey(name)) {
                    this.nameToFieldInfoMap.put(name, e.getValue());
                }
            }
        }
        this.names = nameSet.isEmpty() ? Collections.emptyList() : Collections.unmodifiableList(new ArrayList(nameSet));
    }

    public Collection<FieldInfo> getFieldInfos() {
        return Collections.unmodifiableCollection(this.nameToFieldInfoMap.values());
    }
}
