package com.box.androidsdk.content.models;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject.Member;
import com.eclipsesource.json.JsonValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class BoxList<E extends BoxJsonObject> extends BoxJsonObject implements Collection<E> {
    public static final String FIELD_ENTRIES = "entries";
    public static final String FIELD_LIMIT = "limit";
    public static final String FIELD_OFFSET = "offset";
    public static final String FIELD_ORDER = "order";
    public static final String FIELD_TOTAL_COUNT = "total_count";
    private static final long serialVersionUID = 8036181424029520417L;
    protected final Collection<E> collection = new ArrayList<E>() {
        public boolean add(E object) {
            BoxList.this.addCollectionToProperties();
            return super.add(object);
        }

        public void add(int index, E object) {
            BoxList.this.addCollectionToProperties();
            super.add(index, object);
        }

        public boolean addAll(Collection<? extends E> collection) {
            BoxList.this.addCollectionToProperties();
            return super.addAll(collection);
        }

        public boolean addAll(int index, Collection<? extends E> collection) {
            BoxList.this.addCollectionToProperties();
            return super.addAll(index, collection);
        }
    };
    protected transient boolean collectionInProperties = false;

    protected void addCollectionToProperties() {
        if (!this.collectionInProperties) {
            this.mProperties.put(FIELD_ENTRIES, this.collection);
            this.collectionInProperties = true;
        }
    }

    public BoxList(Map<String, Object> map) {
        super(map);
    }

    public Long offset() {
        return (Long) this.mProperties.get(FIELD_OFFSET);
    }

    public Long limit() {
        return (Long) this.mProperties.get(FIELD_LIMIT);
    }

    public Long fullSize() {
        return (Long) this.mProperties.get(FIELD_TOTAL_COUNT);
    }

    protected void parseJSONMember(Member member) {
        String memberName = member.getName();
        JsonValue value = member.getValue();
        if (memberName.equals(FIELD_ORDER)) {
            this.mProperties.put(FIELD_ORDER, parseOrder(value));
        } else if (memberName.equals(FIELD_TOTAL_COUNT)) {
            this.mProperties.put(FIELD_TOTAL_COUNT, Long.valueOf(value.asLong()));
        } else if (memberName.equals(FIELD_OFFSET)) {
            this.mProperties.put(FIELD_OFFSET, Long.valueOf(value.asLong()));
        } else if (memberName.equals(FIELD_LIMIT)) {
            this.mProperties.put(FIELD_LIMIT, Long.valueOf(value.asLong()));
        } else if (memberName.equals(FIELD_ENTRIES)) {
            addCollectionToProperties();
            Iterator it = value.asArray().iterator();
            while (it.hasNext()) {
                this.collection.add(BoxEntity.createEntityFromJson(((JsonValue) it.next()).asObject()));
            }
        } else {
            super.parseJSONMember(member);
        }
    }

    private ArrayList<BoxOrder> parseOrder(JsonValue jsonObject) {
        JsonArray entries = jsonObject.asArray();
        ArrayList<BoxOrder> orders = new ArrayList(entries.size());
        Iterator it = entries.iterator();
        while (it.hasNext()) {
            JsonValue entry = (JsonValue) it.next();
            BoxOrder order = new BoxOrder();
            order.createFromJson(entry.asObject());
            orders.add(order);
        }
        return orders;
    }

    protected JsonValue parseJsonObject(Entry<String, Object> entry) {
        if (!((String) entry.getKey()).equals(FIELD_ENTRIES)) {
            return super.parseJsonObject(entry);
        }
        JsonValue jsonArray = new JsonArray();
        for (E obj : (Collection) entry.getValue()) {
            jsonArray.add(obj.toJsonObject());
        }
        return jsonArray;
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

    public ArrayList<BoxOrder> getSortOrders() {
        return (ArrayList) this.mProperties.get(FIELD_ORDER);
    }
}
