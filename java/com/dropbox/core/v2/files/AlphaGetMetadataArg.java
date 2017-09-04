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
import java.util.List;
import java.util.regex.Pattern;

class AlphaGetMetadataArg extends GetMetadataArg {
    protected final List<String> includePropertyTemplates;

    public static class Builder extends com.dropbox.core.v2.files.GetMetadataArg.Builder {
        protected List<String> includePropertyTemplates = null;

        protected Builder(String path) {
            super(path);
        }

        public Builder withIncludePropertyTemplates(List<String> includePropertyTemplates) {
            if (includePropertyTemplates != null) {
                for (String x : includePropertyTemplates) {
                    if (x == null) {
                        throw new IllegalArgumentException("An item in list 'includePropertyTemplates' is null");
                    } else if (x.length() < 1) {
                        throw new IllegalArgumentException("Stringan item in list 'includePropertyTemplates' is shorter than 1");
                    } else if (!Pattern.matches("(/|ptid:).*", x)) {
                        throw new IllegalArgumentException("Stringan item in list 'includePropertyTemplates' does not match pattern");
                    }
                }
            }
            this.includePropertyTemplates = includePropertyTemplates;
            return this;
        }

        public Builder withIncludeMediaInfo(Boolean includeMediaInfo) {
            super.withIncludeMediaInfo(includeMediaInfo);
            return this;
        }

        public Builder withIncludeDeleted(Boolean includeDeleted) {
            super.withIncludeDeleted(includeDeleted);
            return this;
        }

        public Builder withIncludeHasExplicitSharedMembers(Boolean includeHasExplicitSharedMembers) {
            super.withIncludeHasExplicitSharedMembers(includeHasExplicitSharedMembers);
            return this;
        }

        public AlphaGetMetadataArg build() {
            return new AlphaGetMetadataArg(this.path, this.includeMediaInfo, this.includeDeleted, this.includeHasExplicitSharedMembers, this.includePropertyTemplates);
        }
    }

    static class Serializer extends StructSerializer<AlphaGetMetadataArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(AlphaGetMetadataArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
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
            if (value.includePropertyTemplates != null) {
                g.writeFieldName("include_property_templates");
                StoneSerializers.nullable(StoneSerializers.list(StoneSerializers.string())).serialize(value.includePropertyTemplates, g);
            }
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public AlphaGetMetadataArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
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
                List<String> f_includePropertyTemplates = null;
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
                    } else if ("include_property_templates".equals(field)) {
                        f_includePropertyTemplates = (List) StoneSerializers.nullable(StoneSerializers.list(StoneSerializers.string())).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_path == null) {
                    throw new JsonParseException(p, "Required field \"path\" missing.");
                }
                AlphaGetMetadataArg value = new AlphaGetMetadataArg(f_path, f_includeMediaInfo.booleanValue(), f_includeDeleted.booleanValue(), f_includeHasExplicitSharedMembers.booleanValue(), f_includePropertyTemplates);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public AlphaGetMetadataArg(String path, boolean includeMediaInfo, boolean includeDeleted, boolean includeHasExplicitSharedMembers, List<String> includePropertyTemplates) {
        super(path, includeMediaInfo, includeDeleted, includeHasExplicitSharedMembers);
        if (includePropertyTemplates != null) {
            for (String x : includePropertyTemplates) {
                if (x == null) {
                    throw new IllegalArgumentException("An item in list 'includePropertyTemplates' is null");
                } else if (x.length() < 1) {
                    throw new IllegalArgumentException("Stringan item in list 'includePropertyTemplates' is shorter than 1");
                } else if (!Pattern.matches("(/|ptid:).*", x)) {
                    throw new IllegalArgumentException("Stringan item in list 'includePropertyTemplates' does not match pattern");
                }
            }
        }
        this.includePropertyTemplates = includePropertyTemplates;
    }

    public AlphaGetMetadataArg(String path) {
        this(path, false, false, false, null);
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

    public List<String> getIncludePropertyTemplates() {
        return this.includePropertyTemplates;
    }

    public static Builder newBuilder(String path) {
        return new Builder(path);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.includePropertyTemplates}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        AlphaGetMetadataArg other = (AlphaGetMetadataArg) obj;
        if ((this.path == other.path || this.path.equals(other.path)) && this.includeMediaInfo == other.includeMediaInfo && this.includeDeleted == other.includeDeleted && this.includeHasExplicitSharedMembers == other.includeHasExplicitSharedMembers) {
            if (this.includePropertyTemplates == other.includePropertyTemplates) {
                return true;
            }
            if (this.includePropertyTemplates != null && this.includePropertyTemplates.equals(other.includePropertyTemplates)) {
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
