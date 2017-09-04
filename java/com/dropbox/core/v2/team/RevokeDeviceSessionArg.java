package com.dropbox.core.v2.team;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.UnionSerializer;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Arrays;
import net.sqlcipher.database.SQLiteDatabase;

public final class RevokeDeviceSessionArg {
    private final Tag _tag;
    private final RevokeDesktopClientArg desktopClientValue;
    private final DeviceSessionArg mobileClientValue;
    private final DeviceSessionArg webSessionValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$team$RevokeDeviceSessionArg$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$team$RevokeDeviceSessionArg$Tag[Tag.WEB_SESSION.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$RevokeDeviceSessionArg$Tag[Tag.DESKTOP_CLIENT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$team$RevokeDeviceSessionArg$Tag[Tag.MOBILE_CLIENT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    static class Serializer extends UnionSerializer<RevokeDeviceSessionArg> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(RevokeDeviceSessionArg value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$RevokeDeviceSessionArg$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeStartObject();
                    writeTag("web_session", g);
                    Serializer.INSTANCE.serialize(value.webSessionValue, g, true);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeStartObject();
                    writeTag("desktop_client", g);
                    Serializer.INSTANCE.serialize(value.desktopClientValue, g, true);
                    g.writeEndObject();
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeStartObject();
                    writeTag("mobile_client", g);
                    Serializer.INSTANCE.serialize(value.mobileClientValue, g, true);
                    g.writeEndObject();
                    return;
                default:
                    throw new IllegalArgumentException("Unrecognized tag: " + value.tag());
            }
        }

        public RevokeDeviceSessionArg deserialize(JsonParser p) throws IOException, JsonParseException {
            boolean collapsed;
            String tag;
            if (p.getCurrentToken() == JsonToken.VALUE_STRING) {
                collapsed = true;
                tag = StoneSerializer.getStringValue(p);
                p.nextToken();
            } else {
                collapsed = false;
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                throw new JsonParseException(p, "Required field missing: .tag");
            }
            RevokeDeviceSessionArg value;
            if ("web_session".equals(tag)) {
                value = RevokeDeviceSessionArg.webSession(Serializer.INSTANCE.deserialize(p, true));
            } else if ("desktop_client".equals(tag)) {
                value = RevokeDeviceSessionArg.desktopClient(Serializer.INSTANCE.deserialize(p, true));
            } else if ("mobile_client".equals(tag)) {
                value = RevokeDeviceSessionArg.mobileClient(Serializer.INSTANCE.deserialize(p, true));
            } else {
                throw new JsonParseException(p, "Unknown tag: " + tag);
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }

    public enum Tag {
        WEB_SESSION,
        DESKTOP_CLIENT,
        MOBILE_CLIENT
    }

    private RevokeDeviceSessionArg(Tag _tag, DeviceSessionArg webSessionValue, RevokeDesktopClientArg desktopClientValue, DeviceSessionArg mobileClientValue) {
        this._tag = _tag;
        this.webSessionValue = webSessionValue;
        this.desktopClientValue = desktopClientValue;
        this.mobileClientValue = mobileClientValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isWebSession() {
        return this._tag == Tag.WEB_SESSION;
    }

    public static RevokeDeviceSessionArg webSession(DeviceSessionArg value) {
        if (value != null) {
            return new RevokeDeviceSessionArg(Tag.WEB_SESSION, value, null, null);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public DeviceSessionArg getWebSessionValue() {
        if (this._tag == Tag.WEB_SESSION) {
            return this.webSessionValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.WEB_SESSION, but was Tag." + this._tag.name());
    }

    public boolean isDesktopClient() {
        return this._tag == Tag.DESKTOP_CLIENT;
    }

    public static RevokeDeviceSessionArg desktopClient(RevokeDesktopClientArg value) {
        if (value != null) {
            return new RevokeDeviceSessionArg(Tag.DESKTOP_CLIENT, null, value, null);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public RevokeDesktopClientArg getDesktopClientValue() {
        if (this._tag == Tag.DESKTOP_CLIENT) {
            return this.desktopClientValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.DESKTOP_CLIENT, but was Tag." + this._tag.name());
    }

    public boolean isMobileClient() {
        return this._tag == Tag.MOBILE_CLIENT;
    }

    public static RevokeDeviceSessionArg mobileClient(DeviceSessionArg value) {
        if (value != null) {
            return new RevokeDeviceSessionArg(Tag.MOBILE_CLIENT, null, null, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public DeviceSessionArg getMobileClientValue() {
        if (this._tag == Tag.MOBILE_CLIENT) {
            return this.mobileClientValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.MOBILE_CLIENT, but was Tag." + this._tag.name());
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.webSessionValue, this.desktopClientValue, this.mobileClientValue});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RevokeDeviceSessionArg)) {
            return false;
        }
        RevokeDeviceSessionArg other = (RevokeDeviceSessionArg) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$team$RevokeDeviceSessionArg$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
                if (this.webSessionValue == other.webSessionValue || this.webSessionValue.equals(other.webSessionValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                if (this.desktopClientValue == other.desktopClientValue || this.desktopClientValue.equals(other.desktopClientValue)) {
                    return true;
                }
                return false;
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                if (this.mobileClientValue == other.mobileClientValue || this.mobileClientValue.equals(other.mobileClientValue)) {
                    return true;
                }
                return false;
            default:
                return false;
        }
    }

    public String toString() {
        return Serializer.INSTANCE.serialize((Object) this, false);
    }

    public String toStringMultiline() {
        return Serializer.INSTANCE.serialize((Object) this, true);
    }
}
