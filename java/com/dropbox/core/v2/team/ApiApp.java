package com.dropbox.core.v2.team;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.stone.StructSerializer;
import com.dropbox.core.util.LangUtil;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

public class ApiApp {
    protected final String appId;
    protected final String appName;
    protected final boolean isAppFolder;
    protected final Date linked;
    protected final String publisher;
    protected final String publisherUrl;

    public static class Builder {
        protected final String appId;
        protected final String appName;
        protected final boolean isAppFolder;
        protected Date linked;
        protected String publisher;
        protected String publisherUrl;

        protected Builder(String appId, String appName, boolean isAppFolder) {
            if (appId == null) {
                throw new IllegalArgumentException("Required value for 'appId' is null");
            }
            this.appId = appId;
            if (appName == null) {
                throw new IllegalArgumentException("Required value for 'appName' is null");
            }
            this.appName = appName;
            this.isAppFolder = isAppFolder;
            this.publisher = null;
            this.publisherUrl = null;
            this.linked = null;
        }

        public Builder withPublisher(String publisher) {
            this.publisher = publisher;
            return this;
        }

        public Builder withPublisherUrl(String publisherUrl) {
            this.publisherUrl = publisherUrl;
            return this;
        }

        public Builder withLinked(Date linked) {
            this.linked = LangUtil.truncateMillis(linked);
            return this;
        }

        public ApiApp build() {
            return new ApiApp(this.appId, this.appName, this.isAppFolder, this.publisher, this.publisherUrl, this.linked);
        }
    }

    static class Serializer extends StructSerializer<ApiApp> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ApiApp value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("app_id");
            StoneSerializers.string().serialize(value.appId, g);
            g.writeFieldName("app_name");
            StoneSerializers.string().serialize(value.appName, g);
            g.writeFieldName("is_app_folder");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.isAppFolder), g);
            if (value.publisher != null) {
                g.writeFieldName("publisher");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.publisher, g);
            }
            if (value.publisherUrl != null) {
                g.writeFieldName("publisher_url");
                StoneSerializers.nullable(StoneSerializers.string()).serialize(value.publisherUrl, g);
            }
            if (value.linked != null) {
                g.writeFieldName("linked");
                StoneSerializers.nullable(StoneSerializers.timestamp()).serialize(value.linked, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public ApiApp deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_appId = null;
                String f_appName = null;
                Boolean f_isAppFolder = null;
                String f_publisher = null;
                String f_publisherUrl = null;
                Date f_linked = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("app_id".equals(field)) {
                        f_appId = (String) StoneSerializers.string().deserialize(p);
                    } else if ("app_name".equals(field)) {
                        f_appName = (String) StoneSerializers.string().deserialize(p);
                    } else if ("is_app_folder".equals(field)) {
                        f_isAppFolder = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if ("publisher".equals(field)) {
                        f_publisher = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("publisher_url".equals(field)) {
                        f_publisherUrl = (String) StoneSerializers.nullable(StoneSerializers.string()).deserialize(p);
                    } else if ("linked".equals(field)) {
                        f_linked = (Date) StoneSerializers.nullable(StoneSerializers.timestamp()).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_appId == null) {
                    throw new JsonParseException(p, "Required field \"app_id\" missing.");
                } else if (f_appName == null) {
                    throw new JsonParseException(p, "Required field \"app_name\" missing.");
                } else if (f_isAppFolder == null) {
                    throw new JsonParseException(p, "Required field \"is_app_folder\" missing.");
                } else {
                    ApiApp value = new ApiApp(f_appId, f_appName, f_isAppFolder.booleanValue(), f_publisher, f_publisherUrl, f_linked);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public ApiApp(String appId, String appName, boolean isAppFolder, String publisher, String publisherUrl, Date linked) {
        if (appId == null) {
            throw new IllegalArgumentException("Required value for 'appId' is null");
        }
        this.appId = appId;
        if (appName == null) {
            throw new IllegalArgumentException("Required value for 'appName' is null");
        }
        this.appName = appName;
        this.publisher = publisher;
        this.publisherUrl = publisherUrl;
        this.linked = LangUtil.truncateMillis(linked);
        this.isAppFolder = isAppFolder;
    }

    public ApiApp(String appId, String appName, boolean isAppFolder) {
        this(appId, appName, isAppFolder, null, null, null);
    }

    public String getAppId() {
        return this.appId;
    }

    public String getAppName() {
        return this.appName;
    }

    public boolean getIsAppFolder() {
        return this.isAppFolder;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public String getPublisherUrl() {
        return this.publisherUrl;
    }

    public Date getLinked() {
        return this.linked;
    }

    public static Builder newBuilder(String appId, String appName, boolean isAppFolder) {
        return new Builder(appId, appName, isAppFolder);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.appId, this.appName, this.publisher, this.publisherUrl, this.linked, Boolean.valueOf(this.isAppFolder)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        ApiApp other = (ApiApp) obj;
        if ((this.appId == other.appId || this.appId.equals(other.appId)) && ((this.appName == other.appName || this.appName.equals(other.appName)) && this.isAppFolder == other.isAppFolder && ((this.publisher == other.publisher || (this.publisher != null && this.publisher.equals(other.publisher))) && (this.publisherUrl == other.publisherUrl || (this.publisherUrl != null && this.publisherUrl.equals(other.publisherUrl)))))) {
            if (this.linked == other.linked) {
                return true;
            }
            if (this.linked != null && this.linked.equals(other.linked)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return Serializer.INSTANCE.serialize((Object) this, false);
    }

    public String toStringMultiline() {
        return Serializer.INSTANCE.serialize((Object) this, true);
    }
}
