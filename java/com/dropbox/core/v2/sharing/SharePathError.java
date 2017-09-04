package com.dropbox.core.v2.sharing;

import com.dropbox.core.stone.CompositeSerializer;
import com.dropbox.core.stone.StoneSerializer;
import com.dropbox.core.stone.UnionSerializer;
import com.dropbox.core.v2.files.PathRootError;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.github.clans.fab.R;
import com.samsung.android.sdk.pass.SpassFingerprint;
import in.sinew.enpass.IRemoteStorage;
import java.io.IOException;
import java.util.Arrays;
import net.sqlcipher.database.SQLiteDatabase;

public final class SharePathError {
    public static final SharePathError CONTAINS_APP_FOLDER = new SharePathError(Tag.CONTAINS_APP_FOLDER, null, null);
    public static final SharePathError CONTAINS_SHARED_FOLDER = new SharePathError(Tag.CONTAINS_SHARED_FOLDER, null, null);
    public static final SharePathError CONTAINS_TEAM_FOLDER = new SharePathError(Tag.CONTAINS_TEAM_FOLDER, null, null);
    public static final SharePathError INSIDE_APP_FOLDER = new SharePathError(Tag.INSIDE_APP_FOLDER, null, null);
    public static final SharePathError INSIDE_OSX_PACKAGE = new SharePathError(Tag.INSIDE_OSX_PACKAGE, null, null);
    public static final SharePathError INSIDE_PUBLIC_FOLDER = new SharePathError(Tag.INSIDE_PUBLIC_FOLDER, null, null);
    public static final SharePathError INSIDE_SHARED_FOLDER = new SharePathError(Tag.INSIDE_SHARED_FOLDER, null, null);
    public static final SharePathError INVALID_PATH = new SharePathError(Tag.INVALID_PATH, null, null);
    public static final SharePathError IS_APP_FOLDER = new SharePathError(Tag.IS_APP_FOLDER, null, null);
    public static final SharePathError IS_FILE = new SharePathError(Tag.IS_FILE, null, null);
    public static final SharePathError IS_OSX_PACKAGE = new SharePathError(Tag.IS_OSX_PACKAGE, null, null);
    public static final SharePathError IS_PUBLIC_FOLDER = new SharePathError(Tag.IS_PUBLIC_FOLDER, null, null);
    public static final SharePathError OTHER = new SharePathError(Tag.OTHER, null, null);
    private final Tag _tag;
    private final SharedFolderMetadata alreadySharedValue;
    private final PathRootError invalidPathRootValue;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$dropbox$core$v2$sharing$SharePathError$Tag = new int[Tag.values().length];

