package com.dropbox.core.v2.team;

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

public class GetStorageReport extends BaseDfbReport {
    protected final List<List<StorageBucket>> memberStorageMap;
    protected final List<Long> sharedFolders;
    protected final List<Long> sharedUsage;
    protected final List<Long> totalUsage;
    protected final List<Long> unsharedUsage;

    static class Serializer extends StructSerializer<GetStorageReport> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(GetStorageReport value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("start_date");
            StoneSerializers.string().serialize(value.startDate, g);
            g.writeFieldName("total_usage");
            StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).serialize(value.totalUsage, g);
            g.writeFieldName("shared_usage");
            StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).serialize(value.sharedUsage, g);
            g.writeFieldName("unshared_usage");
            StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).serialize(value.unsharedUsage, g);
            g.writeFieldName("shared_folders");
            StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).serialize(value.sharedFolders, g);
            g.writeFieldName("member_storage_map");
            StoneSerializers.list(StoneSerializers.list(Serializer.INSTANCE)).serialize(value.memberStorageMap, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public GetStorageReport deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_startDate = null;
                List<Long> f_totalUsage = null;
                List<Long> f_sharedUsage = null;
                List<Long> f_unsharedUsage = null;
                List<Long> f_sharedFolders = null;
                List<List<StorageBucket>> f_memberStorageMap = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("start_date".equals(field)) {
                        f_startDate = (String) StoneSerializers.string().deserialize(p);
                    } else if ("total_usage".equals(field)) {
                        f_totalUsage = (List) StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).deserialize(p);
                    } else if ("shared_usage".equals(field)) {
                        f_sharedUsage = (List) StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).deserialize(p);
                    } else if ("unshared_usage".equals(field)) {
                        f_unsharedUsage = (List) StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).deserialize(p);
                    } else if ("shared_folders".equals(field)) {
                        f_sharedFolders = (List) StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).deserialize(p);
                    } else if ("member_storage_map".equals(field)) {
                        f_memberStorageMap = (List) StoneSerializers.list(StoneSerializers.list(Serializer.INSTANCE)).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_startDate == null) {
                    throw new JsonParseException(p, "Required field \"start_date\" missing.");
                } else if (f_totalUsage == null) {
                    throw new JsonParseException(p, "Required field \"total_usage\" missing.");
                } else if (f_sharedUsage == null) {
                    throw new JsonParseException(p, "Required field \"shared_usage\" missing.");
                } else if (f_unsharedUsage == null) {
                    throw new JsonParseException(p, "Required field \"unshared_usage\" missing.");
                } else if (f_sharedFolders == null) {
                    throw new JsonParseException(p, "Required field \"shared_folders\" missing.");
                } else if (f_memberStorageMap == null) {
                    throw new JsonParseException(p, "Required field \"member_storage_map\" missing.");
                } else {
                    GetStorageReport value = new GetStorageReport(f_startDate, f_totalUsage, f_sharedUsage, f_unsharedUsage, f_sharedFolders, f_memberStorageMap);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public GetStorageReport(String startDate, List<Long> totalUsage, List<Long> sharedUsage, List<Long> unsharedUsage, List<Long> sharedFolders, List<List<StorageBucket>> memberStorageMap) {
        super(startDate);
        if (totalUsage == null) {
            throw new IllegalArgumentException("Required value for 'totalUsage' is null");
        }
        for (Long x : totalUsage) {
            if (x == null) {
                throw new IllegalArgumentException("An item in list 'totalUsage' is null");
            }
        }
        this.totalUsage = totalUsage;
        if (sharedUsage == null) {
            throw new IllegalArgumentException("Required value for 'sharedUsage' is null");
        }
        for (Long x2 : sharedUsage) {
            if (x2 == null) {
                throw new IllegalArgumentException("An item in list 'sharedUsage' is null");
            }
        }
        this.sharedUsage = sharedUsage;
        if (unsharedUsage == null) {
            throw new IllegalArgumentException("Required value for 'unsharedUsage' is null");
        }
        for (Long x22 : unsharedUsage) {
            if (x22 == null) {
                throw new IllegalArgumentException("An item in list 'unsharedUsage' is null");
            }
        }
        this.unsharedUsage = unsharedUsage;
        if (sharedFolders == null) {
            throw new IllegalArgumentException("Required value for 'sharedFolders' is null");
        }
        for (Long x222 : sharedFolders) {
            if (x222 == null) {
                throw new IllegalArgumentException("An item in list 'sharedFolders' is null");
            }
        }
        this.sharedFolders = sharedFolders;
        if (memberStorageMap == null) {
            throw new IllegalArgumentException("Required value for 'memberStorageMap' is null");
        }
        for (List<StorageBucket> x3 : memberStorageMap) {
            if (x3 == null) {
                throw new IllegalArgumentException("An item in list 'memberStorageMap' is null");
            }
            for (StorageBucket x1 : x3) {
                if (x1 == null) {
                    throw new IllegalArgumentException("An item in listan item in list 'memberStorageMap' is null");
                }
            }
        }
        this.memberStorageMap = memberStorageMap;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public List<Long> getTotalUsage() {
        return this.totalUsage;
    }

    public List<Long> getSharedUsage() {
        return this.sharedUsage;
    }

    public List<Long> getUnsharedUsage() {
        return this.unsharedUsage;
    }

    public List<Long> getSharedFolders() {
        return this.sharedFolders;
    }

    public List<List<StorageBucket>> getMemberStorageMap() {
        return this.memberStorageMap;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.totalUsage, this.sharedUsage, this.unsharedUsage, this.sharedFolders, this.memberStorageMap}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        GetStorageReport other = (GetStorageReport) obj;
        if ((this.startDate == other.startDate || this.startDate.equals(other.startDate)) && ((this.totalUsage == other.totalUsage || this.totalUsage.equals(other.totalUsage)) && ((this.sharedUsage == other.sharedUsage || this.sharedUsage.equals(other.sharedUsage)) && ((this.unsharedUsage == other.unsharedUsage || this.unsharedUsage.equals(other.unsharedUsage)) && ((this.sharedFolders == other.sharedFolders || this.sharedFolders.equals(other.sharedFolders)) && (this.memberStorageMap == other.memberStorageMap || this.memberStorageMap.equals(other.memberStorageMap))))))) {
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
