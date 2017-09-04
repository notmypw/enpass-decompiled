package com.dropbox.core;

import com.dropbox.core.json.JsonReadException;
import com.dropbox.core.json.JsonReader;
import com.dropbox.core.json.JsonWriter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;

public final class DbxHost {
    public static final DbxHost DEFAULT = new DbxHost("api.dropboxapi.com", "content.dropboxapi.com", "www.dropbox.com", "notify.dropboxapi.com");
    public static final JsonReader<DbxHost> Reader = new JsonReader<DbxHost>() {
        public DbxHost read(JsonParser parser) throws IOException, JsonReadException {
            JsonToken t = parser.getCurrentToken();
            if (t == JsonToken.VALUE_STRING) {
                String s = parser.getText();
                JsonReader.nextToken(parser);
                return DbxHost.fromBaseHost(s);
            } else if (t == JsonToken.START_OBJECT) {
                JsonLocation top = parser.getTokenLocation();
                JsonReader.nextToken(parser);
                String api = null;
                String content = null;
                String web = null;
                String notify = null;
                while (parser.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String fieldName = parser.getCurrentName();
                    parser.nextToken();
                    try {
                        if (fieldName.equals("api")) {
                            api = (String) JsonReader.StringReader.readField(parser, fieldName, api);
                        } else if (fieldName.equals("content")) {
                            content = (String) JsonReader.StringReader.readField(parser, fieldName, content);
                        } else if (fieldName.equals("web")) {
                            web = (String) JsonReader.StringReader.readField(parser, fieldName, web);
                        } else if (fieldName.equals("notify")) {
                            notify = (String) JsonReader.StringReader.readField(parser, fieldName, notify);
                        } else {
                            throw new JsonReadException("unknown field", parser.getCurrentLocation());
                        }
                    } catch (JsonReadException ex) {
                        throw ex.addFieldContext(fieldName);
                    }
                }
                JsonReader.expectObjectEnd(parser);
                if (api == null) {
                    throw new JsonReadException("missing field \"api\"", top);
                } else if (content == null) {
                    throw new JsonReadException("missing field \"content\"", top);
                } else if (web == null) {
                    throw new JsonReadException("missing field \"web\"", top);
                } else if (notify != null) {
                    return new DbxHost(api, content, web, notify);
                } else {
                    throw new JsonReadException("missing field \"notify\"", top);
                }
            } else {
                throw new JsonReadException("expecting a string or an object", parser.getTokenLocation());
            }
        }
    };
    public static final JsonWriter<DbxHost> Writer = new JsonWriter<DbxHost>() {
        public void write(DbxHost host, JsonGenerator g) throws IOException {
            String base = host.inferBaseHost();
            if (base != null) {
                g.writeString(base);
                return;
            }
            g.writeStartObject();
            g.writeStringField("api", host.api);
            g.writeStringField("content", host.content);
            g.writeStringField("web", host.web);
            g.writeStringField("notify", host.notify);
            g.writeEndObject();
        }
    };
    private final String api;
    private final String content;
    private final String notify;
    private final String web;

    public DbxHost(String api, String content, String web, String notify) {
        this.api = api;
        this.content = content;
        this.web = web;
        this.notify = notify;
    }

    public String getApi() {
        return this.api;
    }

    public String getContent() {
        return this.content;
    }

    public String getWeb() {
        return this.web;
    }

    public String getNotify() {
        return this.notify;
    }

    public int hashCode() {
        return Arrays.hashCode(new String[]{this.api, this.content, this.web, this.notify});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DbxHost)) {
            return false;
        }
        DbxHost other = (DbxHost) obj;
        if (other.api.equals(this.api) && other.content.equals(this.content) && other.web.equals(this.web) && other.notify.equals(this.notify)) {
            return true;
        }
        return false;
    }

    private static DbxHost fromBaseHost(String s) {
        return new DbxHost("api-" + s, "api-content-" + s, "meta-" + s, "api-notify-" + s);
    }

    private String inferBaseHost() {
        if (this.web.startsWith("meta-") && this.api.startsWith("api-") && this.content.startsWith("api-content-") && this.notify.startsWith("api-notify-")) {
            String webBase = this.web.substring("meta-".length());
            String apiBase = this.api.substring("api-".length());
            String contentBase = this.content.substring("api-content-".length());
            String notifyBase = this.notify.substring("api-notify-".length());
            if (webBase.equals(apiBase) && webBase.equals(contentBase) && webBase.equals(notifyBase)) {
                return webBase;
            }
        }
        return null;
    }
}
