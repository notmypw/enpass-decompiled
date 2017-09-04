package com.google.api.client.json;

import com.google.api.client.json.JsonPolymorphicTypeMap.TypeDef;
import com.google.api.client.util.Beta;
import com.google.api.client.util.ClassInfo;
import com.google.api.client.util.Data;
import com.google.api.client.util.FieldInfo;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Sets;
import com.google.api.client.util.Types;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import net.sqlcipher.database.SQLiteDatabase;

public abstract class JsonParser {
    private static WeakHashMap<Class<?>, Field> cachedTypemapFields = new WeakHashMap();
    private static final Lock lock = new ReentrantLock();

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$api$client$json$JsonToken = new int[JsonToken.values().length];

        static {
            try {
                $SwitchMap$com$google$api$client$json$JsonToken[JsonToken.START_OBJECT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$api$client$json$JsonToken[JsonToken.START_ARRAY.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$google$api$client$json$JsonToken[JsonToken.END_ARRAY.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$google$api$client$json$JsonToken[JsonToken.FIELD_NAME.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$google$api$client$json$JsonToken[JsonToken.END_OBJECT.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$google$api$client$json$JsonToken[JsonToken.VALUE_TRUE.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$google$api$client$json$JsonToken[JsonToken.VALUE_FALSE.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$google$api$client$json$JsonToken[JsonToken.VALUE_NUMBER_FLOAT.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$google$api$client$json$JsonToken[JsonToken.VALUE_NUMBER_INT.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$google$api$client$json$JsonToken[JsonToken.VALUE_STRING.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$google$api$client$json$JsonToken[JsonToken.VALUE_NULL.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
        }
    }

    public abstract void close() throws IOException;

    public abstract BigInteger getBigIntegerValue() throws IOException;

    public abstract byte getByteValue() throws IOException;

    public abstract String getCurrentName() throws IOException;

    public abstract JsonToken getCurrentToken();

    public abstract BigDecimal getDecimalValue() throws IOException;

    public abstract double getDoubleValue() throws IOException;

    public abstract JsonFactory getFactory();

    public abstract float getFloatValue() throws IOException;

    public abstract int getIntValue() throws IOException;

    public abstract long getLongValue() throws IOException;

    public abstract short getShortValue() throws IOException;

    public abstract String getText() throws IOException;

    public abstract JsonToken nextToken() throws IOException;

    public abstract JsonParser skipChildren() throws IOException;

    public final <T> T parseAndClose(Class<T> destinationClass) throws IOException {
        return parseAndClose((Class) destinationClass, null);
    }

    @Beta
    public final <T> T parseAndClose(Class<T> destinationClass, CustomizeJsonParser customizeParser) throws IOException {
        try {
            T parse = parse((Class) destinationClass, customizeParser);
            return parse;
        } finally {
            close();
        }
    }

    public final void skipToKey(String keyToFind) throws IOException {
        skipToKey(Collections.singleton(keyToFind));
    }

    public final String skipToKey(Set<String> keysToFind) throws IOException {
        JsonToken curToken = startParsingObjectOrArray();
        while (curToken == JsonToken.FIELD_NAME) {
            String key = getText();
            nextToken();
            if (keysToFind.contains(key)) {
                return key;
            }
            skipChildren();
            curToken = nextToken();
        }
        return null;
    }

    private JsonToken startParsing() throws IOException {
        JsonToken currentToken = getCurrentToken();
        if (currentToken == null) {
            currentToken = nextToken();
        }
        Preconditions.checkArgument(currentToken != null, "no JSON input found");
        return currentToken;
    }

    private JsonToken startParsingObjectOrArray() throws IOException {
        JsonToken currentToken = startParsing();
        switch (AnonymousClass1.$SwitchMap$com$google$api$client$json$JsonToken[currentToken.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                currentToken = nextToken();
                boolean z = currentToken == JsonToken.FIELD_NAME || currentToken == JsonToken.END_OBJECT;
                Preconditions.checkArgument(z, currentToken);
                return currentToken;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                return nextToken();
            default:
                return currentToken;
        }
    }

    public final void parseAndClose(Object destination) throws IOException {
        parseAndClose(destination, null);
    }

    @Beta
    public final void parseAndClose(Object destination, CustomizeJsonParser customizeParser) throws IOException {
        try {
            parse(destination, customizeParser);
        } finally {
            close();
        }
    }

    public final <T> T parse(Class<T> destinationClass) throws IOException {
        return parse((Class) destinationClass, null);
    }

    @Beta
    public final <T> T parse(Class<T> destinationClass, CustomizeJsonParser customizeParser) throws IOException {
        return parse((Type) destinationClass, false, customizeParser);
    }

    public Object parse(Type dataType, boolean close) throws IOException {
        return parse(dataType, close, null);
    }

    @Beta
    public Object parse(Type dataType, boolean close, CustomizeJsonParser customizeParser) throws IOException {
        try {
            if (!Void.class.equals(dataType)) {
                startParsing();
            }
            Object parseValue = parseValue(null, dataType, new ArrayList(), null, customizeParser, true);
            return parseValue;
        } finally {
            if (close) {
                close();
            }
        }
    }

    public final void parse(Object destination) throws IOException {
        parse(destination, null);
    }

    @Beta
    public final void parse(Object destination, CustomizeJsonParser customizeParser) throws IOException {
        ArrayList context = new ArrayList();
        context.add(destination.getClass());
        parse(context, destination, customizeParser);
    }

    private void parse(ArrayList<Type> context, Object destination, CustomizeJsonParser customizeParser) throws IOException {
        if (destination instanceof GenericJson) {
            ((GenericJson) destination).setFactory(getFactory());
        }
        JsonToken curToken = startParsingObjectOrArray();
        Class<?> destinationClass = destination.getClass();
        ClassInfo classInfo = ClassInfo.of(destinationClass);
        boolean isGenericData = GenericData.class.isAssignableFrom(destinationClass);
        if (isGenericData || !Map.class.isAssignableFrom(destinationClass)) {
            while (curToken == JsonToken.FIELD_NAME) {
                String key = getText();
                nextToken();
                if (customizeParser == null || !customizeParser.stopAt(destination, key)) {
                    FieldInfo fieldInfo = classInfo.getFieldInfo(key);
                    if (fieldInfo != null) {
                        if (!fieldInfo.isFinal() || fieldInfo.isPrimitive()) {
                            Field field = fieldInfo.getField();
                            int contextSize = context.size();
                            context.add(field.getGenericType());
                            Object fieldValue = parseValue(field, fieldInfo.getGenericType(), context, destination, customizeParser, true);
                            context.remove(contextSize);
                            fieldInfo.setValue(destination, fieldValue);
                        } else {
                            throw new IllegalArgumentException("final array/object fields are not supported");
                        }
                    } else if (isGenericData) {
                        ((GenericData) destination).set(key, parseValue(null, null, context, destination, customizeParser, true));
                    } else {
                        if (customizeParser != null) {
                            customizeParser.handleUnrecognizedKey(destination, key);
                        }
                        skipChildren();
                    }
                    curToken = nextToken();
                } else {
                    return;
                }
            }
            return;
        }
        parseMap(null, (Map) destination, Types.getMapValueParameter(destinationClass), context, customizeParser);
    }

    public final <T> Collection<T> parseArrayAndClose(Class<?> destinationCollectionClass, Class<T> destinationItemClass) throws IOException {
        return parseArrayAndClose((Class) destinationCollectionClass, (Class) destinationItemClass, null);
    }

    @Beta
    public final <T> Collection<T> parseArrayAndClose(Class<?> destinationCollectionClass, Class<T> destinationItemClass, CustomizeJsonParser customizeParser) throws IOException {
        try {
            Collection<T> parseArray = parseArray((Class) destinationCollectionClass, (Class) destinationItemClass, customizeParser);
            return parseArray;
        } finally {
            close();
        }
    }

    public final <T> void parseArrayAndClose(Collection<? super T> destinationCollection, Class<T> destinationItemClass) throws IOException {
        parseArrayAndClose((Collection) destinationCollection, (Class) destinationItemClass, null);
    }

    @Beta
    public final <T> void parseArrayAndClose(Collection<? super T> destinationCollection, Class<T> destinationItemClass, CustomizeJsonParser customizeParser) throws IOException {
        try {
            parseArray((Collection) destinationCollection, (Class) destinationItemClass, customizeParser);
        } finally {
            close();
        }
    }

    public final <T> Collection<T> parseArray(Class<?> destinationCollectionClass, Class<T> destinationItemClass) throws IOException {
        return parseArray((Class) destinationCollectionClass, (Class) destinationItemClass, null);
    }

    @Beta
    public final <T> Collection<T> parseArray(Class<?> destinationCollectionClass, Class<T> destinationItemClass, CustomizeJsonParser customizeParser) throws IOException {
        Collection destinationCollection = Data.newCollectionInstance(destinationCollectionClass);
        parseArray(destinationCollection, (Class) destinationItemClass, customizeParser);
        return destinationCollection;
    }

    public final <T> void parseArray(Collection<? super T> destinationCollection, Class<T> destinationItemClass) throws IOException {
        parseArray((Collection) destinationCollection, (Class) destinationItemClass, null);
    }

    @Beta
    public final <T> void parseArray(Collection<? super T> destinationCollection, Class<T> destinationItemClass, CustomizeJsonParser customizeParser) throws IOException {
        parseArray(null, destinationCollection, destinationItemClass, new ArrayList(), customizeParser);
    }

    private <T> void parseArray(Field fieldContext, Collection<T> destinationCollection, Type destinationItemType, ArrayList<Type> context, CustomizeJsonParser customizeParser) throws IOException {
        JsonToken curToken = startParsingObjectOrArray();
        while (curToken != JsonToken.END_ARRAY) {
            destinationCollection.add(parseValue(fieldContext, destinationItemType, context, destinationCollection, customizeParser, true));
            curToken = nextToken();
        }
    }

    private void parseMap(Field fieldContext, Map<String, Object> destinationMap, Type valueType, ArrayList<Type> context, CustomizeJsonParser customizeParser) throws IOException {
        JsonToken curToken = startParsingObjectOrArray();
        while (curToken == JsonToken.FIELD_NAME) {
            String key = getText();
            nextToken();
            if (customizeParser == null || !customizeParser.stopAt(destinationMap, key)) {
                destinationMap.put(key, parseValue(fieldContext, valueType, context, destinationMap, customizeParser, true));
                curToken = nextToken();
            } else {
                return;
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.lang.Object parseValue(java.lang.reflect.Field r39, java.lang.reflect.Type r40, java.util.ArrayList<java.lang.reflect.Type> r41, java.lang.Object r42, com.google.api.client.json.CustomizeJsonParser r43, boolean r44) throws java.io.IOException {
        /*
        r38 = this;
        r0 = r41;
        r1 = r40;
        r40 = com.google.api.client.util.Data.resolveWildcardTypeOrTypeVariable(r0, r1);
        r0 = r40;
        r4 = r0 instanceof java.lang.Class;
        if (r4 == 0) goto L_0x002d;
    L_0x000e:
        r4 = r40;
        r4 = (java.lang.Class) r4;
        r37 = r4;
    L_0x0014:
        r0 = r40;
        r4 = r0 instanceof java.lang.reflect.ParameterizedType;
        if (r4 == 0) goto L_0x0022;
    L_0x001a:
        r4 = r40;
        r4 = (java.lang.reflect.ParameterizedType) r4;
        r37 = com.google.api.client.util.Types.getRawClass(r4);
    L_0x0022:
        r4 = java.lang.Void.class;
        r0 = r37;
        if (r0 != r4) goto L_0x0030;
    L_0x0028:
        r38.skipChildren();
        r4 = 0;
    L_0x002c:
        return r4;
    L_0x002d:
        r37 = 0;
        goto L_0x0014;
    L_0x0030:
        r31 = r38.getCurrentToken();
        r4 = com.google.api.client.json.JsonParser.AnonymousClass1.$SwitchMap$com$google$api$client$json$JsonToken;	 Catch:{ IllegalArgumentException -> 0x005e }
        r5 = r38.getCurrentToken();	 Catch:{ IllegalArgumentException -> 0x005e }
        r5 = r5.ordinal();	 Catch:{ IllegalArgumentException -> 0x005e }
        r4 = r4[r5];	 Catch:{ IllegalArgumentException -> 0x005e }
        switch(r4) {
            case 1: goto L_0x010c;
            case 2: goto L_0x009b;
            case 3: goto L_0x009b;
            case 4: goto L_0x010c;
            case 5: goto L_0x010c;
            case 6: goto L_0x0242;
            case 7: goto L_0x0242;
            case 8: goto L_0x0272;
            case 9: goto L_0x0272;
            case 10: goto L_0x0343;
            case 11: goto L_0x03b3;
            default: goto L_0x0043;
        };	 Catch:{ IllegalArgumentException -> 0x005e }
    L_0x0043:
        r4 = new java.lang.IllegalArgumentException;	 Catch:{ IllegalArgumentException -> 0x005e }
        r5 = new java.lang.StringBuilder;	 Catch:{ IllegalArgumentException -> 0x005e }
        r5.<init>();	 Catch:{ IllegalArgumentException -> 0x005e }
        r8 = "unexpected JSON node type: ";
        r5 = r5.append(r8);	 Catch:{ IllegalArgumentException -> 0x005e }
        r0 = r31;
        r5 = r5.append(r0);	 Catch:{ IllegalArgumentException -> 0x005e }
        r5 = r5.toString();	 Catch:{ IllegalArgumentException -> 0x005e }
        r4.<init>(r5);	 Catch:{ IllegalArgumentException -> 0x005e }
        throw r4;	 Catch:{ IllegalArgumentException -> 0x005e }
    L_0x005e:
        r23 = move-exception;
        r21 = new java.lang.StringBuilder;
        r21.<init>();
        r22 = r38.getCurrentName();
        if (r22 == 0) goto L_0x0077;
    L_0x006a:
        r4 = "key ";
        r0 = r21;
        r4 = r0.append(r4);
        r0 = r22;
        r4.append(r0);
    L_0x0077:
        if (r39 == 0) goto L_0x008f;
    L_0x0079:
        if (r22 == 0) goto L_0x0082;
    L_0x007b:
        r4 = ", ";
        r0 = r21;
        r0.append(r4);
    L_0x0082:
        r4 = "field ";
        r0 = r21;
        r4 = r0.append(r4);
        r0 = r39;
        r4.append(r0);
    L_0x008f:
        r4 = new java.lang.IllegalArgumentException;
        r5 = r21.toString();
        r0 = r23;
        r4.<init>(r5, r0);
        throw r4;
    L_0x009b:
        r26 = com.google.api.client.util.Types.isArray(r40);	 Catch:{ IllegalArgumentException -> 0x005e }
        if (r40 == 0) goto L_0x00af;
    L_0x00a1:
        if (r26 != 0) goto L_0x00af;
    L_0x00a3:
        if (r37 == 0) goto L_0x00f6;
    L_0x00a5:
        r4 = java.util.Collection.class;
        r0 = r37;
        r4 = com.google.api.client.util.Types.isAssignableToOrFrom(r0, r4);	 Catch:{ IllegalArgumentException -> 0x005e }
        if (r4 == 0) goto L_0x00f6;
    L_0x00af:
        r4 = 1;
    L_0x00b0:
        r5 = "expected collection or array type but got %s";
        r8 = 1;
        r8 = new java.lang.Object[r8];	 Catch:{ IllegalArgumentException -> 0x005e }
        r9 = 0;
        r8[r9] = r40;	 Catch:{ IllegalArgumentException -> 0x005e }
        com.google.api.client.util.Preconditions.checkArgument(r4, r5, r8);	 Catch:{ IllegalArgumentException -> 0x005e }
        r6 = 0;
        if (r43 == 0) goto L_0x00ca;
    L_0x00be:
        if (r39 == 0) goto L_0x00ca;
    L_0x00c0:
        r0 = r43;
        r1 = r42;
        r2 = r39;
        r6 = r0.newInstanceForArray(r1, r2);	 Catch:{ IllegalArgumentException -> 0x005e }
    L_0x00ca:
        if (r6 != 0) goto L_0x00d0;
    L_0x00cc:
        r6 = com.google.api.client.util.Data.newCollectionInstance(r40);	 Catch:{ IllegalArgumentException -> 0x005e }
    L_0x00d0:
        r7 = 0;
        if (r26 == 0) goto L_0x00f8;
    L_0x00d3:
        r7 = com.google.api.client.util.Types.getArrayComponentType(r40);	 Catch:{ IllegalArgumentException -> 0x005e }
    L_0x00d7:
        r0 = r41;
        r7 = com.google.api.client.util.Data.resolveWildcardTypeOrTypeVariable(r0, r7);	 Catch:{ IllegalArgumentException -> 0x005e }
        r4 = r38;
        r5 = r39;
        r8 = r41;
        r9 = r43;
        r4.parseArray(r5, r6, r7, r8, r9);	 Catch:{ IllegalArgumentException -> 0x005e }
        if (r26 == 0) goto L_0x0109;
    L_0x00ea:
        r0 = r41;
        r4 = com.google.api.client.util.Types.getRawArrayComponentType(r0, r7);	 Catch:{ IllegalArgumentException -> 0x005e }
        r4 = com.google.api.client.util.Types.toArray(r6, r4);	 Catch:{ IllegalArgumentException -> 0x005e }
        goto L_0x002c;
    L_0x00f6:
        r4 = 0;
        goto L_0x00b0;
    L_0x00f8:
        if (r37 == 0) goto L_0x00d7;
    L_0x00fa:
        r4 = java.lang.Iterable.class;
        r0 = r37;
        r4 = r4.isAssignableFrom(r0);	 Catch:{ IllegalArgumentException -> 0x005e }
        if (r4 == 0) goto L_0x00d7;
    L_0x0104:
        r7 = com.google.api.client.util.Types.getIterableParameter(r40);	 Catch:{ IllegalArgumentException -> 0x005e }
        goto L_0x00d7;
    L_0x0109:
        r4 = r6;
        goto L_0x002c;
    L_0x010c:
        r4 = com.google.api.client.util.Types.isArray(r40);	 Catch:{ IllegalArgumentException -> 0x005e }
        if (r4 != 0) goto L_0x0185;
    L_0x0112:
        r4 = 1;
    L_0x0113:
        r5 = "expected object or map type but got %s";
        r8 = 1;
        r8 = new java.lang.Object[r8];	 Catch:{ IllegalArgumentException -> 0x005e }
        r9 = 0;
        r8[r9] = r40;	 Catch:{ IllegalArgumentException -> 0x005e }
        com.google.api.client.util.Preconditions.checkArgument(r4, r5, r8);	 Catch:{ IllegalArgumentException -> 0x005e }
        if (r44 == 0) goto L_0x0187;
    L_0x0120:
        r36 = getCachedTypemapFieldFor(r37);	 Catch:{ IllegalArgumentException -> 0x005e }
    L_0x0124:
        r29 = 0;
        if (r37 == 0) goto L_0x0134;
    L_0x0128:
        if (r43 == 0) goto L_0x0134;
    L_0x012a:
        r0 = r43;
        r1 = r42;
        r2 = r37;
        r29 = r0.newInstanceForObject(r1, r2);	 Catch:{ IllegalArgumentException -> 0x005e }
    L_0x0134:
        if (r37 == 0) goto L_0x018a;
    L_0x0136:
        r4 = java.util.Map.class;
        r0 = r37;
        r4 = com.google.api.client.util.Types.isAssignableToOrFrom(r0, r4);	 Catch:{ IllegalArgumentException -> 0x005e }
        if (r4 == 0) goto L_0x018a;
    L_0x0140:
        r27 = 1;
    L_0x0142:
        if (r36 == 0) goto L_0x018d;
    L_0x0144:
        r29 = new com.google.api.client.json.GenericJson;	 Catch:{ IllegalArgumentException -> 0x005e }
        r29.<init>();	 Catch:{ IllegalArgumentException -> 0x005e }
        r4 = r29;
    L_0x014b:
        r20 = r41.size();	 Catch:{ IllegalArgumentException -> 0x005e }
        if (r40 == 0) goto L_0x0158;
    L_0x0151:
        r0 = r41;
        r1 = r40;
        r0.add(r1);	 Catch:{ IllegalArgumentException -> 0x005e }
    L_0x0158:
        if (r27 == 0) goto L_0x01a3;
    L_0x015a:
        r5 = com.google.api.client.util.GenericData.class;
        r0 = r37;
        r5 = r5.isAssignableFrom(r0);	 Catch:{ IllegalArgumentException -> 0x005e }
        if (r5 != 0) goto L_0x01a3;
    L_0x0164:
        r5 = java.util.Map.class;
        r0 = r37;
        r5 = r5.isAssignableFrom(r0);	 Catch:{ IllegalArgumentException -> 0x005e }
        if (r5 == 0) goto L_0x01a1;
    L_0x016e:
        r11 = com.google.api.client.util.Types.getMapValueParameter(r40);	 Catch:{ IllegalArgumentException -> 0x005e }
    L_0x0172:
        if (r11 == 0) goto L_0x01a3;
    L_0x0174:
        r0 = r4;
        r0 = (java.util.Map) r0;	 Catch:{ IllegalArgumentException -> 0x005e }
        r10 = r0;
        r8 = r38;
        r9 = r39;
        r12 = r41;
        r13 = r43;
        r8.parseMap(r9, r10, r11, r12, r13);	 Catch:{ IllegalArgumentException -> 0x005e }
        goto L_0x002c;
    L_0x0185:
        r4 = 0;
        goto L_0x0113;
    L_0x0187:
        r36 = 0;
        goto L_0x0124;
    L_0x018a:
        r27 = 0;
        goto L_0x0142;
    L_0x018d:
        if (r29 != 0) goto L_0x040b;
    L_0x018f:
        if (r27 != 0) goto L_0x0193;
    L_0x0191:
        if (r37 != 0) goto L_0x019a;
    L_0x0193:
        r29 = com.google.api.client.util.Data.newMapInstance(r37);	 Catch:{ IllegalArgumentException -> 0x005e }
        r4 = r29;
        goto L_0x014b;
    L_0x019a:
        r29 = com.google.api.client.util.Types.newInstance(r37);	 Catch:{ IllegalArgumentException -> 0x005e }
        r4 = r29;
        goto L_0x014b;
    L_0x01a1:
        r11 = 0;
        goto L_0x0172;
    L_0x01a3:
        r0 = r38;
        r1 = r41;
        r2 = r43;
        r0.parse(r1, r4, r2);	 Catch:{ IllegalArgumentException -> 0x005e }
        if (r40 == 0) goto L_0x01b5;
    L_0x01ae:
        r0 = r41;
        r1 = r20;
        r0.remove(r1);	 Catch:{ IllegalArgumentException -> 0x005e }
    L_0x01b5:
        if (r36 == 0) goto L_0x002c;
    L_0x01b7:
        r0 = r4;
        r0 = (com.google.api.client.json.GenericJson) r0;	 Catch:{ IllegalArgumentException -> 0x005e }
        r5 = r0;
        r8 = r36.getName();	 Catch:{ IllegalArgumentException -> 0x005e }
        r35 = r5.get(r8);	 Catch:{ IllegalArgumentException -> 0x005e }
        if (r35 == 0) goto L_0x023b;
    L_0x01c5:
        r5 = 1;
    L_0x01c6:
        r8 = "No value specified for @JsonPolymorphicTypeMap field";
        com.google.api.client.util.Preconditions.checkArgument(r5, r8);	 Catch:{ IllegalArgumentException -> 0x005e }
        r34 = r35.toString();	 Catch:{ IllegalArgumentException -> 0x005e }
        r5 = com.google.api.client.json.JsonPolymorphicTypeMap.class;
        r0 = r36;
        r33 = r0.getAnnotation(r5);	 Catch:{ IllegalArgumentException -> 0x005e }
        r33 = (com.google.api.client.json.JsonPolymorphicTypeMap) r33;	 Catch:{ IllegalArgumentException -> 0x005e }
        r14 = 0;
        r19 = r33.typeDefinitions();	 Catch:{ IllegalArgumentException -> 0x005e }
        r0 = r19;
        r0 = r0.length;	 Catch:{ IllegalArgumentException -> 0x005e }
        r28 = r0;
        r25 = 0;
    L_0x01e5:
        r0 = r25;
        r1 = r28;
        if (r0 >= r1) goto L_0x01fd;
    L_0x01eb:
        r32 = r19[r25];	 Catch:{ IllegalArgumentException -> 0x005e }
        r5 = r32.key();	 Catch:{ IllegalArgumentException -> 0x005e }
        r0 = r34;
        r5 = r5.equals(r0);	 Catch:{ IllegalArgumentException -> 0x005e }
        if (r5 == 0) goto L_0x023d;
    L_0x01f9:
        r14 = r32.ref();	 Catch:{ IllegalArgumentException -> 0x005e }
    L_0x01fd:
        if (r14 == 0) goto L_0x0240;
    L_0x01ff:
        r5 = 1;
    L_0x0200:
        r8 = new java.lang.StringBuilder;	 Catch:{ IllegalArgumentException -> 0x005e }
        r8.<init>();	 Catch:{ IllegalArgumentException -> 0x005e }
        r9 = "No TypeDef annotation found with key: ";
        r8 = r8.append(r9);	 Catch:{ IllegalArgumentException -> 0x005e }
        r0 = r34;
        r8 = r8.append(r0);	 Catch:{ IllegalArgumentException -> 0x005e }
        r8 = r8.toString();	 Catch:{ IllegalArgumentException -> 0x005e }
        com.google.api.client.util.Preconditions.checkArgument(r5, r8);	 Catch:{ IllegalArgumentException -> 0x005e }
        r24 = r38.getFactory();	 Catch:{ IllegalArgumentException -> 0x005e }
        r0 = r24;
        r4 = r0.toString(r4);	 Catch:{ IllegalArgumentException -> 0x005e }
        r0 = r24;
        r12 = r0.createJsonParser(r4);	 Catch:{ IllegalArgumentException -> 0x005e }
        r12.startParsing();	 Catch:{ IllegalArgumentException -> 0x005e }
        r16 = 0;
        r17 = 0;
        r18 = 0;
        r13 = r39;
        r15 = r41;
        r4 = r12.parseValue(r13, r14, r15, r16, r17, r18);	 Catch:{ IllegalArgumentException -> 0x005e }
        goto L_0x002c;
    L_0x023b:
        r5 = 0;
        goto L_0x01c6;
    L_0x023d:
        r25 = r25 + 1;
        goto L_0x01e5;
    L_0x0240:
        r5 = 0;
        goto L_0x0200;
    L_0x0242:
        if (r40 == 0) goto L_0x0256;
    L_0x0244:
        r4 = java.lang.Boolean.TYPE;	 Catch:{ IllegalArgumentException -> 0x005e }
        r0 = r37;
        if (r0 == r4) goto L_0x0256;
    L_0x024a:
        if (r37 == 0) goto L_0x026c;
    L_0x024c:
        r4 = java.lang.Boolean.class;
        r0 = r37;
        r4 = r0.isAssignableFrom(r4);	 Catch:{ IllegalArgumentException -> 0x005e }
        if (r4 == 0) goto L_0x026c;
    L_0x0256:
        r4 = 1;
    L_0x0257:
        r5 = "expected type Boolean or boolean but got %s";
        r8 = 1;
        r8 = new java.lang.Object[r8];	 Catch:{ IllegalArgumentException -> 0x005e }
        r9 = 0;
        r8[r9] = r40;	 Catch:{ IllegalArgumentException -> 0x005e }
        com.google.api.client.util.Preconditions.checkArgument(r4, r5, r8);	 Catch:{ IllegalArgumentException -> 0x005e }
        r4 = com.google.api.client.json.JsonToken.VALUE_TRUE;	 Catch:{ IllegalArgumentException -> 0x005e }
        r0 = r31;
        if (r0 != r4) goto L_0x026e;
    L_0x0268:
        r4 = java.lang.Boolean.TRUE;	 Catch:{ IllegalArgumentException -> 0x005e }
        goto L_0x002c;
    L_0x026c:
        r4 = 0;
        goto L_0x0257;
    L_0x026e:
        r4 = java.lang.Boolean.FALSE;	 Catch:{ IllegalArgumentException -> 0x005e }
        goto L_0x002c;
    L_0x0272:
        if (r39 == 0) goto L_0x027e;
    L_0x0274:
        r4 = com.google.api.client.json.JsonString.class;
        r0 = r39;
        r4 = r0.getAnnotation(r4);	 Catch:{ IllegalArgumentException -> 0x005e }
        if (r4 != 0) goto L_0x0296;
    L_0x027e:
        r4 = 1;
    L_0x027f:
        r5 = "number type formatted as a JSON number cannot use @JsonString annotation";
        com.google.api.client.util.Preconditions.checkArgument(r4, r5);	 Catch:{ IllegalArgumentException -> 0x005e }
        if (r37 == 0) goto L_0x0290;
    L_0x0286:
        r4 = java.math.BigDecimal.class;
        r0 = r37;
        r4 = r0.isAssignableFrom(r4);	 Catch:{ IllegalArgumentException -> 0x005e }
        if (r4 == 0) goto L_0x0298;
    L_0x0290:
        r4 = r38.getDecimalValue();	 Catch:{ IllegalArgumentException -> 0x005e }
        goto L_0x002c;
    L_0x0296:
        r4 = 0;
        goto L_0x027f;
    L_0x0298:
        r4 = java.math.BigInteger.class;
        r0 = r37;
        if (r0 != r4) goto L_0x02a4;
    L_0x029e:
        r4 = r38.getBigIntegerValue();	 Catch:{ IllegalArgumentException -> 0x005e }
        goto L_0x002c;
    L_0x02a4:
        r4 = java.lang.Double.class;
        r0 = r37;
        if (r0 == r4) goto L_0x02b0;
    L_0x02aa:
        r4 = java.lang.Double.TYPE;	 Catch:{ IllegalArgumentException -> 0x005e }
        r0 = r37;
        if (r0 != r4) goto L_0x02ba;
    L_0x02b0:
        r4 = r38.getDoubleValue();	 Catch:{ IllegalArgumentException -> 0x005e }
        r4 = java.lang.Double.valueOf(r4);	 Catch:{ IllegalArgumentException -> 0x005e }
        goto L_0x002c;
    L_0x02ba:
        r4 = java.lang.Long.class;
        r0 = r37;
        if (r0 == r4) goto L_0x02c6;
    L_0x02c0:
        r4 = java.lang.Long.TYPE;	 Catch:{ IllegalArgumentException -> 0x005e }
        r0 = r37;
        if (r0 != r4) goto L_0x02d0;
    L_0x02c6:
        r4 = r38.getLongValue();	 Catch:{ IllegalArgumentException -> 0x005e }
        r4 = java.lang.Long.valueOf(r4);	 Catch:{ IllegalArgumentException -> 0x005e }
        goto L_0x002c;
    L_0x02d0:
        r4 = java.lang.Float.class;
        r0 = r37;
        if (r0 == r4) goto L_0x02dc;
    L_0x02d6:
        r4 = java.lang.Float.TYPE;	 Catch:{ IllegalArgumentException -> 0x005e }
        r0 = r37;
        if (r0 != r4) goto L_0x02e6;
    L_0x02dc:
        r4 = r38.getFloatValue();	 Catch:{ IllegalArgumentException -> 0x005e }
        r4 = java.lang.Float.valueOf(r4);	 Catch:{ IllegalArgumentException -> 0x005e }
        goto L_0x002c;
    L_0x02e6:
        r4 = java.lang.Integer.class;
        r0 = r37;
        if (r0 == r4) goto L_0x02f2;
    L_0x02ec:
        r4 = java.lang.Integer.TYPE;	 Catch:{ IllegalArgumentException -> 0x005e }
        r0 = r37;
        if (r0 != r4) goto L_0x02fc;
    L_0x02f2:
        r4 = r38.getIntValue();	 Catch:{ IllegalArgumentException -> 0x005e }
        r4 = java.lang.Integer.valueOf(r4);	 Catch:{ IllegalArgumentException -> 0x005e }
        goto L_0x002c;
    L_0x02fc:
        r4 = java.lang.Short.class;
        r0 = r37;
        if (r0 == r4) goto L_0x0308;
    L_0x0302:
        r4 = java.lang.Short.TYPE;	 Catch:{ IllegalArgumentException -> 0x005e }
        r0 = r37;
        if (r0 != r4) goto L_0x0312;
    L_0x0308:
        r4 = r38.getShortValue();	 Catch:{ IllegalArgumentException -> 0x005e }
        r4 = java.lang.Short.valueOf(r4);	 Catch:{ IllegalArgumentException -> 0x005e }
        goto L_0x002c;
    L_0x0312:
        r4 = java.lang.Byte.class;
        r0 = r37;
        if (r0 == r4) goto L_0x031e;
    L_0x0318:
        r4 = java.lang.Byte.TYPE;	 Catch:{ IllegalArgumentException -> 0x005e }
        r0 = r37;
        if (r0 != r4) goto L_0x0328;
    L_0x031e:
        r4 = r38.getByteValue();	 Catch:{ IllegalArgumentException -> 0x005e }
        r4 = java.lang.Byte.valueOf(r4);	 Catch:{ IllegalArgumentException -> 0x005e }
        goto L_0x002c;
    L_0x0328:
        r4 = new java.lang.IllegalArgumentException;	 Catch:{ IllegalArgumentException -> 0x005e }
        r5 = new java.lang.StringBuilder;	 Catch:{ IllegalArgumentException -> 0x005e }
        r5.<init>();	 Catch:{ IllegalArgumentException -> 0x005e }
        r8 = "expected numeric type but got ";
        r5 = r5.append(r8);	 Catch:{ IllegalArgumentException -> 0x005e }
        r0 = r40;
        r5 = r5.append(r0);	 Catch:{ IllegalArgumentException -> 0x005e }
        r5 = r5.toString();	 Catch:{ IllegalArgumentException -> 0x005e }
        r4.<init>(r5);	 Catch:{ IllegalArgumentException -> 0x005e }
        throw r4;	 Catch:{ IllegalArgumentException -> 0x005e }
    L_0x0343:
        r4 = r38.getText();	 Catch:{ IllegalArgumentException -> 0x005e }
        r4 = r4.trim();	 Catch:{ IllegalArgumentException -> 0x005e }
        r5 = java.util.Locale.US;	 Catch:{ IllegalArgumentException -> 0x005e }
        r30 = r4.toLowerCase(r5);	 Catch:{ IllegalArgumentException -> 0x005e }
        r4 = java.lang.Float.TYPE;	 Catch:{ IllegalArgumentException -> 0x005e }
        r0 = r37;
        if (r0 == r4) goto L_0x0369;
    L_0x0357:
        r4 = java.lang.Float.class;
        r0 = r37;
        if (r0 == r4) goto L_0x0369;
    L_0x035d:
        r4 = java.lang.Double.TYPE;	 Catch:{ IllegalArgumentException -> 0x005e }
        r0 = r37;
        if (r0 == r4) goto L_0x0369;
    L_0x0363:
        r4 = java.lang.Double.class;
        r0 = r37;
        if (r0 != r4) goto L_0x0387;
    L_0x0369:
        r4 = "nan";
        r0 = r30;
        r4 = r0.equals(r4);	 Catch:{ IllegalArgumentException -> 0x005e }
        if (r4 != 0) goto L_0x03a5;
    L_0x0373:
        r4 = "infinity";
        r0 = r30;
        r4 = r0.equals(r4);	 Catch:{ IllegalArgumentException -> 0x005e }
        if (r4 != 0) goto L_0x03a5;
    L_0x037d:
        r4 = "-infinity";
        r0 = r30;
        r4 = r0.equals(r4);	 Catch:{ IllegalArgumentException -> 0x005e }
        if (r4 != 0) goto L_0x03a5;
    L_0x0387:
        if (r37 == 0) goto L_0x039f;
    L_0x0389:
        r4 = java.lang.Number.class;
        r0 = r37;
        r4 = r4.isAssignableFrom(r0);	 Catch:{ IllegalArgumentException -> 0x005e }
        if (r4 == 0) goto L_0x039f;
    L_0x0393:
        if (r39 == 0) goto L_0x03b1;
    L_0x0395:
        r4 = com.google.api.client.json.JsonString.class;
        r0 = r39;
        r4 = r0.getAnnotation(r4);	 Catch:{ IllegalArgumentException -> 0x005e }
        if (r4 == 0) goto L_0x03b1;
    L_0x039f:
        r4 = 1;
    L_0x03a0:
        r5 = "number field formatted as a JSON string must use the @JsonString annotation";
        com.google.api.client.util.Preconditions.checkArgument(r4, r5);	 Catch:{ IllegalArgumentException -> 0x005e }
    L_0x03a5:
        r4 = r38.getText();	 Catch:{ IllegalArgumentException -> 0x005e }
        r0 = r40;
        r4 = com.google.api.client.util.Data.parsePrimitiveValue(r0, r4);	 Catch:{ IllegalArgumentException -> 0x005e }
        goto L_0x002c;
    L_0x03b1:
        r4 = 0;
        goto L_0x03a0;
    L_0x03b3:
        if (r37 == 0) goto L_0x03bb;
    L_0x03b5:
        r4 = r37.isPrimitive();	 Catch:{ IllegalArgumentException -> 0x005e }
        if (r4 != 0) goto L_0x03e3;
    L_0x03bb:
        r4 = 1;
    L_0x03bc:
        r5 = "primitive number field but found a JSON null";
        com.google.api.client.util.Preconditions.checkArgument(r4, r5);	 Catch:{ IllegalArgumentException -> 0x005e }
        if (r37 == 0) goto L_0x03fd;
    L_0x03c3:
        r4 = r37.getModifiers();	 Catch:{ IllegalArgumentException -> 0x005e }
        r4 = r4 & 1536;
        if (r4 == 0) goto L_0x03fd;
    L_0x03cb:
        r4 = java.util.Collection.class;
        r0 = r37;
        r4 = com.google.api.client.util.Types.isAssignableToOrFrom(r0, r4);	 Catch:{ IllegalArgumentException -> 0x005e }
        if (r4 == 0) goto L_0x03e5;
    L_0x03d5:
        r4 = com.google.api.client.util.Data.newCollectionInstance(r40);	 Catch:{ IllegalArgumentException -> 0x005e }
        r4 = r4.getClass();	 Catch:{ IllegalArgumentException -> 0x005e }
        r4 = com.google.api.client.util.Data.nullOf(r4);	 Catch:{ IllegalArgumentException -> 0x005e }
        goto L_0x002c;
    L_0x03e3:
        r4 = 0;
        goto L_0x03bc;
    L_0x03e5:
        r4 = java.util.Map.class;
        r0 = r37;
        r4 = com.google.api.client.util.Types.isAssignableToOrFrom(r0, r4);	 Catch:{ IllegalArgumentException -> 0x005e }
        if (r4 == 0) goto L_0x03fd;
    L_0x03ef:
        r4 = com.google.api.client.util.Data.newMapInstance(r37);	 Catch:{ IllegalArgumentException -> 0x005e }
        r4 = r4.getClass();	 Catch:{ IllegalArgumentException -> 0x005e }
        r4 = com.google.api.client.util.Data.nullOf(r4);	 Catch:{ IllegalArgumentException -> 0x005e }
        goto L_0x002c;
    L_0x03fd:
        r0 = r41;
        r1 = r40;
        r4 = com.google.api.client.util.Types.getRawArrayComponentType(r0, r1);	 Catch:{ IllegalArgumentException -> 0x005e }
        r4 = com.google.api.client.util.Data.nullOf(r4);	 Catch:{ IllegalArgumentException -> 0x005e }
        goto L_0x002c;
    L_0x040b:
        r4 = r29;
        goto L_0x014b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.api.client.json.JsonParser.parseValue(java.lang.reflect.Field, java.lang.reflect.Type, java.util.ArrayList, java.lang.Object, com.google.api.client.json.CustomizeJsonParser, boolean):java.lang.Object");
    }

    private static Field getCachedTypemapFieldFor(Class<?> key) {
        if (key == null) {
            return null;
        }
        lock.lock();
        try {
            if (cachedTypemapFields.containsKey(key)) {
                Field field = (Field) cachedTypemapFields.get(key);
                return field;
            }
            Field value = null;
            for (FieldInfo fieldInfo : ClassInfo.of(key).getFieldInfos()) {
                Field field2 = fieldInfo.getField();
                JsonPolymorphicTypeMap typemapAnnotation = (JsonPolymorphicTypeMap) field2.getAnnotation(JsonPolymorphicTypeMap.class);
                if (typemapAnnotation != null) {
                    Preconditions.checkArgument(value == null, "Class contains more than one field with @JsonPolymorphicTypeMap annotation: %s", key);
                    Preconditions.checkArgument(Data.isPrimitive(field2.getType()), "Field which has the @JsonPolymorphicTypeMap, %s, is not a supported type: %s", key, field2.getType());
                    value = field2;
                    TypeDef[] typeDefs = typemapAnnotation.typeDefinitions();
                    HashSet<String> typeDefKeys = Sets.newHashSet();
                    Preconditions.checkArgument(typeDefs.length > 0, "@JsonPolymorphicTypeMap must have at least one @TypeDef");
                    for (TypeDef typeDef : typeDefs) {
                        Preconditions.checkArgument(typeDefKeys.add(typeDef.key()), "Class contains two @TypeDef annotations with identical key: %s", arr$[i$].key());
                    }
                }
            }
            cachedTypemapFields.put(key, value);
            lock.unlock();
            return value;
        } finally {
            lock.unlock();
        }
    }
}
