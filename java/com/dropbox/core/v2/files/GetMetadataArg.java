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

class GetMetadataArg {
    protected final boolean includeDeleted;
    protected final boolean includeHasExplicitSharedMembers;
    protected final boolean includeMediaInfo;
    protected final String path;

    public static class Builder {
        protected boolean includeDeleted;
        protected boolean includeHasExplicitSharedMembers;
        protected boolean includeMediaInfo;
        protected final String path;

        protected Builder(String path) {
            if (path == null) {
                throw new IllegalArgumentException("Required value for 'path' is null");
            } else if (Pattern.matches("(/(.|[\\r\\n])*|id:.*)|(rev:[0-9a-f]{9,})|(ns:[0-9]+(/.*)?)", path)) {
                this.path = path;
                this.includeMediaInfo = false;
                this.includeDeleted = false;
                this.includeHasExplicitSharedMembers = false;
            } else {
                throw new IllegalArgumentException("String 'path' does not match pattern");
            }
        }

        public Builder withIncludeMediaInfo(Boolean includeMediaInfo) {
            if (includeMediaInfo != null) {
                this.includeMediaInfo = includeMediaInfo.booleanValue();
            } else {
                this.includeMediaInfo = false;
            }
            return this;
        }

        public Builder withIncludeDeleted(Boolean includeDeleted) {
            if (includeDeleted != null) {
                this.includeDeleted = includeDeleted.booleanValue();
            } else {
                this.includeDeleted = false;
            }
            return this;
        }

        public Builder withIncludeHasExplicitSharedMembers(Boolean includeHasExplicitSharedMembers) {
            if (includeHasExplicitSharedMembers != null) {
                this.includeHasExplicitSharedMembers = includeHasExplicitSharedMembers.booleanValue();
            } else {
                this.includeHasExplicitSharedMembers = false;
            }
            return this;
        }

        public GetMetadataArg build() {
            return new GetMetadataArg(this.path, this.includeMediaInfo, this.includeDeleted, this.includeHasExplicitSharedMembers);
        }
    }

    static class Serializer extends StructSerializer<GetMetadataArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(GetMetadataArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxMetadataUpdateTask.PATH);
            StoneSerializers.string().serialize(value.path, g);
            g.writeFieldName("include_media_info");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.includeMediaInfo), g);
            g.writeFieldName("include_deleted");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.includeDeleted), g);
            g.writeFieldName("include_has_explicit_shared_members");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.includeHasExplicitSharedMembers), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public GetMetadataArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_path = null;
                Boolean f_includeMediaInfo = Boolean.valueOf(false);
                Boolean f_includeDeleted = Boolean.valueOf(false);
                Boolean f_includeHasExplicitSharedMembers = Boolean.valueOf(false);
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxMetadataUpdateTask.PATH.equals(field)) {
                        f_path = (String) StoneSerializers.string().deserialize(p);
                    } else if ("include_media_info".equals(field)) {
                        f_includeMediaInfo = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if ("include_deleted".equals(field)) {
                        f_includeDeleted = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if ("include_has_explicit_shared_members".equals(field)) {
                        f_includeHasExplicitSharedMembers = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_path == null) {
                    throw new JsonParseException(p, "Required field \"path\" missing.");
                }
                GetMetadataArg value = new GetMetadataArg(f_path, f_includeMediaInfo.booleanValue(), f_includeDeleted.booleanValue(), f_includeHasExplicitSharedMembers.booleanValue());
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public GetMetadataArg(String path, boolean includeMediaInfo, boolean includeDeleted, boolean includeHasExplicitSharedMembers) {
        if (path == null) {
            throw new IllegalArgumentException("Required value for 'path' is null");
        } else if (Pattern.matches("(/(.|[\\r\\n])*|id:.*)|(rev:[0-9a-f]{9,})|(ns:[0-9]+(/.*)?)", path)) {
            this.path = path;
            this.includeMediaInfo = includeMediaInfo;
            this.includeDeleted = includeDeleted;
            this.includeHasExplicitSharedMembers = includeHasExplicitSharedMembers;
        } else {
            throw new IllegalArgumentException("String 'path' does not match pattern");
        }
    }

    public GetMetadataArg(String path) {
        this(path, false, false, false);
    }

    public String getPath() {
        return this.path;
    }

    public boolean getIncludeMediaInfo() {
        return this.includeMediaInfo;
    }

    public boolean getIncludeDeleted() {
        return this.includeDeleted;
    }

    public boolean getIncludeHasExplicitSharedMembers() {
        return this.includeHasExplicitSharedMembers;
    }

    public static Builder newBuilder(String path) {
        return new Builder(path);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.path, Boolean.valueOf(this.includeMediaInfo), Boolean.valueOf(this.includeDeleted), Boolean.valueOf(this.includeHasExplicitSharedMembers)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        GetMetadataArg other = (GetMetadataArg) obj;
        if ((this.path == other.path || this.path.equals(other.path)) && this.includeMediaInfo == other.includeMediaInfo && this.includeDeleted == other.includeDeleted && this.includeHasExplicitSharedMembers == other.includeHasExplicitSharedMembers) {
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
