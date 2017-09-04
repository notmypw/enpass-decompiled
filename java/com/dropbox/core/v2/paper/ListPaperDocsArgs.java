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

class ListPaperDocsArgs {
    protected final ListPaperDocsFilterBy filterBy;
    protected final int limit;
    protected final ListPaperDocsSortBy sortBy;
    protected final ListPaperDocsSortOrder sortOrder;

    public static class Builder {
        protected ListPaperDocsFilterBy filterBy = ListPaperDocsFilterBy.DOCS_ACCESSED;
        protected int limit = 1000;
        protected ListPaperDocsSortBy sortBy = ListPaperDocsSortBy.ACCESSED;
        protected ListPaperDocsSortOrder sortOrder = ListPaperDocsSortOrder.ASCENDING;

        protected Builder() {
        }

        public Builder withFilterBy(ListPaperDocsFilterBy filterBy) {
            if (filterBy != null) {
                this.filterBy = filterBy;
            } else {
                this.filterBy = ListPaperDocsFilterBy.DOCS_ACCESSED;
            }
            return this;
        }

        public Builder withSortBy(ListPaperDocsSortBy sortBy) {
            if (sortBy != null) {
                this.sortBy = sortBy;
            } else {
                this.sortBy = ListPaperDocsSortBy.ACCESSED;
            }
            return this;
        }

        public Builder withSortOrder(ListPaperDocsSortOrder sortOrder) {
            if (sortOrder != null) {
                this.sortOrder = sortOrder;
            } else {
                this.sortOrder = ListPaperDocsSortOrder.ASCENDING;
            }
            return this;
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

        public ListPaperDocsArgs build() {
            return new ListPaperDocsArgs(this.filterBy, this.sortBy, this.sortOrder, this.limit);
        }
    }

    static class Serializer extends StructSerializer<ListPaperDocsArgs> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(ListPaperDocsArgs value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("filter_by");
            Serializer.INSTANCE.serialize(value.filterBy, g);
            g.writeFieldName("sort_by");
            Serializer.INSTANCE.serialize(value.sortBy, g);
            g.writeFieldName("sort_order");
            Serializer.INSTANCE.serialize(value.sortOrder, g);
            g.writeFieldName(BoxList.FIELD_LIMIT);
            StoneSerializers.int32().serialize(Integer.valueOf(value.limit), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public ListPaperDocsArgs deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                ListPaperDocsFilterBy f_filterBy = ListPaperDocsFilterBy.DOCS_ACCESSED;
                ListPaperDocsSortBy f_sortBy = ListPaperDocsSortBy.ACCESSED;
                ListPaperDocsSortOrder f_sortOrder = ListPaperDocsSortOrder.ASCENDING;
                Integer f_limit = Integer.valueOf(1000);
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("filter_by".equals(field)) {
                        f_filterBy = Serializer.INSTANCE.deserialize(p);
                    } else if ("sort_by".equals(field)) {
                        f_sortBy = Serializer.INSTANCE.deserialize(p);
                    } else if ("sort_order".equals(field)) {
                        f_sortOrder = Serializer.INSTANCE.deserialize(p);
                    } else if (BoxList.FIELD_LIMIT.equals(field)) {
                        f_limit = (Integer) StoneSerializers.int32().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                ListPaperDocsArgs value = new ListPaperDocsArgs(f_filterBy, f_sortBy, f_sortOrder, f_limit.intValue());
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public ListPaperDocsArgs(ListPaperDocsFilterBy filterBy, ListPaperDocsSortBy sortBy, ListPaperDocsSortOrder sortOrder, int limit) {
        if (filterBy == null) {
            throw new IllegalArgumentException("Required value for 'filterBy' is null");
        }
        this.filterBy = filterBy;
        if (sortBy == null) {
            throw new IllegalArgumentException("Required value for 'sortBy' is null");
        }
        this.sortBy = sortBy;
        if (sortOrder == null) {
            throw new IllegalArgumentException("Required value for 'sortOrder' is null");
        }
        this.sortOrder = sortOrder;
        if (limit < 1) {
            throw new IllegalArgumentException("Number 'limit' is smaller than 1");
        } else if (limit > 1000) {
            throw new IllegalArgumentException("Number 'limit' is larger than 1000");
        } else {
            this.limit = limit;
        }
    }

    public ListPaperDocsArgs() {
        this(ListPaperDocsFilterBy.DOCS_ACCESSED, ListPaperDocsSortBy.ACCESSED, ListPaperDocsSortOrder.ASCENDING, 1000);
    }

    public ListPaperDocsFilterBy getFilterBy() {
        return this.filterBy;
    }

    public ListPaperDocsSortBy getSortBy() {
        return this.sortBy;
    }

    public ListPaperDocsSortOrder getSortOrder() {
        return this.sortOrder;
    }

    public int getLimit() {
        return this.limit;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.filterBy, this.sortBy, this.sortOrder, Integer.valueOf(this.limit)});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        ListPaperDocsArgs other = (ListPaperDocsArgs) obj;
        if ((this.filterBy == other.filterBy || this.filterBy.equals(other.filterBy)) && ((this.sortBy == other.sortBy || this.sortBy.equals(other.sortBy)) && ((this.sortOrder == other.sortOrder || this.sortOrder.equals(other.sortOrder)) && this.limit == other.limit))) {
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
