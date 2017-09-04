package com.box.androidsdk.content.models;

import java.util.Map;

public class BoxMapJsonObject extends BoxJsonObject {
    public BoxMapJsonObject(Map<String, Object> map) {
        super(map);
    }

    public Object getValue(String key) {
        return this.mProperties.get(key);
    }
}
