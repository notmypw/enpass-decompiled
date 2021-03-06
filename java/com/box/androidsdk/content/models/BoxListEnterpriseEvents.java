package com.box.androidsdk.content.models;

import com.box.androidsdk.content.utils.IStreamPosition;
import com.eclipsesource.json.JsonObject.Member;
import com.eclipsesource.json.JsonValue;
import com.github.clans.fab.BuildConfig;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class BoxListEnterpriseEvents extends BoxList<BoxEnterpriseEvent> implements IStreamPosition {
    public static final String FIELD_CHUNK_SIZE = "chunk_size";
    public static final String FIELD_NEXT_STREAM_POSITION = "next_stream_position";
    private static final long serialVersionUID = 940295540206254689L;
    private final HashSet<String> mEventIds = new HashSet();
    private boolean mFilterDuplicates = true;

    protected void parseJSONMember(Member member) {
        String memberName = member.getName();
        JsonValue value = member.getValue();
        if (memberName.equals(FIELD_NEXT_STREAM_POSITION)) {
            this.mProperties.put(FIELD_NEXT_STREAM_POSITION, value.asString());
        } else if (memberName.equals(FIELD_CHUNK_SIZE)) {
            this.mProperties.put(FIELD_CHUNK_SIZE, Long.valueOf(value.asLong()));
        } else if (memberName.equals(BoxList.FIELD_ENTRIES)) {
            Iterator it = value.asArray().iterator();
            while (it.hasNext()) {
                JsonValue entry = (JsonValue) it.next();
                BoxEnterpriseEvent event = new BoxEnterpriseEvent();
                event.createFromJson(entry.asObject());
                add(event);
            }
            this.mProperties.put(BoxList.FIELD_ENTRIES, this.collection);
        } else {
            super.parseJSONMember(member);
        }
    }

    public boolean add(BoxEnterpriseEvent boxEnterpriseEvent) {
        if (this.mFilterDuplicates && this.mEventIds.contains(boxEnterpriseEvent.getEventId())) {
            return false;
        }
        this.mEventIds.add(boxEnterpriseEvent.getEventId());
        return super.add((BoxJsonObject) boxEnterpriseEvent);
    }

    public boolean remove(Object o) {
        if (o instanceof BoxEvent) {
            this.mEventIds.remove(((BoxEvent) o).getEventId());
        }
        return super.remove(o);
    }

    public boolean removeAll(Collection<?> c) {
        for (Object o : c) {
            if (o instanceof BoxEvent) {
                this.mEventIds.remove(((BoxEvent) o).getEventId());
            }
        }
        return super.removeAll(c);
    }

    public boolean addAll(Collection<? extends BoxEnterpriseEvent> c) {
        boolean addSuccessful = true;
        for (BoxEnterpriseEvent event : c) {
            addSuccessful &= add(event);
        }
        return addSuccessful;
    }

    public void clear() {
        this.mEventIds.clear();
        super.clear();
    }

    public Long getChunkSize() {
        return (Long) this.mProperties.get(FIELD_CHUNK_SIZE);
    }

    public Long getNextStreamPosition() {
        return Long.valueOf(Long.parseLong(((String) this.mProperties.get(FIELD_NEXT_STREAM_POSITION)).replace("\"", BuildConfig.FLAVOR)));
    }

    public void setFilterDuplicates(boolean filterDuplicates) {
        this.mFilterDuplicates = filterDuplicates;
    }
}
