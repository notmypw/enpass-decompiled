package com.box.androidsdk.content.models;

import com.box.androidsdk.content.utils.BoxDateFormat;
import com.box.androidsdk.content.utils.SdkUtils;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonObject.Member;
import com.eclipsesource.json.JsonValue;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public abstract class BoxJsonObject extends BoxObject implements Serializable {
    private static final long serialVersionUID = 7174936367401884790L;
    protected final LinkedHashMap<String, Object> mProperties;

    public BoxJsonObject() {
        this.mProperties = new LinkedHashMap();
    }

    public BoxJsonObject(Map<String, Object> map) {
        this.mProperties = new LinkedHashMap(map);
    }

    public void createFromJson(String json) {
        createFromJson(JsonObject.readFrom(json));
    }

    public void createFromJson(JsonObject object) {
        Iterator it = object.iterator();
        while (it.hasNext()) {
            Member member = (Member) it.next();
            if (member.getValue().isNull()) {
                parseNullJsonMember(member);
            } else {
                parseJSONMember(member);
            }
        }
    }

    public void parseNullJsonMember(Member member) {
        if (!SdkUtils.isEmptyString(member.getName())) {
            this.mProperties.put(member.getName(), null);
        }
    }

    protected void parseJSONMember(Member member) {
        String memberName = member.getName();
        JsonValue value = member.getValue();
        if (!(this instanceof BoxEntity)) {
            System.out.println("unhandled json member '" + memberName + "' xxx  " + value + " current object " + getClass());
        }
        try {
            this.mProperties.put(memberName, value.asString());
        } catch (UnsupportedOperationException e) {
            this.mProperties.put(memberName, value.toString());
        }
    }

    public String toJson() {
        return toJsonObject().toString();
    }

    protected JsonObject toJsonObject() {
        JsonObject jsonObj = new JsonObject();
        for (Entry<String, Object> entry : this.mProperties.entrySet()) {
            jsonObj.add((String) entry.getKey(), parseJsonObject(entry));
        }
        return jsonObj;
    }

    protected JsonValue parseJsonObject(Entry<String, Object> entry) {
        Object obj = entry.getValue();
        if (obj instanceof BoxJsonObject) {
            return ((BoxJsonObject) obj).toJsonObject();
        }
        if (obj instanceof Integer) {
            return JsonValue.valueOf(((Integer) obj).intValue());
        }
        if (obj instanceof Long) {
            return JsonValue.valueOf(((Long) obj).longValue());
        }
        if (obj instanceof Float) {
            return JsonValue.valueOf(((Float) obj).floatValue());
        }
        if (obj instanceof Double) {
            return JsonValue.valueOf(((Double) obj).doubleValue());
        }
        if (obj instanceof Boolean) {
            return JsonValue.valueOf(((Boolean) obj).booleanValue());
        }
        if (obj instanceof Enum) {
            return JsonValue.valueOf(obj.toString());
        }
        if (obj instanceof Date) {
            return JsonValue.valueOf(BoxDateFormat.format((Date) obj));
        }
        if (obj instanceof String) {
            return JsonValue.valueOf((String) obj);
        }
        if (obj instanceof Collection) {
            return parseJsonArray((Collection) obj);
        }
        return JsonValue.valueOf(null);
    }

    private JsonArray parseJsonArray(Collection collection) {
        JsonArray arr = new JsonArray();
        for (Object o : collection) {
            arr.add(JsonValue.valueOf(o.toString()));
        }
        return arr;
    }

    public HashMap<String, Object> getPropertiesAsHashMap() {
        return (HashMap) SdkUtils.cloneSerializable(this.mProperties);
    }
}
