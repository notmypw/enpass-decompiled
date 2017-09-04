package com.eclipsesource.json;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class JsonObject extends JsonValue implements Iterable<Member> {
    private final List<String> names;
    private transient HashIndexTable table;
    private final List<JsonValue> values;

    static class HashIndexTable {
        private final byte[] hashTable;

        public HashIndexTable() {
            this.hashTable = new byte[32];
        }

        public HashIndexTable(HashIndexTable original) {
            this.hashTable = new byte[32];
            System.arraycopy(original.hashTable, 0, this.hashTable, 0, this.hashTable.length);
        }

        void add(String name, int index) {
            int slot = hashSlotFor(name);
            if (index < 255) {
                this.hashTable[slot] = (byte) (index + 1);
            } else {
                this.hashTable[slot] = (byte) 0;
            }
        }

        void remove(int index) {
            for (int i = 0; i < this.hashTable.length; i++) {
                if (this.hashTable[i] == index + 1) {
                    this.hashTable[i] = (byte) 0;
                } else if (this.hashTable[i] > index + 1) {
                    byte[] bArr = this.hashTable;
                    bArr[i] = (byte) (bArr[i] - 1);
                }
            }
        }

        int get(Object name) {
            return (this.hashTable[hashSlotFor(name)] & 255) - 1;
        }

        private int hashSlotFor(Object element) {
            return element.hashCode() & (this.hashTable.length - 1);
        }
    }

    public static class Member {
        private final String name;
        private final JsonValue value;

        Member(String name, JsonValue value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return this.name;
        }

        public JsonValue getValue() {
            return this.value;
        }

        public int hashCode() {
            return ((this.name.hashCode() + 31) * 31) + this.value.hashCode();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            Member other = (Member) obj;
            if (this.name.equals(other.name) && this.value.equals(other.value)) {
                return true;
            }
            return false;
        }
    }

    public JsonObject() {
        this.names = new ArrayList();
        this.values = new ArrayList();
        this.table = new HashIndexTable();
    }

    public JsonObject(JsonObject object) {
        this(object, false);
    }

    private JsonObject(JsonObject object, boolean unmodifiable) {
        if (object == null) {
            throw new NullPointerException("object is null");
        }
        if (unmodifiable) {
            this.names = Collections.unmodifiableList(object.names);
            this.values = Collections.unmodifiableList(object.values);
        } else {
            this.names = new ArrayList(object.names);
            this.values = new ArrayList(object.values);
        }
        this.table = new HashIndexTable();
        updateHashIndex();
    }

    public static JsonObject readFrom(Reader reader) throws IOException {
        return JsonValue.readFrom(reader).asObject();
    }

    public static JsonObject readFrom(String string) {
        return JsonValue.readFrom(string).asObject();
    }

    public static JsonObject unmodifiableObject(JsonObject object) {
        return new JsonObject(object, true);
    }

    public JsonObject add(String name, int value) {
        add(name, JsonValue.valueOf(value));
        return this;
    }

    public JsonObject add(String name, long value) {
        add(name, JsonValue.valueOf(value));
        return this;
    }

    public JsonObject add(String name, float value) {
        add(name, JsonValue.valueOf(value));
        return this;
    }

    public JsonObject add(String name, double value) {
        add(name, JsonValue.valueOf(value));
        return this;
    }

    public JsonObject add(String name, boolean value) {
        add(name, JsonValue.valueOf(value));
        return this;
    }

    public JsonObject add(String name, String value) {
        add(name, JsonValue.valueOf(value));
        return this;
    }

    public JsonObject add(String name, JsonValue value) {
        if (name == null) {
            throw new NullPointerException("name is null");
        } else if (value == null) {
            throw new NullPointerException("value is null");
        } else {
            this.table.add(name, this.names.size());
            this.names.add(name);
            this.values.add(value);
            return this;
        }
    }

    public JsonObject set(String name, int value) {
        set(name, JsonValue.valueOf(value));
        return this;
    }

    public JsonObject set(String name, long value) {
        set(name, JsonValue.valueOf(value));
        return this;
    }

    public JsonObject set(String name, float value) {
        set(name, JsonValue.valueOf(value));
        return this;
    }

    public JsonObject set(String name, double value) {
        set(name, JsonValue.valueOf(value));
        return this;
    }

    public JsonObject set(String name, boolean value) {
        set(name, JsonValue.valueOf(value));
        return this;
    }

    public JsonObject set(String name, String value) {
        set(name, JsonValue.valueOf(value));
        return this;
    }

    public JsonObject set(String name, JsonValue value) {
        if (name == null) {
            throw new NullPointerException("name is null");
        } else if (value == null) {
            throw new NullPointerException("value is null");
        } else {
            int index = indexOf(name);
            if (index != -1) {
                this.values.set(index, value);
            } else {
                this.table.add(name, this.names.size());
                this.names.add(name);
                this.values.add(value);
            }
            return this;
        }
    }

    public JsonObject remove(String name) {
        if (name == null) {
            throw new NullPointerException("name is null");
        }
        int index = indexOf(name);
        if (index != -1) {
            this.table.remove(index);
            this.names.remove(index);
            this.values.remove(index);
        }
        return this;
    }

    public JsonValue get(String name) {
        if (name == null) {
            throw new NullPointerException("name is null");
        }
        int index = indexOf(name);
        return index != -1 ? (JsonValue) this.values.get(index) : null;
    }

    public int size() {
        return this.names.size();
    }

    public boolean isEmpty() {
        return this.names.isEmpty();
    }

    public List<String> names() {
        return Collections.unmodifiableList(this.names);
    }

    public Iterator<Member> iterator() {
        final Iterator<String> namesIterator = this.names.iterator();
        final Iterator<JsonValue> valuesIterator = this.values.iterator();
        return new Iterator<Member>() {
            public boolean hasNext() {
                return namesIterator.hasNext();
            }

            public Member next() {
                return new Member((String) namesIterator.next(), (JsonValue) valuesIterator.next());
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    protected void write(JsonWriter writer) throws IOException {
        writer.writeObject(this);
    }

    public boolean isObject() {
        return true;
    }

    public JsonObject asObject() {
        return this;
    }

    public int hashCode() {
        return ((this.names.hashCode() + 31) * 31) + this.values.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        JsonObject other = (JsonObject) obj;
        if (this.names.equals(other.names) && this.values.equals(other.values)) {
            return true;
        }
        return false;
    }

    int indexOf(String name) {
        int index = this.table.get(name);
        return (index == -1 || !name.equals(this.names.get(index))) ? this.names.lastIndexOf(name) : index;
    }

    private synchronized void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        inputStream.defaultReadObject();
        this.table = new HashIndexTable();
        updateHashIndex();
    }

    private void updateHashIndex() {
        int size = this.names.size();
        for (int i = 0; i < size; i++) {
            this.table.add((String) this.names.get(i), i);
        }
    }
}
