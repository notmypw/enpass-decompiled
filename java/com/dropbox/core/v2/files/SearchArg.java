package com.dropbox.core.v2.files;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.StoneSerializers;
import com.dropbox.core.stone.StructSerializer;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;

class SearchArg {
    protected final long maxResults;
    protected final SearchMode mode;
    protected final String path;
    protected final String query;
    protected final long start;

    public static class Builder {
        protected long maxResults;
        protected SearchMode mode;
        protected final String path;
        protected final String query;
        protected long start;

        protected Builder(String path, String query) {
            if (path == null) {
                throw new IllegalArgumentException("Required value for 'path' is null");
            } else if (Pattern.matches("(/(.|[\\r\\n])*)?|(ns:[0-9]+(/.*)?)", path)) {
                this.path = path;
                if (query == null) {
                    throw new IllegalArgumentException("Required value for 'query' is null");
                }
                this.query = query;
                this.start = 0;
                this.maxResults = 100;
                this.mode = SearchMode.FILENAME;
            } else {
                throw new IllegalArgumentException("String 'path' does not match pattern");
            }
        }

        public Builder withStart(Long start) {
            if (start != null) {
                this.start = start.longValue();
            } else {
                this.start = 0;
            }
            return this;
        }

        public Builder withMaxResults(Long maxResults) {
            if (maxResults.longValue() < 1) {
                throw new IllegalArgumentException("Number 'maxResults' is smaller than 1L");
            } else if (maxResults.longValue() > 1000) {
                throw new IllegalArgumentException("Number 'maxResults' is larger than 1000L");
            } else {
                if (maxResults != null) {
                    this.maxResults = maxResults.longValue();
                } else {
                    this.maxResults = 100;
                }
                return this;
            }
        }

        public Builder withMode(SearchMode mode) {
            if (mode != null) {
                this.mode = mode;
            } else {
                this.mode = SearchMode.FILENAME;
            }
            return this;
        }

        public SearchArg build() {
            return new SearchArg(this.path, this.query, this.start, this.maxResults, this.mode);
        }
    }

    static class Serializer extends StructSerializer<SearchArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(SearchArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxMetadataUpdateTask.PATH);
            StoneSerializers.string().serialize(value.path, g);
            g.writeFieldName("query");
            StoneSerializers.string().serialize(value.query, g);
            g.writeFieldName("start");
            StoneSerializers.uInt64().serialize(Long.valueOf(value.start), g);
            g.writeFieldName("max_results");
            StoneSerializers.uInt64().serialize(Long.valueOf(value.maxResults), g);
            g.writeFieldName("mode");
            Serializer.INSTANCE.serialize(value.mode, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public SearchArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_path = null;
                String f_query = null;
                Long f_start = Long.valueOf(0);
                Long f_maxResults = Long.valueOf(100);
                SearchMode f_mode = SearchMode.FILENAME;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxMetadataUpdateTask.PATH.equals(field)) {
                        f_path = (String) StoneSerializers.string().deserialize(p);
                    } else if ("query".equals(field)) {
                        f_query = (String) StoneSerializers.string().deserialize(p);
                    } else if ("start".equals(field)) {
                        f_start = (Long) StoneSerializers.uInt64().deserialize(p);
                    } else if ("max_results".equals(field)) {
                        f_maxResults = (Long) StoneSerializers.uInt64().deserialize(p);
                    } else if ("mode".equals(field)) {
                        f_mode = Serializer.INSTANCE.deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_path == null) {
                    throw new JsonParseException(p, "Required field \"path\" missing.");
                } else if (f_query == null) {
                    throw new JsonParseException(p, "Required field \"query\" missing.");
                } else {
                    SearchArg value = new SearchArg(f_path, f_query, f_start.longValue(), f_maxResults.longValue(), f_mode);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public SearchArg(String path, String query, long start, long maxResults, SearchMode mode) {
        if (path == null) {
            throw new IllegalArgumentException("Required value for 'path' is null");
        } else if (Pattern.matches("(/(.|[\\r\\n])*)?|(ns:[0-9]+(/.*)?)", path)) {
            this.path = path;
            if (query == null) {
                throw new IllegalArgumentException("Required value for 'query' is null");
            }
            this.query = query;
            this.start = start;
            if (maxResults < 1) {
                throw new IllegalArgumentException("Number 'maxResults' is smaller than 1L");
            } else if (maxResults > 1000) {
                throw new IllegalArgumentException("Number 'maxResults' is larger than 1000L");
            } else {
                this.maxResults = maxResults;
                if (mode == null) {
                    throw new IllegalArgumentException("Required value for 'mode' is null");
                }
                this.mode = mode;
            }
        } else {
            throw new IllegalArgumentException("String 'path' does not match pattern");
        }
    }

    public SearchArg(String path, String query) {
        this(path, query, 0, 100, SearchMode.FILENAME);
    }

    public String getPath() {
        return this.path;
    }

    public String getQuery() {
        return this.query;
    }

    public long getStart() {
        return this.start;
    }

    public long getMaxResults() {
        return this.maxResults;
    }

    public SearchMode getMode() {
        return this.mode;
    }

    public static Builder newBuilder(String path, String query) {
        return new Builder(path, query);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.path, this.query, Long.valueOf(this.start), Long.valueOf(this.maxResults), this.mode});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        SearchArg other = (SearchArg) obj;
        if ((this.path == other.path || this.path.equals(other.path)) && ((this.query == other.query || this.query.equals(other.query)) && this.start == other.start && this.maxResults == other.maxResults && (this.mode == other.mode || this.mode.equals(other.mode)))) {
            return true;
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
