package com.dropbox.core.v2.paper;

import com.box.androidsdk.content.models.BoxList;
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

class ListUsersOnPaperDocArgs extends RefPaperDoc {
    protected final UserOnPaperDocFilter filterBy;
    protected final int limit;

    public static class Builder {
        protected final String docId;
        protected UserOnPaperDocFilter filterBy;
        protected int limit;

        protected Builder(String docId) {
            if (docId == null) {
                throw new IllegalArgumentException("Required value for 'docId' is null");
            }
            this.docId = docId;
            this.limit = 1000;
            this.filterBy = UserOnPaperDocFilter.SHARED;
        }

        public Builder withLimit(Integer limit) {
            if (limit.intValue() < 1) {
                throw new IllegalArgumentException("Number 'limit' is smaller than 1");
            } else if (limit.intValue() > 1000) {
                throw new IllegalArgumentException("Number 'limit' is larger than 1000");
            } else {
                if (limit != null) {
                    this.limit = limit.intValue();
                } else {
                    this.limit = 1000;
                }
                return this;
            }
        }

        public Builder withFilterBy(UserOnPaperDocFilter filterBy) {
            if (filterBy != null) {
                this.filterBy = filterBy;
            } else {
                this.filterBy = UserOnPaperDocFilter.SHARED;
            }
            return this;
        }

        public ListUsersOnPaperDocArgs build() {
            return new ListUsersOnPaperDocArgs(this.docId, this.limit, this.filterBy);
        }
    }

    static class Serializer extends StructSerializer<ListUsersOnPaperDocArgs> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ListUsersOnPaperDocArgs value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("doc_id");
            StoneSerializers.string().serialize(value.docId, g);
            g.writeFieldName(BoxList.FIELD_LIMIT);
            StoneSerializers.int32().serialize(Integer.valueOf(value.limit), g);
            g.writeFieldName("filter_by");
            Serializer.INSTANCE.serialize(value.filterBy, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public ListUsersOnPaperDocArgs deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_docId = null;
                Integer f_limit = Integer.valueOf(1000);
                UserOnPaperDocFilter f_filterBy = UserOnPaperDocFilter.SHARED;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("doc_id".equals(field)) {
                        f_docId = (String) StoneSerializers.string().deserialize(p);
                    } else if (BoxList.FIELD_LIMIT.equals(field)) {
                        f_limit = (Integer) StoneSerializers.int32().deserialize(p);
                    } else if ("filter_by".equals(field)) {
                        f_filterBy = Serializer.INSTANCE.deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_docId == null) {
                    throw new JsonParseException(p, "Required field \"doc_id\" missing.");
                }
                ListUsersOnPaperDocArgs value = new ListUsersOnPaperDocArgs(f_docId, f_limit.intValue(), f_filterBy);
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public ListUsersOnPaperDocArgs(String docId, int limit, UserOnPaperDocFilter filterBy) {
        super(docId);
        if (limit < 1) {
            throw new IllegalArgumentException("Number 'limit' is smaller than 1");
        } else if (limit > 1000) {
            throw new IllegalArgumentException("Number 'limit' is larger than 1000");
        } else {
            this.limit = limit;
            if (filterBy == null) {
                throw new IllegalArgumentException("Required value for 'filterBy' is null");
            }
            this.filterBy = filterBy;
        }
    }

    public ListUsersOnPaperDocArgs(String docId) {
        this(docId, 1000, UserOnPaperDocFilter.SHARED);
    }

    public String getDocId() {
        return this.docId;
    }

    public int getLimit() {
        return this.limit;
    }

    public UserOnPaperDocFilter getFilterBy() {
        return this.filterBy;
    }

    public static Builder newBuilder(String docId) {
        return new Builder(docId);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.limit), this.filterBy}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        ListUsersOnPaperDocArgs other = (ListUsersOnPaperDocArgs) obj;
        if ((this.docId == other.docId || this.docId.equals(other.docId)) && this.limit == other.limit && (this.filterBy == other.filterBy || this.filterBy.equals(other.filterBy))) {
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
