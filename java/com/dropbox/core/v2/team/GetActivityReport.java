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

public class GetActivityReport extends BaseDfbReport {
    protected final List<Long> activeSharedFolders1Day;
    protected final List<Long> activeSharedFolders28Day;
    protected final List<Long> activeSharedFolders7Day;
    protected final List<Long> activeUsers1Day;
    protected final List<Long> activeUsers28Day;
    protected final List<Long> activeUsers7Day;
    protected final List<Long> adds;
    protected final List<Long> deletes;
    protected final List<Long> edits;
    protected final List<Long> sharedLinksCreated;
    protected final List<Long> sharedLinksViewedByNotLoggedIn;
    protected final List<Long> sharedLinksViewedByOutsideUser;
    protected final List<Long> sharedLinksViewedByTeam;
    protected final List<Long> sharedLinksViewedTotal;

    static class Serializer extends StructSerializer<GetActivityReport> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(GetActivityReport value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("start_date");
            StoneSerializers.string().serialize(value.startDate, g);
            g.writeFieldName("adds");
            StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).serialize(value.adds, g);
            g.writeFieldName("edits");
            StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).serialize(value.edits, g);
            g.writeFieldName("deletes");
            StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).serialize(value.deletes, g);
            g.writeFieldName("active_users_28_day");
            StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).serialize(value.activeUsers28Day, g);
            g.writeFieldName("active_users_7_day");
            StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).serialize(value.activeUsers7Day, g);
            g.writeFieldName("active_users_1_day");
            StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).serialize(value.activeUsers1Day, g);
            g.writeFieldName("active_shared_folders_28_day");
            StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).serialize(value.activeSharedFolders28Day, g);
            g.writeFieldName("active_shared_folders_7_day");
            StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).serialize(value.activeSharedFolders7Day, g);
            g.writeFieldName("active_shared_folders_1_day");
            StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).serialize(value.activeSharedFolders1Day, g);
            g.writeFieldName("shared_links_created");
            StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).serialize(value.sharedLinksCreated, g);
            g.writeFieldName("shared_links_viewed_by_team");
            StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).serialize(value.sharedLinksViewedByTeam, g);
            g.writeFieldName("shared_links_viewed_by_outside_user");
            StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).serialize(value.sharedLinksViewedByOutsideUser, g);
            g.writeFieldName("shared_links_viewed_by_not_logged_in");
            StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).serialize(value.sharedLinksViewedByNotLoggedIn, g);
            g.writeFieldName("shared_links_viewed_total");
            StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).serialize(value.sharedLinksViewedTotal, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public GetActivityReport deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                String f_startDate = null;
                List<Long> f_adds = null;
                List<Long> f_edits = null;
                List<Long> f_deletes = null;
                List<Long> f_activeUsers28Day = null;
                List<Long> f_activeUsers7Day = null;
                List<Long> f_activeUsers1Day = null;
                List<Long> f_activeSharedFolders28Day = null;
                List<Long> f_activeSharedFolders7Day = null;
                List<Long> f_activeSharedFolders1Day = null;
                List<Long> f_sharedLinksCreated = null;
                List<Long> f_sharedLinksViewedByTeam = null;
                List<Long> f_sharedLinksViewedByOutsideUser = null;
                List<Long> f_sharedLinksViewedByNotLoggedIn = null;
                List<Long> f_sharedLinksViewedTotal = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("start_date".equals(field)) {
                        f_startDate = (String) StoneSerializers.string().deserialize(p);
                    } else if ("adds".equals(field)) {
                        f_adds = (List) StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).deserialize(p);
                    } else if ("edits".equals(field)) {
                        f_edits = (List) StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).deserialize(p);
                    } else if ("deletes".equals(field)) {
                        f_deletes = (List) StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).deserialize(p);
                    } else if ("active_users_28_day".equals(field)) {
                        f_activeUsers28Day = (List) StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).deserialize(p);
                    } else if ("active_users_7_day".equals(field)) {
                        f_activeUsers7Day = (List) StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).deserialize(p);
                    } else if ("active_users_1_day".equals(field)) {
                        f_activeUsers1Day = (List) StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).deserialize(p);
                    } else if ("active_shared_folders_28_day".equals(field)) {
                        f_activeSharedFolders28Day = (List) StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).deserialize(p);
                    } else if ("active_shared_folders_7_day".equals(field)) {
                        f_activeSharedFolders7Day = (List) StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).deserialize(p);
                    } else if ("active_shared_folders_1_day".equals(field)) {
                        f_activeSharedFolders1Day = (List) StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).deserialize(p);
                    } else if ("shared_links_created".equals(field)) {
                        f_sharedLinksCreated = (List) StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).deserialize(p);
                    } else if ("shared_links_viewed_by_team".equals(field)) {
                        f_sharedLinksViewedByTeam = (List) StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).deserialize(p);
                    } else if ("shared_links_viewed_by_outside_user".equals(field)) {
                        f_sharedLinksViewedByOutsideUser = (List) StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).deserialize(p);
                    } else if ("shared_links_viewed_by_not_logged_in".equals(field)) {
                        f_sharedLinksViewedByNotLoggedIn = (List) StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).deserialize(p);
                    } else if ("shared_links_viewed_total".equals(field)) {
                        f_sharedLinksViewedTotal = (List) StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_startDate == null) {
                    throw new JsonParseException(p, "Required field \"start_date\" missing.");
                } else if (f_adds == null) {
                    throw new JsonParseException(p, "Required field \"adds\" missing.");
                } else if (f_edits == null) {
                    throw new JsonParseException(p, "Required field \"edits\" missing.");
                } else if (f_deletes == null) {
                    throw new JsonParseException(p, "Required field \"deletes\" missing.");
                } else if (f_activeUsers28Day == null) {
                    throw new JsonParseException(p, "Required field \"active_users_28_day\" missing.");
                } else if (f_activeUsers7Day == null) {
                    throw new JsonParseException(p, "Required field \"active_users_7_day\" missing.");
                } else if (f_activeUsers1Day == null) {
                    throw new JsonParseException(p, "Required field \"active_users_1_day\" missing.");
                } else if (f_activeSharedFolders28Day == null) {
                    throw new JsonParseException(p, "Required field \"active_shared_folders_28_day\" missing.");
                } else if (f_activeSharedFolders7Day == null) {
                    throw new JsonParseException(p, "Required field \"active_shared_folders_7_day\" missing.");
                } else if (f_activeSharedFolders1Day == null) {
                    throw new JsonParseException(p, "Required field \"active_shared_folders_1_day\" missing.");
                } else if (f_sharedLinksCreated == null) {
                    throw new JsonParseException(p, "Required field \"shared_links_created\" missing.");
                } else if (f_sharedLinksViewedByTeam == null) {
                    throw new JsonParseException(p, "Required field \"shared_links_viewed_by_team\" missing.");
                } else if (f_sharedLinksViewedByOutsideUser == null) {
                    throw new JsonParseException(p, "Required field \"shared_links_viewed_by_outside_user\" missing.");
                } else if (f_sharedLinksViewedByNotLoggedIn == null) {
                    throw new JsonParseException(p, "Required field \"shared_links_viewed_by_not_logged_in\" missing.");
                } else if (f_sharedLinksViewedTotal == null) {
                    throw new JsonParseException(p, "Required field \"shared_links_viewed_total\" missing.");
                } else {
                    GetActivityReport value = new GetActivityReport(f_startDate, f_adds, f_edits, f_deletes, f_activeUsers28Day, f_activeUsers7Day, f_activeUsers1Day, f_activeSharedFolders28Day, f_activeSharedFolders7Day, f_activeSharedFolders1Day, f_sharedLinksCreated, f_sharedLinksViewedByTeam, f_sharedLinksViewedByOutsideUser, f_sharedLinksViewedByNotLoggedIn, f_sharedLinksViewedTotal);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public GetActivityReport(String startDate, List<Long> adds, List<Long> edits, List<Long> deletes, List<Long> activeUsers28Day, List<Long> activeUsers7Day, List<Long> activeUsers1Day, List<Long> activeSharedFolders28Day, List<Long> activeSharedFolders7Day, List<Long> activeSharedFolders1Day, List<Long> sharedLinksCreated, List<Long> sharedLinksViewedByTeam, List<Long> sharedLinksViewedByOutsideUser, List<Long> sharedLinksViewedByNotLoggedIn, List<Long> sharedLinksViewedTotal) {
        super(startDate);
        if (adds == null) {
            throw new IllegalArgumentException("Required value for 'adds' is null");
        }
        for (Long x : adds) {
            if (x == null) {
                throw new IllegalArgumentException("An item in list 'adds' is null");
            }
        }
        this.adds = adds;
        if (edits == null) {
            throw new IllegalArgumentException("Required value for 'edits' is null");
        }
        for (Long x2 : edits) {
            if (x2 == null) {
                throw new IllegalArgumentException("An item in list 'edits' is null");
            }
        }
        this.edits = edits;
        if (deletes == null) {
            throw new IllegalArgumentException("Required value for 'deletes' is null");
        }
        for (Long x22 : deletes) {
            if (x22 == null) {
                throw new IllegalArgumentException("An item in list 'deletes' is null");
            }
        }
        this.deletes = deletes;
        if (activeUsers28Day == null) {
            throw new IllegalArgumentException("Required value for 'activeUsers28Day' is null");
        }
        for (Long x222 : activeUsers28Day) {
            if (x222 == null) {
                throw new IllegalArgumentException("An item in list 'activeUsers28Day' is null");
            }
        }
        this.activeUsers28Day = activeUsers28Day;
        if (activeUsers7Day == null) {
            throw new IllegalArgumentException("Required value for 'activeUsers7Day' is null");
        }
        for (Long x2222 : activeUsers7Day) {
            if (x2222 == null) {
                throw new IllegalArgumentException("An item in list 'activeUsers7Day' is null");
            }
        }
        this.activeUsers7Day = activeUsers7Day;
        if (activeUsers1Day == null) {
            throw new IllegalArgumentException("Required value for 'activeUsers1Day' is null");
        }
        for (Long x22222 : activeUsers1Day) {
            if (x22222 == null) {
                throw new IllegalArgumentException("An item in list 'activeUsers1Day' is null");
            }
        }
        this.activeUsers1Day = activeUsers1Day;
        if (activeSharedFolders28Day == null) {
            throw new IllegalArgumentException("Required value for 'activeSharedFolders28Day' is null");
        }
        for (Long x222222 : activeSharedFolders28Day) {
            if (x222222 == null) {
                throw new IllegalArgumentException("An item in list 'activeSharedFolders28Day' is null");
            }
        }
        this.activeSharedFolders28Day = activeSharedFolders28Day;
        if (activeSharedFolders7Day == null) {
            throw new IllegalArgumentException("Required value for 'activeSharedFolders7Day' is null");
        }
        for (Long x2222222 : activeSharedFolders7Day) {
            if (x2222222 == null) {
                throw new IllegalArgumentException("An item in list 'activeSharedFolders7Day' is null");
            }
        }
        this.activeSharedFolders7Day = activeSharedFolders7Day;
        if (activeSharedFolders1Day == null) {
            throw new IllegalArgumentException("Required value for 'activeSharedFolders1Day' is null");
        }
        for (Long x22222222 : activeSharedFolders1Day) {
            if (x22222222 == null) {
                throw new IllegalArgumentException("An item in list 'activeSharedFolders1Day' is null");
            }
        }
        this.activeSharedFolders1Day = activeSharedFolders1Day;
        if (sharedLinksCreated == null) {
            throw new IllegalArgumentException("Required value for 'sharedLinksCreated' is null");
        }
        for (Long x222222222 : sharedLinksCreated) {
            if (x222222222 == null) {
                throw new IllegalArgumentException("An item in list 'sharedLinksCreated' is null");
            }
        }
        this.sharedLinksCreated = sharedLinksCreated;
        if (sharedLinksViewedByTeam == null) {
            throw new IllegalArgumentException("Required value for 'sharedLinksViewedByTeam' is null");
        }
        for (Long x2222222222 : sharedLinksViewedByTeam) {
            if (x2222222222 == null) {
                throw new IllegalArgumentException("An item in list 'sharedLinksViewedByTeam' is null");
            }
        }
        this.sharedLinksViewedByTeam = sharedLinksViewedByTeam;
        if (sharedLinksViewedByOutsideUser == null) {
            throw new IllegalArgumentException("Required value for 'sharedLinksViewedByOutsideUser' is null");
        }
        for (Long x22222222222 : sharedLinksViewedByOutsideUser) {
            if (x22222222222 == null) {
                throw new IllegalArgumentException("An item in list 'sharedLinksViewedByOutsideUser' is null");
            }
        }
        this.sharedLinksViewedByOutsideUser = sharedLinksViewedByOutsideUser;
        if (sharedLinksViewedByNotLoggedIn == null) {
            throw new IllegalArgumentException("Required value for 'sharedLinksViewedByNotLoggedIn' is null");
        }
        for (Long x222222222222 : sharedLinksViewedByNotLoggedIn) {
            if (x222222222222 == null) {
                throw new IllegalArgumentException("An item in list 'sharedLinksViewedByNotLoggedIn' is null");
            }
        }
        this.sharedLinksViewedByNotLoggedIn = sharedLinksViewedByNotLoggedIn;
        if (sharedLinksViewedTotal == null) {
            throw new IllegalArgumentException("Required value for 'sharedLinksViewedTotal' is null");
        }
        for (Long x2222222222222 : sharedLinksViewedTotal) {
            if (x2222222222222 == null) {
                throw new IllegalArgumentException("An item in list 'sharedLinksViewedTotal' is null");
            }
        }
        this.sharedLinksViewedTotal = sharedLinksViewedTotal;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public List<Long> getAdds() {
        return this.adds;
    }

    public List<Long> getEdits() {
        return this.edits;
    }

    public List<Long> getDeletes() {
        return this.deletes;
    }

    public List<Long> getActiveUsers28Day() {
        return this.activeUsers28Day;
    }

    public List<Long> getActiveUsers7Day() {
        return this.activeUsers7Day;
    }

    public List<Long> getActiveUsers1Day() {
        return this.activeUsers1Day;
    }

    public List<Long> getActiveSharedFolders28Day() {
        return this.activeSharedFolders28Day;
    }

    public List<Long> getActiveSharedFolders7Day() {
        return this.activeSharedFolders7Day;
    }

    public List<Long> getActiveSharedFolders1Day() {
        return this.activeSharedFolders1Day;
    }

    public List<Long> getSharedLinksCreated() {
        return this.sharedLinksCreated;
    }

    public List<Long> getSharedLinksViewedByTeam() {
        return this.sharedLinksViewedByTeam;
    }

    public List<Long> getSharedLinksViewedByOutsideUser() {
        return this.sharedLinksViewedByOutsideUser;
    }

    public List<Long> getSharedLinksViewedByNotLoggedIn() {
        return this.sharedLinksViewedByNotLoggedIn;
    }

    public List<Long> getSharedLinksViewedTotal() {
        return this.sharedLinksViewedTotal;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.adds, this.edits, this.deletes, this.activeUsers28Day, this.activeUsers7Day, this.activeUsers1Day, this.activeSharedFolders28Day, this.activeSharedFolders7Day, this.activeSharedFolders1Day, this.sharedLinksCreated, this.sharedLinksViewedByTeam, this.sharedLinksViewedByOutsideUser, this.sharedLinksViewedByNotLoggedIn, this.sharedLinksViewedTotal}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        GetActivityReport other = (GetActivityReport) obj;
        if ((this.startDate == other.startDate || this.startDate.equals(other.startDate)) && ((this.adds == other.adds || this.adds.equals(other.adds)) && ((this.edits == other.edits || this.edits.equals(other.edits)) && ((this.deletes == other.deletes || this.deletes.equals(other.deletes)) && ((this.activeUsers28Day == other.activeUsers28Day || this.activeUsers28Day.equals(other.activeUsers28Day)) && ((this.activeUsers7Day == other.activeUsers7Day || this.activeUsers7Day.equals(other.activeUsers7Day)) && ((this.activeUsers1Day == other.activeUsers1Day || this.activeUsers1Day.equals(other.activeUsers1Day)) && ((this.activeSharedFolders28Day == other.activeSharedFolders28Day || this.activeSharedFolders28Day.equals(other.activeSharedFolders28Day)) && ((this.activeSharedFolders7Day == other.activeSharedFolders7Day || this.activeSharedFolders7Day.equals(other.activeSharedFolders7Day)) && ((this.activeSharedFolders1Day == other.activeSharedFolders1Day || this.activeSharedFolders1Day.equals(other.activeSharedFolders1Day)) && ((this.sharedLinksCreated == other.sharedLinksCreated || this.sharedLinksCreated.equals(other.sharedLinksCreated)) && ((this.sharedLinksViewedByTeam == other.sharedLinksViewedByTeam || this.sharedLinksViewedByTeam.equals(other.sharedLinksViewedByTeam)) && ((this.sharedLinksViewedByOutsideUser == other.sharedLinksViewedByOutsideUser || this.sharedLinksViewedByOutsideUser.equals(other.sharedLinksViewedByOutsideUser)) && ((this.sharedLinksViewedByNotLoggedIn == other.sharedLinksViewedByNotLoggedIn || this.sharedLinksViewedByNotLoggedIn.equals(other.sharedLinksViewedByNotLoggedIn)) && (this.sharedLinksViewedTotal == other.sharedLinksViewedTotal || this.sharedLinksViewedTotal.equals(other.sharedLinksViewedTotal)))))))))))))))) {
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
