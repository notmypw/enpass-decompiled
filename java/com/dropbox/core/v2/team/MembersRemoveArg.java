package com.dropbox.core.v2.team;

import com.box.androidsdk.content.models.BoxUser;
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

class MembersRemoveArg extends MembersDeactivateArg {
    protected final boolean keepAccount;
    protected final UserSelectorArg transferAdminId;
    protected final UserSelectorArg transferDestId;

    public static class Builder {
        protected boolean keepAccount;
        protected UserSelectorArg transferAdminId;
        protected UserSelectorArg transferDestId;
        protected final UserSelectorArg user;
        protected boolean wipeData;

        protected Builder(UserSelectorArg user) {
            if (user == null) {
                throw new IllegalArgumentException("Required value for 'user' is null");
            }
            this.user = user;
            this.wipeData = true;
            this.transferDestId = null;
            this.transferAdminId = null;
            this.keepAccount = false;
        }

        public Builder withWipeData(Boolean wipeData) {
            if (wipeData != null) {
                this.wipeData = wipeData.booleanValue();
            } else {
                this.wipeData = true;
            }
            return this;
        }

        public Builder withTransferDestId(UserSelectorArg transferDestId) {
            this.transferDestId = transferDestId;
            return this;
        }

        public Builder withTransferAdminId(UserSelectorArg transferAdminId) {
            this.transferAdminId = transferAdminId;
            return this;
        }

        public Builder withKeepAccount(Boolean keepAccount) {
            if (keepAccount != null) {
                this.keepAccount = keepAccount.booleanValue();
            } else {
                this.keepAccount = false;
            }
            return this;
        }

        public MembersRemoveArg build() {
            return new MembersRemoveArg(this.user, this.wipeData, this.transferDestId, this.transferAdminId, this.keepAccount);
        }
    }

    static class Serializer extends StructSerializer<MembersRemoveArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(MembersRemoveArg value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName(BoxUser.TYPE);
            Serializer.INSTANCE.serialize(value.user, g);
            g.writeFieldName("wipe_data");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.wipeData), g);
            if (value.transferDestId != null) {
                g.writeFieldName("transfer_dest_id");
                StoneSerializers.nullable(Serializer.INSTANCE).serialize(value.transferDestId, g);
            }
            if (value.transferAdminId != null) {
                g.writeFieldName("transfer_admin_id");
                StoneSerializers.nullable(Serializer.INSTANCE).serialize(value.transferAdminId, g);
            }
            g.writeFieldName("keep_account");
            StoneSerializers.boolean_().serialize(Boolean.valueOf(value.keepAccount), g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public MembersRemoveArg deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                UserSelectorArg f_user = null;
                Boolean f_wipeData = Boolean.valueOf(true);
                UserSelectorArg f_transferDestId = null;
                UserSelectorArg f_transferAdminId = null;
                Boolean f_keepAccount = Boolean.valueOf(false);
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if (BoxUser.TYPE.equals(field)) {
                        f_user = Serializer.INSTANCE.deserialize(p);
                    } else if ("wipe_data".equals(field)) {
                        f_wipeData = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else if ("transfer_dest_id".equals(field)) {
                        f_transferDestId = (UserSelectorArg) StoneSerializers.nullable(Serializer.INSTANCE).deserialize(p);
                    } else if ("transfer_admin_id".equals(field)) {
                        f_transferAdminId = (UserSelectorArg) StoneSerializers.nullable(Serializer.INSTANCE).deserialize(p);
                    } else if ("keep_account".equals(field)) {
                        f_keepAccount = (Boolean) StoneSerializers.boolean_().deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_user == null) {
                    throw new JsonParseException(p, "Required field \"user\" missing.");
                }
                MembersRemoveArg value = new MembersRemoveArg(f_user, f_wipeData.booleanValue(), f_transferDestId, f_transferAdminId, f_keepAccount.booleanValue());
                if (!collapsed) {
                    StoneSerializer.expectEndObject(p);
                }
                return value;
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public MembersRemoveArg(UserSelectorArg user, boolean wipeData, UserSelectorArg transferDestId, UserSelectorArg transferAdminId, boolean keepAccount) {
        super(user, wipeData);
        this.transferDestId = transferDestId;
        this.transferAdminId = transferAdminId;
        this.keepAccount = keepAccount;
    }

    public MembersRemoveArg(UserSelectorArg user) {
        this(user, true, null, null, false);
    }

    public UserSelectorArg getUser() {
        return this.user;
    }

    public boolean getWipeData() {
        return this.wipeData;
    }

    public UserSelectorArg getTransferDestId() {
        return this.transferDestId;
    }

    public UserSelectorArg getTransferAdminId() {
        return this.transferAdminId;
    }

    public boolean getKeepAccount() {
        return this.keepAccount;
    }

    public static Builder newBuilder(UserSelectorArg user) {
        return new Builder(user);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.transferDestId, this.transferAdminId, Boolean.valueOf(this.keepAccount)}) + (super.hashCode() * 31);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        MembersRemoveArg other = (MembersRemoveArg) obj;
        if ((this.user == other.user || this.user.equals(other.user)) && this.wipeData == other.wipeData && ((this.transferDestId == other.transferDestId || (this.transferDestId != null && this.transferDestId.equals(other.transferDestId))) && ((this.transferAdminId == other.transferAdminId || (this.transferAdminId != null && this.transferAdminId.equals(other.transferAdminId))) && this.keepAccount == other.keepAccount))) {
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
