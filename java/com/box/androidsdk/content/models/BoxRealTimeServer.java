package com.box.androidsdk.content.models;

import com.box.androidsdk.content.utils.SdkUtils;
import com.eclipsesource.json.JsonObject.Member;
import com.eclipsesource.json.JsonValue;
import java.util.Map;

public class BoxRealTimeServer extends BoxJsonObject {
    public static final String FIELD_MAX_RETRIES = "max_retries";
    public static final String FIELD_RETRY_TIMEOUT = "retry_timeout";
    public static final String FIELD_TTL = "ttl";
    public static final String FIELD_TYPE = "type";
    public static final String FIELD_URL = "url";
    public static final String TYPE = "realtime_server";
    private static final long serialVersionUID = -6591493101188395748L;

    protected void parseJSONMember(Member member) {
        String memberName = member.getName();
        JsonValue value = member.getValue();
        if (memberName.equals(FIELD_TYPE)) {
            this.mProperties.put(FIELD_TYPE, value.asString());
        } else if (memberName.equals(FIELD_URL)) {
            this.mProperties.put(FIELD_URL, value.asString());
        } else if (memberName.equals(FIELD_TTL)) {
            this.mProperties.put(FIELD_TTL, Long.valueOf(SdkUtils.parseJsonValueToLong(value)));
        } else if (memberName.equals(FIELD_MAX_RETRIES)) {
            this.mProperties.put(FIELD_MAX_RETRIES, Long.valueOf(SdkUtils.parseJsonValueToLong(value)));
        } else if (memberName.equals(FIELD_RETRY_TIMEOUT)) {
            this.mProperties.put(FIELD_RETRY_TIMEOUT, Long.valueOf(SdkUtils.parseJsonValueToLong(value)));
        } else {
            super.parseJSONMember(member);
        }
    }

    public String getType() {
        return (String) this.mProperties.get(TYPE);
    }

    public String getUrl() {
        return (String) this.mProperties.get(FIELD_URL);
    }

    public Long getTTL() {
        return (Long) this.mProperties.get(FIELD_TTL);
    }

    public Long getMaxRetries() {
        return (Long) this.mProperties.get(FIELD_MAX_RETRIES);
    }

    public Long getFieldRetryTimeout() {
        return Long.valueOf(((Long) this.mProperties.get(FIELD_RETRY_TIMEOUT)).longValue() - 590);
    }

    public BoxRealTimeServer(Map<String, Object> map) {
        super(map);
    }
}
