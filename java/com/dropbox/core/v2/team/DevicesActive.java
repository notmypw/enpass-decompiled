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

public class DevicesActive {
    protected final List<Long> android;
    protected final List<Long> ios;
    protected final List<Long> linux;
    protected final List<Long> macos;
    protected final List<Long> other;
    protected final List<Long> total;
    protected final List<Long> windows;

    static class Serializer extends StructSerializer<DevicesActive> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(DevicesActive value, JsonGenerator g, boolean collapse) throws IOException, JsonGenerationException {
            if (!collapse) {
                g.writeStartObject();
            }
            g.writeFieldName("windows");
            StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).serialize(value.windows, g);
            g.writeFieldName("macos");
            StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).serialize(value.macos, g);
            g.writeFieldName("linux");
            StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).serialize(value.linux, g);
            g.writeFieldName("ios");
            StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).serialize(value.ios, g);
            g.writeFieldName("android");
            StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).serialize(value.android, g);
            g.writeFieldName("other");
            StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).serialize(value.other, g);
            g.writeFieldName("total");
            StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).serialize(value.total, g);
            if (!collapse) {
                g.writeEndObject();
            }
        }

        public DevicesActive deserialize(JsonParser p, boolean collapsed) throws IOException, JsonParseException {
            String tag = null;
            if (!collapsed) {
                StoneSerializer.expectStartObject(p);
                tag = CompositeSerializer.readTag(p);
            }
            if (tag == null) {
                List<Long> f_windows = null;
                List<Long> f_macos = null;
                List<Long> f_linux = null;
                List<Long> f_ios = null;
                List<Long> f_android = null;
                List<Long> f_other = null;
                List<Long> f_total = null;
                while (p.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String field = p.getCurrentName();
                    p.nextToken();
                    if ("windows".equals(field)) {
                        f_windows = (List) StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).deserialize(p);
                    } else if ("macos".equals(field)) {
                        f_macos = (List) StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).deserialize(p);
                    } else if ("linux".equals(field)) {
                        f_linux = (List) StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).deserialize(p);
                    } else if ("ios".equals(field)) {
                        f_ios = (List) StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).deserialize(p);
                    } else if ("android".equals(field)) {
                        f_android = (List) StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).deserialize(p);
                    } else if ("other".equals(field)) {
                        f_other = (List) StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).deserialize(p);
                    } else if ("total".equals(field)) {
                        f_total = (List) StoneSerializers.list(StoneSerializers.nullable(StoneSerializers.uInt64())).deserialize(p);
                    } else {
                        StoneSerializer.skipValue(p);
                    }
                }
                if (f_windows == null) {
                    throw new JsonParseException(p, "Required field \"windows\" missing.");
                } else if (f_macos == null) {
                    throw new JsonParseException(p, "Required field \"macos\" missing.");
                } else if (f_linux == null) {
                    throw new JsonParseException(p, "Required field \"linux\" missing.");
                } else if (f_ios == null) {
                    throw new JsonParseException(p, "Required field \"ios\" missing.");
                } else if (f_android == null) {
                    throw new JsonParseException(p, "Required field \"android\" missing.");
                } else if (f_other == null) {
                    throw new JsonParseException(p, "Required field \"other\" missing.");
                } else if (f_total == null) {
                    throw new JsonParseException(p, "Required field \"total\" missing.");
                } else {
                    DevicesActive value = new DevicesActive(f_windows, f_macos, f_linux, f_ios, f_android, f_other, f_total);
                    if (!collapsed) {
                        StoneSerializer.expectEndObject(p);
                    }
                    return value;
                }
            }
            throw new JsonParseException(p, "No subtype found that matches tag: \"" + tag + "\"");
        }
    }

    public DevicesActive(List<Long> windows, List<Long> macos, List<Long> linux, List<Long> ios, List<Long> android, List<Long> other, List<Long> total) {
        if (windows == null) {
            throw new IllegalArgumentException("Required value for 'windows' is null");
        }
        for (Long x : windows) {
            if (x == null) {
                throw new IllegalArgumentException("An item in list 'windows' is null");
            }
        }
        this.windows = windows;
        if (macos == null) {
            throw new IllegalArgumentException("Required value for 'macos' is null");
        }
        for (Long x2 : macos) {
            if (x2 == null) {
                throw new IllegalArgumentException("An item in list 'macos' is null");
            }
        }
        this.macos = macos;
        if (linux == null) {
            throw new IllegalArgumentException("Required value for 'linux' is null");
        }
        for (Long x22 : linux) {
            if (x22 == null) {
                throw new IllegalArgumentException("An item in list 'linux' is null");
            }
        }
        this.linux = linux;
        if (ios == null) {
            throw new IllegalArgumentException("Required value for 'ios' is null");
        }
        for (Long x222 : ios) {
            if (x222 == null) {
                throw new IllegalArgumentException("An item in list 'ios' is null");
            }
        }
        this.ios = ios;
        if (android == null) {
            throw new IllegalArgumentException("Required value for 'android' is null");
        }
        for (Long x2222 : android) {
            if (x2222 == null) {
                throw new IllegalArgumentException("An item in list 'android' is null");
            }
        }
        this.android = android;
        if (other == null) {
            throw new IllegalArgumentException("Required value for 'other' is null");
        }
        for (Long x22222 : other) {
            if (x22222 == null) {
                throw new IllegalArgumentException("An item in list 'other' is null");
            }
        }
        this.other = other;
        if (total == null) {
            throw new IllegalArgumentException("Required value for 'total' is null");
        }
        for (Long x222222 : total) {
            if (x222222 == null) {
                throw new IllegalArgumentException("An item in list 'total' is null");
            }
        }
        this.total = total;
    }

    public List<Long> getWindows() {
        return this.windows;
    }

    public List<Long> getMacos() {
        return this.macos;
    }

    public List<Long> getLinux() {
        return this.linux;
    }

    public List<Long> getIos() {
        return this.ios;
    }

    public List<Long> getAndroid() {
        return this.android;
    }

    public List<Long> getOther() {
        return this.other;
    }

    public List<Long> getTotal() {
        return this.total;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.windows, this.macos, this.linux, this.ios, this.android, this.other, this.total});
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        DevicesActive other = (DevicesActive) obj;
        if ((this.windows == other.windows || this.windows.equals(other.windows)) && ((this.macos == other.macos || this.macos.equals(other.macos)) && ((this.linux == other.linux || this.linux.equals(other.linux)) && ((this.ios == other.ios || this.ios.equals(other.ios)) && ((this.android == other.android || this.android.equals(other.android)) && ((this.other == other.other || this.other.equals(other.other)) && (this.total == other.total || this.total.equals(other.total)))))))) {
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
