package com.dropbox.core.v1;

import com.box.androidsdk.content.BoxApiMetadata;
import com.box.androidsdk.content.models.BoxList;
import com.dropbox.core.json.JsonArrayReader;
import com.dropbox.core.json.JsonReadException;
import com.dropbox.core.json.JsonReader;
import com.dropbox.core.json.JsonReader.FieldMapping;
import com.dropbox.core.json.JsonReader.FieldMapping.Builder;
import com.dropbox.core.util.Collector;
import com.dropbox.core.util.DumpWriter;
import com.dropbox.core.util.Dumpable;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import net.sqlcipher.database.SQLiteDatabase;

public class DbxDeltaC<C> extends Dumpable {
    public final String cursor;
    public final C entries;
    public final boolean hasMore;
    public final boolean reset;

    public static final class Entry<MD extends Dumpable> extends Dumpable {
        public final String lcPath;
        public final MD metadata;

        public static final class Reader<MD extends Dumpable> extends JsonReader<Entry<MD>> {
            public final JsonReader<MD> metadataReader;

            public Reader(JsonReader<MD> metadataReader) {
                this.metadataReader = metadataReader;
            }

            public Entry<MD> read(JsonParser parser) throws IOException, JsonReadException {
                return read(parser, this.metadataReader);
            }

            public static <MD extends Dumpable> Entry<MD> read(JsonParser parser, JsonReader<MD> metadataReader) throws IOException, JsonReadException {
                JsonLocation arrayStart = JsonReader.expectArrayStart(parser);
                if (JsonReader.isArrayEnd(parser)) {
                    throw new JsonReadException("expecting a two-element array of [path, metadata], found a zero-element array", arrayStart);
                }
                try {
                    String lcPath = (String) JsonReader.StringReader.read(parser);
                    if (JsonReader.isArrayEnd(parser)) {
                        throw new JsonReadException("expecting a two-element array of [path, metadata], found a one-element array", arrayStart);
                    }
                    try {
                        Dumpable metadata = (Dumpable) metadataReader.readOptional(parser);
                        if (JsonReader.isArrayEnd(parser)) {
                            parser.nextToken();
                            return new Entry(lcPath, metadata);
                        }
                        throw new JsonReadException("expecting a two-element array of [path, metadata], found more than two elements", arrayStart);
                    } catch (JsonReadException ex) {
                        throw ex.addArrayContext(1);
                    }
                } catch (JsonReadException ex2) {
                    throw ex2.addArrayContext(0);
                }
            }
        }

        public Entry(String lcPath, MD metadata) {
            this.lcPath = lcPath;
            this.metadata = metadata;
        }

        protected void dumpFields(DumpWriter out) {
            out.f("lcPath").v(this.lcPath);
            out.f(BoxApiMetadata.BOX_API_METADATA).v(this.metadata);
        }
    }

    public static final class Reader<C, MD extends Dumpable> extends JsonReader<DbxDeltaC<C>> {
        private static final FieldMapping FM;
        private static final int FM_cursor = 2;
        private static final int FM_entries = 1;
        private static final int FM_has_more = 3;
        private static final int FM_reset = 0;
        public final Collector<Entry<MD>, C> entryCollector;
        public final JsonReader<MD> metadataReader;

        public Reader(JsonReader<MD> metadataReader, Collector<Entry<MD>, C> entryCollector) {
            this.metadataReader = metadataReader;
            this.entryCollector = entryCollector;
        }

        public DbxDeltaC<C> read(JsonParser parser) throws IOException, JsonReadException {
            return read(parser, this.metadataReader, this.entryCollector);
        }

        public static <C, MD extends Dumpable> DbxDeltaC<C> read(JsonParser parser, JsonReader<MD> metadataReader, Collector<Entry<MD>, C> entryCollector) throws IOException, JsonReadException {
            JsonLocation top = JsonReader.expectObjectStart(parser);
            Boolean reset = null;
            Object entries = null;
            String cursor = null;
            Boolean has_more = null;
            while (parser.getCurrentToken() == JsonToken.FIELD_NAME) {
                String fieldName = parser.getCurrentName();
                JsonReader.nextToken(parser);
                int fi = FM.get(fieldName);
                if (fi == -1) {
                    try {
                        JsonReader.skipValue(parser);
                    } catch (JsonReadException ex) {
                        throw ex.addFieldContext(fieldName);
                    }
                }
                switch (fi) {
                    case SQLiteDatabase.OPEN_READWRITE /*0*/:
                        reset = (Boolean) JsonReader.BooleanReader.readField(parser, fieldName, reset);
                        break;
                    case FM_entries /*1*/:
                        entries = JsonArrayReader.mk(new Reader(metadataReader), entryCollector).readField(parser, fieldName, entries);
                        break;
                    case FM_cursor /*2*/:
                        cursor = (String) JsonReader.StringReader.readField(parser, fieldName, cursor);
                        break;
                    case FM_has_more /*3*/:
                        has_more = (Boolean) JsonReader.BooleanReader.readField(parser, fieldName, has_more);
                        break;
                    default:
                        throw new AssertionError("bad index: " + fi + ", field = \"" + fieldName + "\"");
                }
            }
            JsonReader.expectObjectEnd(parser);
            if (reset == null) {
                throw new JsonReadException("missing field \"path\"", top);
            } else if (entries == null) {
                throw new JsonReadException("missing field \"entries\"", top);
            } else if (cursor == null) {
                throw new JsonReadException("missing field \"cursor\"", top);
            } else if (has_more != null) {
                return new DbxDeltaC(reset.booleanValue(), entries, cursor, has_more.booleanValue());
            } else {
                throw new JsonReadException("missing field \"has_more\"", top);
            }
        }

        static {
            Builder b = new Builder();
            b.add("reset", 0);
            b.add(BoxList.FIELD_ENTRIES, FM_entries);
            b.add("cursor", FM_cursor);
            b.add("has_more", FM_has_more);
            FM = b.build();
        }
    }

    public DbxDeltaC(boolean reset, C entries, String cursor, boolean hasMore) {
        this.reset = reset;
        this.entries = entries;
        this.cursor = cursor;
        this.hasMore = hasMore;
    }

    protected void dumpFields(DumpWriter out) {
        out.f("reset").v(this.reset);
        out.f("cursor").v(this.cursor);
        out.f(BoxList.FIELD_ENTRIES).v(this.hasMore);
    }
}
