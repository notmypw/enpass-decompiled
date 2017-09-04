package com.dropbox.core.v1;

import com.dropbox.core.json.JsonArrayReader;
import com.dropbox.core.json.JsonDateReader;
import com.dropbox.core.json.JsonReadException;
import com.dropbox.core.json.JsonReader;
import com.dropbox.core.json.JsonReader.FieldMapping;
import com.dropbox.core.json.JsonReader.FieldMapping.Builder;
import com.dropbox.core.util.Collector;
import com.dropbox.core.util.Collector.ArrayListCollector;
import com.dropbox.core.util.DumpWriter;
import com.dropbox.core.util.Dumpable;
import com.dropbox.core.util.LangUtil;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import in.sinew.enpassengine.Attachment;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public abstract class DbxEntry extends Dumpable implements Serializable {
    static final /* synthetic */ boolean $assertionsDisabled = (!DbxEntry.class.desiredAssertionStatus() ? true : $assertionsDisabled);
    private static final FieldMapping FM;
    private static final int FM_bytes = 1;
    private static final int FM_client_mtime = 9;
    private static final int FM_contents = 11;
    private static final int FM_hash = 10;
    private static final int FM_icon = 7;
    private static final int FM_is_deleted = 4;
    private static final int FM_is_dir = 3;
    private static final int FM_modified = 8;
    private static final int FM_path = 2;
    private static final int FM_photo_info = 12;
    private static final int FM_rev = 5;
    private static final int FM_size = 0;
    private static final int FM_thumb_exists = 6;
    private static final int FM_video_info = 13;
    public static final JsonReader<DbxEntry> Reader = new JsonReader<DbxEntry>() {
        public final DbxEntry read(JsonParser parser) throws IOException, JsonReadException {
            return DbxEntry.read(parser, null).entry;
        }
    };
    public static final JsonReader<DbxEntry> ReaderMaybeDeleted = new JsonReader<DbxEntry>() {
        public final DbxEntry read(JsonParser parser) throws IOException, JsonReadException {
            WithChildrenC<?> wc = DbxEntry.readMaybeDeleted(parser, null);
            if (wc == null) {
                return null;
            }
            return wc.entry;
        }
    };
    public static final long serialVersionUID = 0;
    public final String iconName;
    public final boolean mightHaveThumbnail;
    public final String name;
    public final String path;

    public static final class File extends DbxEntry {
        public static final JsonReader<File> Reader = new JsonReader<File>() {
            public final File read(JsonParser parser) throws IOException, JsonReadException {
                JsonLocation top = parser.getCurrentLocation();
                DbxEntry e = DbxEntry.read(parser, null).entry;
                if (e instanceof File) {
                    return (File) e;
                }
                throw new JsonReadException("Expecting a file entry, got a folder entry", top);
            }
        };
        public static final JsonReader<File> ReaderMaybeDeleted = new JsonReader<File>() {
            public final File read(JsonParser parser) throws IOException, JsonReadException {
                JsonLocation top = parser.getCurrentLocation();
                WithChildrenC<?> wc = DbxEntry._read(parser, null, true);
                if (wc == null) {
                    return null;
                }
                DbxEntry e = wc.entry;
                if (e instanceof File) {
                    return (File) e;
                }
                throw new JsonReadException("Expecting a file entry, got a folder entry", top);
            }
        };
        public static final long serialVersionUID = 0;
        public final Date clientMtime;
        public final String humanSize;
        public final Date lastModified;
        public final long numBytes;
        public final PhotoInfo photoInfo;
        public final String rev;
        public final VideoInfo videoInfo;

        public static class Location extends Dumpable {
            public static JsonReader<Location> Reader = new JsonReader<Location>() {
                public Location read(JsonParser parser) throws IOException, JsonReadException {
                    if (JsonReader.isArrayStart(parser)) {
                        JsonReader.expectArrayStart(parser);
                        Location location = new Location(JsonReader.readDouble(parser), JsonReader.readDouble(parser));
                        JsonReader.expectArrayEnd(parser);
                        return location;
                    }
                    JsonReader.skipValue(parser);
                    return null;
                }
            };
            public final double latitude;
            public final double longitude;

            public Location(double latitude, double longitude) {
                this.latitude = latitude;
                this.longitude = longitude;
            }

            protected void dumpFields(DumpWriter w) {
                w.f("latitude").v(this.latitude);
                w.f("longitude").v(this.longitude);
            }

            public int hashCode() {
                long latitudeBits = Double.doubleToLongBits(this.latitude);
                long longitudeBits = Double.doubleToLongBits(this.longitude);
                return ((((int) ((latitudeBits >>> 32) ^ latitudeBits)) + 527) * 31) + ((int) ((longitudeBits >>> 32) ^ longitudeBits));
            }

            public boolean equals(Object o) {
                return (o != null && getClass().equals(o.getClass()) && equals((Location) o)) ? true : DbxEntry.$assertionsDisabled;
            }

            public boolean equals(Location o) {
                if (this.latitude == o.latitude && this.longitude == o.longitude) {
                    return true;
                }
                return DbxEntry.$assertionsDisabled;
            }
        }

        public static final class PhotoInfo extends Dumpable {
            public static final PhotoInfo PENDING = new PhotoInfo(null, null);
            public static JsonReader<PhotoInfo> Reader = new JsonReader<PhotoInfo>() {
                public PhotoInfo read(JsonParser parser) throws IOException, JsonReadException {
                    JsonReader.expectObjectStart(parser);
                    Date time_taken = null;
                    Location location = null;
                    while (parser.getCurrentToken() == JsonToken.FIELD_NAME) {
                        String fieldName = parser.getCurrentName();
                        JsonReader.nextToken(parser);
                        if (fieldName.equals("lat_long")) {
                            location = (Location) Location.Reader.read(parser);
                        } else if (fieldName.equals("time_taken")) {
                            time_taken = (Date) JsonDateReader.Dropbox.readOptional(parser);
                        } else {
                            JsonReader.skipValue(parser);
                        }
                    }
                    JsonReader.expectObjectEnd(parser);
                    return new PhotoInfo(time_taken, location);
                }
            };
            public final Location location;
            public final Date timeTaken;

            public PhotoInfo(Date timeTaken, Location location) {
                this.timeTaken = timeTaken;
                this.location = location;
            }

            protected void dumpFields(DumpWriter w) {
                w.f("timeTaken").v(this.timeTaken);
                w.f("location").v(this.location);
            }

            public boolean equals(Object o) {
                return (o != null && getClass().equals(o.getClass()) && equals((PhotoInfo) o)) ? true : DbxEntry.$assertionsDisabled;
            }

            public boolean equals(PhotoInfo o) {
                boolean z = true;
                if (o == PENDING || this == PENDING) {
                    if (o != this) {
                        z = DbxEntry.$assertionsDisabled;
                    }
                    return z;
                } else if (LangUtil.nullableEquals(this.timeTaken, o.timeTaken) && LangUtil.nullableEquals(this.location, o.location)) {
                    return true;
                } else {
                    return DbxEntry.$assertionsDisabled;
                }
            }

            public int hashCode() {
                return ((LangUtil.nullableHashCode(this.timeTaken) + DbxEntry.FM_size) * 31) + LangUtil.nullableHashCode(this.location);
            }
        }

        public static final class VideoInfo extends Dumpable {
            public static final VideoInfo PENDING = new VideoInfo(null, null, null);
            public static JsonReader<VideoInfo> Reader = new JsonReader<VideoInfo>() {
                public VideoInfo read(JsonParser parser) throws IOException, JsonReadException {
                    JsonReader.expectObjectStart(parser);
                    Location location = null;
                    Date time_taken = null;
                    Long duration = null;
                    while (parser.getCurrentToken() == JsonToken.FIELD_NAME) {
                        String fieldName = parser.getCurrentName();
                        JsonReader.nextToken(parser);
                        if (fieldName.equals("lat_long")) {
                            location = (Location) Location.Reader.read(parser);
                        } else if (fieldName.equals("time_taken")) {
                            time_taken = (Date) JsonDateReader.Dropbox.readOptional(parser);
                        } else if (fieldName.equals("duration")) {
                            duration = (Long) JsonReader.UnsignedLongReader.readOptional(parser);
                        } else {
                            JsonReader.skipValue(parser);
                        }
                    }
                    JsonReader.expectObjectEnd(parser);
                    return new VideoInfo(time_taken, location, duration);
                }
            };
            public final Long duration;
            public final Location location;
            public final Date timeTaken;

            public VideoInfo(Date timeTaken, Location location, Long duration) {
                this.timeTaken = timeTaken;
                this.location = location;
                this.duration = duration;
            }

            protected void dumpFields(DumpWriter w) {
                w.f("timeTaken").v(this.timeTaken);
                w.f("location").v(this.location);
                w.f("duration").v(this.duration);
            }

            public boolean equals(Object o) {
                return (o != null && getClass().equals(o.getClass()) && equals((VideoInfo) o)) ? true : DbxEntry.$assertionsDisabled;
            }

            public boolean equals(VideoInfo o) {
                boolean z = true;
                if (o == PENDING || this == PENDING) {
                    if (o != this) {
                        z = DbxEntry.$assertionsDisabled;
                    }
                    return z;
                } else if (LangUtil.nullableEquals(this.timeTaken, o.timeTaken) && LangUtil.nullableEquals(this.location, o.location) && LangUtil.nullableEquals(this.duration, o.duration)) {
                    return true;
                } else {
                    return DbxEntry.$assertionsDisabled;
                }
            }

            public int hashCode() {
                return ((((LangUtil.nullableHashCode(this.timeTaken) + DbxEntry.FM_size) * 31) + LangUtil.nullableHashCode(this.location)) * 31) + LangUtil.nullableHashCode(this.duration);
            }
        }

        public File(String path, String iconName, boolean mightHaveThumbnail, long numBytes, String humanSize, Date lastModified, Date clientMtime, String rev, PhotoInfo photoInfo, VideoInfo videoInfo) {
            super(path, iconName, mightHaveThumbnail);
            this.numBytes = numBytes;
            this.humanSize = humanSize;
            this.lastModified = lastModified;
            this.clientMtime = clientMtime;
            this.rev = rev;
            this.photoInfo = photoInfo;
            this.videoInfo = videoInfo;
        }

        public File(String path, String iconName, boolean mightHaveThumbnail, long numBytes, String humanSize, Date lastModified, Date clientMtime, String rev) {
            this(path, iconName, mightHaveThumbnail, numBytes, humanSize, lastModified, clientMtime, rev, null, null);
        }

        protected void dumpFields(DumpWriter w) {
            super.dumpFields(w);
            w.f("numBytes").v(this.numBytes);
            w.f("humanSize").v(this.humanSize);
            w.f("lastModified").v(this.lastModified);
            w.f("clientMtime").v(this.clientMtime);
            w.f("rev").v(this.rev);
            nullablePendingField(w, "photoInfo", this.photoInfo, PhotoInfo.PENDING);
            nullablePendingField(w, "videoInfo", this.videoInfo, VideoInfo.PENDING);
        }

        private static <T extends Dumpable> void nullablePendingField(DumpWriter w, String fieldName, T value, T pendingValue) {
            if (value != null) {
                w.f(fieldName);
                if (value == pendingValue) {
                    w.verbatim("pending");
                } else {
                    w.v((Dumpable) value);
                }
            }
        }

        protected String getTypeName() {
            return "File";
        }

        public boolean isFolder() {
            return DbxEntry.$assertionsDisabled;
        }

        public boolean isFile() {
            return true;
        }

        public Folder asFolder() {
            throw new RuntimeException("not a folder");
        }

        public File asFile() {
            return this;
        }

        public boolean equals(Object o) {
            return (o != null && getClass().equals(o.getClass()) && equals((File) o)) ? true : DbxEntry.$assertionsDisabled;
        }

        public boolean equals(File o) {
            if (partialEquals(o) && this.numBytes == o.numBytes && this.humanSize.equals(o.humanSize) && this.lastModified.equals(o.lastModified) && this.clientMtime.equals(o.clientMtime) && this.rev.equals(o.rev) && LangUtil.nullableEquals(this.photoInfo, o.photoInfo) && LangUtil.nullableEquals(this.videoInfo, o.videoInfo)) {
                return true;
            }
            return DbxEntry.$assertionsDisabled;
        }

        public int hashCode() {
            return (((((((((((partialHashCode() * 31) + ((int) this.numBytes)) * 31) + this.lastModified.hashCode()) * 31) + this.clientMtime.hashCode()) * 31) + this.rev.hashCode()) * 31) + LangUtil.nullableHashCode(this.photoInfo)) * 31) + LangUtil.nullableHashCode(this.videoInfo);
        }
    }

    public static final class Folder extends DbxEntry {
        public static final JsonReader<Folder> Reader = new JsonReader<Folder>() {
            public final Folder read(JsonParser parser) throws IOException, JsonReadException {
                JsonLocation top = parser.getCurrentLocation();
                DbxEntry e = DbxEntry.read(parser, null).entry;
                if (e instanceof Folder) {
                    return (Folder) e;
                }
                throw new JsonReadException("Expecting a file entry, got a folder entry", top);
            }
        };
        public static final long serialVersionUID = 0;

        public Folder(String path, String iconName, boolean mightHaveThumbnail) {
            super(path, iconName, mightHaveThumbnail);
        }

        protected String getTypeName() {
            return "Folder";
        }

        public boolean isFolder() {
            return true;
        }

        public boolean isFile() {
            return DbxEntry.$assertionsDisabled;
        }

        public Folder asFolder() {
            return this;
        }

        public File asFile() {
            throw new RuntimeException("not a file");
        }

        public boolean equals(Object o) {
            return (o != null && getClass().equals(o.getClass()) && equals((Folder) o)) ? true : DbxEntry.$assertionsDisabled;
        }

        public boolean equals(Folder o) {
            if (partialEquals(o)) {
                return true;
            }
            return DbxEntry.$assertionsDisabled;
        }

        public int hashCode() {
            return partialHashCode();
        }
    }

    private static final class PendingReader<T> extends JsonReader<T> {
        private final T pendingValue;
        private final JsonReader<T> reader;

        public PendingReader(JsonReader<T> reader, T pendingValue) {
            this.reader = reader;
            this.pendingValue = pendingValue;
        }

        public static <T> PendingReader<T> mk(JsonReader<T> reader, T pendingValue) {
            return new PendingReader(reader, pendingValue);
        }

        public T read(JsonParser parser) throws IOException, JsonReadException {
            if (parser.getCurrentToken() != JsonToken.VALUE_STRING) {
                return this.reader.read(parser);
            }
            if (parser.getText().equals("pending")) {
                parser.nextToken();
                return this.pendingValue;
            }
            throw new JsonReadException("got a string, but the value wasn't \"pending\"", parser.getTokenLocation());
        }
    }

    public static final class WithChildren extends Dumpable implements Serializable {
        public static final JsonReader<WithChildren> Reader = new JsonReader<WithChildren>() {
            public final WithChildren read(JsonParser parser) throws IOException, JsonReadException {
                WithChildrenC<List<DbxEntry>> c = DbxEntry.read(parser, new ArrayListCollector());
                return new WithChildren(c.entry, c.hash, (List) c.children);
            }
        };
        public static final JsonReader<WithChildren> ReaderMaybeDeleted = new JsonReader<WithChildren>() {
            public final WithChildren read(JsonParser parser) throws IOException, JsonReadException {
                WithChildrenC<List<DbxEntry>> c = DbxEntry.readMaybeDeleted(parser, new ArrayListCollector());
                if (c == null) {
                    return null;
                }
                return new WithChildren(c.entry, c.hash, (List) c.children);
            }
        };
        public static final long serialVersionUID = 0;
        public final List<DbxEntry> children;
        public final DbxEntry entry;
        public final String hash;

        public WithChildren(DbxEntry entry, String hash, List<DbxEntry> children) {
            this.entry = entry;
            this.hash = hash;
            this.children = children;
        }

        public boolean equals(Object o) {
            return (o != null && getClass().equals(o.getClass()) && equals((WithChildren) o)) ? true : DbxEntry.$assertionsDisabled;
        }

        public boolean equals(WithChildren o) {
            if (this.children != null) {
                if (!this.children.equals(o.children)) {
                    return DbxEntry.$assertionsDisabled;
                }
            } else if (o.children != null) {
                return DbxEntry.$assertionsDisabled;
            }
            if (!this.entry.equals(o.entry)) {
                return DbxEntry.$assertionsDisabled;
            }
            if (this.hash != null) {
                if (!this.hash.equals(o.hash)) {
                    return DbxEntry.$assertionsDisabled;
                }
            } else if (o.hash != null) {
                return DbxEntry.$assertionsDisabled;
            }
            return true;
        }

        public int hashCode() {
            int i = DbxEntry.FM_size;
            int hashCode = ((this.entry.hashCode() * 31) + (this.hash != null ? this.hash.hashCode() : DbxEntry.FM_size)) * 31;
            if (this.children != null) {
                i = this.children.hashCode();
            }
            return hashCode + i;
        }

        protected void dumpFields(DumpWriter w) {
            w.v(this.entry);
            w.f("hash").v(this.hash);
            w.f("children").v(this.children);
        }
    }

    public static final class WithChildrenC<C> extends Dumpable implements Serializable {
        public static final long serialVersionUID = 0;
        public final C children;
        public final DbxEntry entry;
        public final String hash;

        public static class Reader<C> extends JsonReader<WithChildrenC<C>> {
            private final Collector<DbxEntry, ? extends C> collector;

            public Reader(Collector<DbxEntry, ? extends C> collector) {
                this.collector = collector;
            }

            public final WithChildrenC<C> read(JsonParser parser) throws IOException, JsonReadException {
                return DbxEntry.read(parser, this.collector);
            }
        }

        public static class ReaderMaybeDeleted<C> extends JsonReader<WithChildrenC<C>> {
            private final Collector<DbxEntry, ? extends C> collector;

            public ReaderMaybeDeleted(Collector<DbxEntry, ? extends C> collector) {
                this.collector = collector;
            }

            public final WithChildrenC<C> read(JsonParser parser) throws IOException, JsonReadException {
                return DbxEntry.readMaybeDeleted(parser, this.collector);
            }
        }

        public WithChildrenC(DbxEntry entry, String hash, C children) {
            this.entry = entry;
            this.hash = hash;
            this.children = children;
        }

        public boolean equals(Object o) {
            return (o != null && getClass().equals(o.getClass()) && equals((WithChildrenC) o)) ? true : DbxEntry.$assertionsDisabled;
        }

        public boolean equals(WithChildrenC<?> o) {
            if (this.children != null) {
                if (!this.children.equals(o.children)) {
                    return DbxEntry.$assertionsDisabled;
                }
            } else if (o.children != null) {
                return DbxEntry.$assertionsDisabled;
            }
            if (!this.entry.equals(o.entry)) {
                return DbxEntry.$assertionsDisabled;
            }
            if (this.hash != null) {
                if (!this.hash.equals(o.hash)) {
                    return DbxEntry.$assertionsDisabled;
                }
            } else if (o.hash != null) {
                return DbxEntry.$assertionsDisabled;
            }
            return true;
        }

        public int hashCode() {
            int i = DbxEntry.FM_size;
            int hashCode = ((this.entry.hashCode() * 31) + (this.hash != null ? this.hash.hashCode() : DbxEntry.FM_size)) * 31;
            if (this.children != null) {
                i = this.children.hashCode();
            }
            return hashCode + i;
        }

        protected void dumpFields(DumpWriter w) {
            w.v(this.entry);
            w.f("hash").v(this.hash);
            if (this.children != null) {
                w.f("children").verbatim(this.children.toString());
            }
        }
    }

    public abstract File asFile();

    public abstract Folder asFolder();

    public abstract boolean isFile();

    public abstract boolean isFolder();

    static {
        Builder b = new Builder();
        b.add(Attachment.ATTACHMENT_SIZE, FM_size);
        b.add("bytes", FM_bytes);
        b.add(BoxMetadataUpdateTask.PATH, FM_path);
        b.add("is_dir", FM_is_dir);
        b.add("is_deleted", FM_is_deleted);
        b.add("rev", FM_rev);
        b.add("thumb_exists", FM_thumb_exists);
        b.add("icon", FM_icon);
        b.add("modified", FM_modified);
        b.add("client_mtime", FM_client_mtime);
        b.add("hash", FM_hash);
        b.add("contents", FM_contents);
        b.add("photo_info", FM_photo_info);
        b.add("video_info", FM_video_info);
        FM = b.build();
    }

    private DbxEntry(String path, String iconName, boolean mightHaveThumbnail) {
        this.name = DbxPathV1.getName(path);
        this.path = path;
        this.iconName = iconName;
        this.mightHaveThumbnail = mightHaveThumbnail;
    }

    protected void dumpFields(DumpWriter w) {
        w.v(this.path);
        w.f("iconName").v(this.iconName);
        w.f("mightHaveThumbnail").v(this.mightHaveThumbnail);
    }

    protected boolean partialEquals(DbxEntry o) {
        if (this.name.equals(o.name) && this.path.equals(o.path) && this.iconName.equals(o.iconName) && this.mightHaveThumbnail == o.mightHaveThumbnail) {
            return true;
        }
        return $assertionsDisabled;
    }

    protected int partialHashCode() {
        return (((((((this.name.hashCode() * 31) + this.path.hashCode()) * 31) + this.iconName.hashCode()) * 31) + this.path.hashCode()) * 31) + (this.mightHaveThumbnail ? FM_bytes : FM_size);
    }

    public static <C> WithChildrenC<C> readMaybeDeleted(JsonParser parser, Collector<DbxEntry, ? extends C> collector) throws IOException, JsonReadException {
        return _read(parser, collector, true);
    }

    public static <C> WithChildrenC<C> read(JsonParser parser, Collector<DbxEntry, ? extends C> collector) throws IOException, JsonReadException {
        WithChildrenC<C> r = _read(parser, collector, $assertionsDisabled);
        if ($assertionsDisabled || r != null) {
            return r;
        }
        throw new AssertionError("@AssumeAssertion(nullness)");
    }

    private static <C> WithChildrenC<C> _read(JsonParser parser, Collector<DbxEntry, ? extends C> collector, boolean allowDeleted) throws IOException, JsonReadException {
        JsonLocation top = JsonReader.expectObjectStart(parser);
        String size = null;
        long bytes = -1;
        String path = null;
        Boolean is_dir = null;
        Boolean is_deleted = null;
        String rev = null;
        Boolean thumb_exists = null;
        String icon = null;
        Date modified = null;
        Date client_mtime = null;
        String hash = null;
        C contents = null;
        PhotoInfo photo_info = null;
        VideoInfo video_info = null;
        while (parser.getCurrentToken() == JsonToken.FIELD_NAME) {
            String fieldName = parser.getCurrentName();
            JsonReader.nextToken(parser);
            int fi = FM.get(fieldName);
            switch (fi) {
                case -1:
                    JsonReader.skipValue(parser);
                    break;
                case FM_size /*0*/:
                    size = (String) JsonReader.StringReader.readField(parser, fieldName, size);
                    break;
                case FM_bytes /*1*/:
                    bytes = JsonReader.readUnsignedLongField(parser, fieldName, bytes);
                    break;
                case FM_path /*2*/:
                    path = (String) JsonReader.StringReader.readField(parser, fieldName, path);
                    break;
                case FM_is_dir /*3*/:
                    is_dir = (Boolean) JsonReader.BooleanReader.readField(parser, fieldName, is_dir);
                    break;
                case FM_is_deleted /*4*/:
                    is_deleted = (Boolean) JsonReader.BooleanReader.readField(parser, fieldName, is_deleted);
                    break;
                case FM_rev /*5*/:
                    rev = (String) JsonReader.StringReader.readField(parser, fieldName, rev);
                    break;
                case FM_thumb_exists /*6*/:
                    thumb_exists = (Boolean) JsonReader.BooleanReader.readField(parser, fieldName, thumb_exists);
                    break;
                case FM_icon /*7*/:
                    icon = (String) JsonReader.StringReader.readField(parser, fieldName, icon);
                    break;
                case FM_modified /*8*/:
                    modified = (Date) JsonDateReader.Dropbox.readField(parser, fieldName, modified);
                    break;
                case FM_client_mtime /*9*/:
                    client_mtime = (Date) JsonDateReader.Dropbox.readField(parser, fieldName, client_mtime);
                    break;
                case FM_hash /*10*/:
                    if (collector != null) {
                        hash = (String) JsonReader.StringReader.readField(parser, fieldName, hash);
                        break;
                    }
                    throw new JsonReadException("not expecting \"hash\" field, since we didn't ask for children", parser.getCurrentLocation());
                case FM_contents /*11*/:
                    if (collector != null) {
                        contents = JsonArrayReader.mk(Reader, collector).readField(parser, fieldName, contents);
                        break;
                    }
                    throw new JsonReadException("not expecting \"contents\" field, since we didn't ask for children", parser.getCurrentLocation());
                case FM_photo_info /*12*/:
                    photo_info = (PhotoInfo) PendingReader.mk(PhotoInfo.Reader, PhotoInfo.PENDING).readField(parser, fieldName, photo_info);
                    break;
                case FM_video_info /*13*/:
                    video_info = (VideoInfo) PendingReader.mk(VideoInfo.Reader, VideoInfo.PENDING).readField(parser, fieldName, video_info);
                    break;
                default:
                    try {
                        throw new AssertionError("bad index: " + fi + ", field = \"" + fieldName + "\"");
                    } catch (JsonReadException ex) {
                        throw ex.addFieldContext(fieldName);
                    }
            }
        }
        JsonReader.expectObjectEnd(parser);
        if (path == null) {
            throw new JsonReadException("missing field \"path\"", top);
        } else if (icon == null) {
            throw new JsonReadException("missing field \"icon\"", top);
        } else {
            DbxEntry e;
            if (is_deleted == null) {
                is_deleted = Boolean.FALSE;
            }
            if (is_dir == null) {
                is_dir = Boolean.FALSE;
            }
            if (thumb_exists == null) {
                thumb_exists = Boolean.FALSE;
            }
            if (is_dir.booleanValue() && !(contents == null && hash == null)) {
                if (hash == null) {
                    throw new JsonReadException("missing \"hash\", when we asked for children", top);
                } else if (contents == null) {
                    throw new JsonReadException("missing \"contents\", when we asked for children", top);
                }
            }
            if (is_dir.booleanValue()) {
                e = new Folder(path, icon, thumb_exists.booleanValue());
            } else if (size == null) {
                throw new JsonReadException("missing \"size\" for a file entry", top);
            } else if (bytes == -1) {
                throw new JsonReadException("missing \"bytes\" for a file entry", top);
            } else if (modified == null) {
                throw new JsonReadException("missing \"modified\" for a file entry", top);
            } else if (client_mtime == null) {
                throw new JsonReadException("missing \"client_mtime\" for a file entry", top);
            } else if (rev == null) {
                throw new JsonReadException("missing \"rev\" for a file entry", top);
            } else {
                e = new File(path, icon, thumb_exists.booleanValue(), bytes, size, modified, client_mtime, rev, photo_info, video_info);
            }
            if (!is_deleted.booleanValue()) {
                return new WithChildrenC(e, hash, contents);
            }
            if (allowDeleted) {
                return null;
            }
            throw new JsonReadException("not expecting \"is_deleted\" entry here", top);
        }
    }
}