        static {
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$SharePathError$Tag[Tag.IS_FILE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$SharePathError$Tag[Tag.INSIDE_SHARED_FOLDER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$SharePathError$Tag[Tag.CONTAINS_SHARED_FOLDER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$SharePathError$Tag[Tag.CONTAINS_APP_FOLDER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$SharePathError$Tag[Tag.CONTAINS_TEAM_FOLDER.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$SharePathError$Tag[Tag.IS_APP_FOLDER.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$SharePathError$Tag[Tag.INSIDE_APP_FOLDER.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$SharePathError$Tag[Tag.IS_PUBLIC_FOLDER.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$SharePathError$Tag[Tag.INSIDE_PUBLIC_FOLDER.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$SharePathError$Tag[Tag.ALREADY_SHARED.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$SharePathError$Tag[Tag.INVALID_PATH.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$SharePathError$Tag[Tag.IS_OSX_PACKAGE.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$SharePathError$Tag[Tag.INSIDE_OSX_PACKAGE.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$SharePathError$Tag[Tag.INVALID_PATH_ROOT.ordinal()] = 14;
            } catch (NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$com$dropbox$core$v2$sharing$SharePathError$Tag[Tag.OTHER.ordinal()] = 15;
            } catch (NoSuchFieldError e15) {
            }
        }
    }

    static class Serializer extends UnionSerializer<SharePathError> {
        public static final Serializer INSTANCE = new Serializer();

        Serializer() {
        }

        public void serialize(SharePathError value, JsonGenerator g) throws IOException, JsonGenerationException {
            switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$SharePathError$Tag[value.tag().ordinal()]) {
                case SQLiteDatabase.OPEN_READONLY /*1*/:
                    g.writeString("is_file");
                    return;
                case SQLiteDatabase.CONFLICT_ABORT /*2*/:
                    g.writeString("inside_shared_folder");
                    return;
                case SQLiteDatabase.CONFLICT_FAIL /*3*/:
                    g.writeString("contains_shared_folder");
                    return;
                case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
                    g.writeString("contains_app_folder");
                    return;
                case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
                    g.writeString("contains_team_folder");
                    return;
                case IRemoteStorage.BOX_REMOTE /*6*/:
                    g.writeString("is_app_folder");
                    return;
                case IRemoteStorage.PIN /*7*/:
                    g.writeString("inside_app_folder");
                    return;
                case IRemoteStorage.FOLDER_REMOTE /*8*/:
                    g.writeString("is_public_folder");
                    return;
                case IRemoteStorage.WEBDAV_REMOTE /*9*/:
                    g.writeString("inside_public_folder");
                    return;
                case IRemoteStorage.WEBDAV_REMOTE_USERNAME /*10*/:
                    g.writeStartObject();
                    writeTag("already_shared", g);
                    Serializer.INSTANCE.serialize(value.alreadySharedValue, g, true);
                    g.writeEndObject();
                    return;
                case IRemoteStorage.WEBDAV_REMOTE_PASSWORD /*11*/:
                    g.writeString("invalid_path");
                    return;
                case SpassFingerprint.STATUS_QUALITY_FAILED /*12*/:
                    g.writeString("is_osx_package");
                    return;
                case SpassFingerprint.STATUS_USER_CANCELLED_BY_TOUCH_OUTSIDE /*13*/:
                    g.writeString("inside_osx_package");
                    return;
                case R.styleable.FloatingActionMenu_menu_labels_colorRipple /*14*/:
                    g.writeStartObject();
                    writeTag("invalid_path_root", g);
                    com.dropbox.core.v2.files.PathRootError.Serializer.INSTANCE.serialize(value.invalidPathRootValue, g, true);
                    g.writeEndObject();
                    return;
                default:
                    g.writeString("other");
                    return;
            }
        }

        public SharePathError deserialize(JsonParser p) throws IOException, JsonParseException {
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
            SharePathError value;
            if ("is_file".equals(tag)) {
                value = SharePathError.IS_FILE;
            } else if ("inside_shared_folder".equals(tag)) {
                value = SharePathError.INSIDE_SHARED_FOLDER;
            } else if ("contains_shared_folder".equals(tag)) {
                value = SharePathError.CONTAINS_SHARED_FOLDER;
            } else if ("contains_app_folder".equals(tag)) {
                value = SharePathError.CONTAINS_APP_FOLDER;
            } else if ("contains_team_folder".equals(tag)) {
                value = SharePathError.CONTAINS_TEAM_FOLDER;
            } else if ("is_app_folder".equals(tag)) {
                value = SharePathError.IS_APP_FOLDER;
            } else if ("inside_app_folder".equals(tag)) {
                value = SharePathError.INSIDE_APP_FOLDER;
            } else if ("is_public_folder".equals(tag)) {
                value = SharePathError.IS_PUBLIC_FOLDER;
            } else if ("inside_public_folder".equals(tag)) {
                value = SharePathError.INSIDE_PUBLIC_FOLDER;
            } else if ("already_shared".equals(tag)) {
                value = SharePathError.alreadyShared(Serializer.INSTANCE.deserialize(p, true));
            } else if ("invalid_path".equals(tag)) {
                value = SharePathError.INVALID_PATH;
            } else if ("is_osx_package".equals(tag)) {
                value = SharePathError.IS_OSX_PACKAGE;
            } else if ("inside_osx_package".equals(tag)) {
                value = SharePathError.INSIDE_OSX_PACKAGE;
            } else if ("invalid_path_root".equals(tag)) {
                value = SharePathError.invalidPathRoot(com.dropbox.core.v2.files.PathRootError.Serializer.INSTANCE.deserialize(p, true));
            } else {
                value = SharePathError.OTHER;
            }
            if (!collapsed) {
                StoneSerializer.skipFields(p);
                StoneSerializer.expectEndObject(p);
            }
            return value;
        }
    }

    public enum Tag {
        IS_FILE,
        INSIDE_SHARED_FOLDER,
        CONTAINS_SHARED_FOLDER,
        CONTAINS_APP_FOLDER,
        CONTAINS_TEAM_FOLDER,
        IS_APP_FOLDER,
        INSIDE_APP_FOLDER,
        IS_PUBLIC_FOLDER,
        INSIDE_PUBLIC_FOLDER,
        ALREADY_SHARED,
        INVALID_PATH,
        IS_OSX_PACKAGE,
        INSIDE_OSX_PACKAGE,
        INVALID_PATH_ROOT,
        OTHER
    }

    private SharePathError(Tag _tag, SharedFolderMetadata alreadySharedValue, PathRootError invalidPathRootValue) {
        this._tag = _tag;
        this.alreadySharedValue = alreadySharedValue;
        this.invalidPathRootValue = invalidPathRootValue;
    }

    public Tag tag() {
        return this._tag;
    }

    public boolean isIsFile() {
        return this._tag == Tag.IS_FILE;
    }

    public boolean isInsideSharedFolder() {
        return this._tag == Tag.INSIDE_SHARED_FOLDER;
    }

    public boolean isContainsSharedFolder() {
        return this._tag == Tag.CONTAINS_SHARED_FOLDER;
    }

    public boolean isContainsAppFolder() {
        return this._tag == Tag.CONTAINS_APP_FOLDER;
    }

    public boolean isContainsTeamFolder() {
        return this._tag == Tag.CONTAINS_TEAM_FOLDER;
    }

    public boolean isIsAppFolder() {
        return this._tag == Tag.IS_APP_FOLDER;
    }

    public boolean isInsideAppFolder() {
        return this._tag == Tag.INSIDE_APP_FOLDER;
    }

    public boolean isIsPublicFolder() {
        return this._tag == Tag.IS_PUBLIC_FOLDER;
    }

    public boolean isInsidePublicFolder() {
        return this._tag == Tag.INSIDE_PUBLIC_FOLDER;
    }

    public boolean isAlreadyShared() {
        return this._tag == Tag.ALREADY_SHARED;
    }

    public static SharePathError alreadyShared(SharedFolderMetadata value) {
        if (value != null) {
            return new SharePathError(Tag.ALREADY_SHARED, value, null);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public SharedFolderMetadata getAlreadySharedValue() {
        if (this._tag == Tag.ALREADY_SHARED) {
            return this.alreadySharedValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.ALREADY_SHARED, but was Tag." + this._tag.name());
    }

    public boolean isInvalidPath() {
        return this._tag == Tag.INVALID_PATH;
    }

    public boolean isIsOsxPackage() {
        return this._tag == Tag.IS_OSX_PACKAGE;
    }

    public boolean isInsideOsxPackage() {
        return this._tag == Tag.INSIDE_OSX_PACKAGE;
    }

    public boolean isInvalidPathRoot() {
        return this._tag == Tag.INVALID_PATH_ROOT;
    }

    public static SharePathError invalidPathRoot(PathRootError value) {
        if (value != null) {
            return new SharePathError(Tag.INVALID_PATH_ROOT, null, value);
        }
        throw new IllegalArgumentException("Value is null");
    }

    public PathRootError getInvalidPathRootValue() {
        if (this._tag == Tag.INVALID_PATH_ROOT) {
            return this.invalidPathRootValue;
        }
        throw new IllegalStateException("Invalid tag: required Tag.INVALID_PATH_ROOT, but was Tag." + this._tag.name());
    }

    public boolean isOther() {
        return this._tag == Tag.OTHER;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this._tag, this.alreadySharedValue, this.invalidPathRootValue});
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SharePathError)) {
            return false;
        }
        SharePathError other = (SharePathError) obj;
        if (this._tag != other._tag) {
            return false;
        }
        switch (AnonymousClass1.$SwitchMap$com$dropbox$core$v2$sharing$SharePathError$Tag[this._tag.ordinal()]) {
            case SQLiteDatabase.OPEN_READONLY /*1*/:
            case SQLiteDatabase.CONFLICT_ABORT /*2*/:
            case SQLiteDatabase.CONFLICT_FAIL /*3*/:
            case SQLiteDatabase.CONFLICT_IGNORE /*4*/:
            case SQLiteDatabase.CONFLICT_REPLACE /*5*/:
            case IRemoteStorage.BOX_REMOTE /*6*/:
            case IRemoteStorage.PIN /*7*/:
            case IRemoteStorage.FOLDER_REMOTE /*8*/:
            case IRemoteStorage.WEBDAV_REMOTE /*9*/:
            case IRemoteStorage.WEBDAV_REMOTE_PASSWORD /*11*/:
            case SpassFingerprint.STATUS_QUALITY_FAILED /*12*/:
            case SpassFingerprint.STATUS_USER_CANCELLED_BY_TOUCH_OUTSIDE /*13*/:
            case R.styleable.FloatingActionMenu_menu_labels_cornerRadius /*15*/:
                return true;
            case IRemoteStorage.WEBDAV_REMOTE_USERNAME /*10*/:
                if (this.alreadySharedValue == other.alreadySharedValue || this.alreadySharedValue.equals(other.alreadySharedValue)) {
                    z = true;
                }
                return z;
            case R.styleable.FloatingActionMenu_menu_labels_colorRipple /*14*/:
                if (this.invalidPathRootValue == other.invalidPathRootValue || this.invalidPathRootValue.equals(other.invalidPathRootValue)) {
                    z = true;
                }
                return z;
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
