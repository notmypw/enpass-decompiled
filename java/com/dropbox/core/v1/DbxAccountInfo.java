package com.dropbox.core.v1;

import com.box.androidsdk.content.auth.OAuthActivity;
import com.box.androidsdk.content.models.BoxUploadEmail;
import com.dropbox.core.json.JsonReadException;
import com.dropbox.core.json.JsonReader;
import com.dropbox.core.json.JsonReader.FieldMapping;
import com.dropbox.core.json.JsonReader.FieldMapping.Builder;
import com.dropbox.core.util.DumpWriter;
import com.dropbox.core.util.Dumpable;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import net.sqlcipher.database.SQLiteDatabase;

public class DbxAccountInfo extends Dumpable {
    private static final FieldMapping FM;
    private static final int FM_country = 2;
    private static final int FM_display_name = 1;
    private static final int FM_email = 6;
    private static final int FM_email_verified = 7;
    private static final int FM_name_details = 5;
    private static final int FM_quota_info = 4;
    private static final int FM_referral_link = 3;
    private static final int FM_uid = 0;
    public static final JsonReader<DbxAccountInfo> Reader = new JsonReader<DbxAccountInfo>() {
        public final DbxAccountInfo read(JsonParser parser) throws IOException, JsonReadException {
            JsonLocation top = JsonReader.expectObjectStart(parser);
            long uid = -1;
            String display_name = null;
            String country = null;
            String referral_link = null;
            Quota quota_info = null;
            String email = null;
            NameDetails nameDetails = null;
            Boolean emailVerified = null;
            while (parser.getCurrentToken() == JsonToken.FIELD_NAME) {
                String fieldName = parser.getCurrentName();
                parser.nextToken();
                try {
                    int fi = DbxAccountInfo.FM.get(fieldName);
                    switch (fi) {
                        case -1:
                            JsonReader.skipValue(parser);
                            break;
                        case SQLiteDatabase.OPEN_READWRITE /*0*/:
                            uid = JsonReader.readUnsignedLongField(parser, fieldName, uid);
                            break;
                        case DbxAccountInfo.FM_display_name /*1*/:
                            display_name = (String) JsonReader.StringReader.readField(parser, fieldName, display_name);
                            break;
                        case DbxAccountInfo.FM_country /*2*/:
                            country = (String) JsonReader.StringReader.readField(parser, fieldName, country);
                            break;
                        case DbxAccountInfo.FM_referral_link /*3*/:
                            referral_link = (String) JsonReader.StringReader.readField(parser, fieldName, referral_link);
                            break;
                        case DbxAccountInfo.FM_quota_info /*4*/:
                            quota_info = (Quota) Quota.Reader.readField(parser, fieldName, quota_info);
                            break;
                        case DbxAccountInfo.FM_name_details /*5*/:
                            nameDetails = (NameDetails) NameDetails.Reader.readField(parser, fieldName, nameDetails);
                            break;
                        case DbxAccountInfo.FM_email /*6*/:
                            email = (String) JsonReader.StringReader.readField(parser, fieldName, email);
                            break;
                        case DbxAccountInfo.FM_email_verified /*7*/:
                            emailVerified = (Boolean) JsonReader.BooleanReader.readField(parser, fieldName, emailVerified);
                            break;
                        default:
                            throw new AssertionError("bad index: " + fi + ", field = \"" + fieldName + "\"");
                    }
                } catch (JsonReadException ex) {
                    throw ex.addFieldContext(fieldName);
                }
            }
            JsonReader.expectObjectEnd(parser);
            if (uid < 0) {
                throw new JsonReadException("missing field \"uid\"", top);
            } else if (display_name == null) {
                throw new JsonReadException("missing field \"display_name\"", top);
            } else if (country == null) {
                throw new JsonReadException("missing field \"country\"", top);
            } else if (referral_link == null) {
                throw new JsonReadException("missing field \"referral_link\"", top);
            } else if (quota_info == null) {
                throw new JsonReadException("missing field \"quota_info\"", top);
            } else if (email == null) {
                throw new JsonReadException("missing field \"email\"", top);
            } else if (nameDetails == null) {
                throw new JsonReadException("missing field \"nameDetails\"", top);
            } else if (emailVerified != null) {
                return new DbxAccountInfo(uid, display_name, country, referral_link, quota_info, email, nameDetails, emailVerified.booleanValue());
            } else {
                throw new JsonReadException("missing field \"emailVerified\"", top);
            }
        }
    };
    public final String country;
    public final String displayName;
    public final String email;
    public final boolean emailVerified;
    public final NameDetails nameDetails;
    public final Quota quota;
    public final String referralLink;
    public final long userId;

    public static final class NameDetails extends Dumpable {
        private static final FieldMapping FM;
        private static final int FM_familiar_name = 0;
        private static final int FM_given_name = 1;
        private static final int FM_surname = 2;
        public static final JsonReader<NameDetails> Reader = new JsonReader<NameDetails>() {
            public final NameDetails read(JsonParser parser) throws IOException, JsonReadException {
                JsonLocation top = JsonReader.expectObjectStart(parser);
                String familiarName = null;
                String givenName = null;
                String surname = null;
                while (parser.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String fieldName = parser.getCurrentName();
                    parser.nextToken();
                    int fi = NameDetails.FM.get(fieldName);
                    switch (fi) {
                        case -1:
                            JsonReader.skipValue(parser);
                            break;
                        case NameDetails.FM_familiar_name /*0*/:
                            familiarName = (String) JsonReader.StringReader.readField(parser, fieldName, familiarName);
                            break;
                        case NameDetails.FM_given_name /*1*/:
                            givenName = (String) JsonReader.StringReader.readField(parser, fieldName, givenName);
                            break;
                        case NameDetails.FM_surname /*2*/:
                            surname = (String) JsonReader.StringReader.readField(parser, fieldName, surname);
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
                if (familiarName == null) {
                    throw new JsonReadException("missing field \"familiarName\"", top);
                } else if (surname == null) {
                    throw new JsonReadException("missing field \"surname\"", top);
                } else if (givenName != null) {
                    return new NameDetails(familiarName, givenName, surname);
                } else {
                    throw new JsonReadException("missing field \"givenName\"", top);
                }
            }
        };
        public final String familiarName;
        public final String givenName;
        public final String surname;

        public NameDetails(String familiarName, String givenName, String surname) {
            this.familiarName = familiarName;
            this.givenName = givenName;
            this.surname = surname;
        }

        protected void dumpFields(DumpWriter out) {
            out.f("familiarName").v(this.familiarName);
            out.f("givenName").v(this.givenName);
            out.f("surname").v(this.surname);
        }

        static {
            Builder b = new Builder();
            b.add("familiar_name", FM_familiar_name);
            b.add("given_name", FM_given_name);
            b.add("surname", FM_surname);
            FM = b.build();
        }
    }

    public static final class Quota extends Dumpable {
        private static final FieldMapping FM;
        private static final int FM_normal = 1;
        private static final int FM_quota = 0;
        private static final int FM_shared = 2;
        public static final JsonReader<Quota> Reader = new JsonReader<Quota>() {
            public final Quota read(JsonParser parser) throws IOException, JsonReadException {
                JsonLocation top = JsonReader.expectObjectStart(parser);
                long quota = -1;
                long normal = -1;
                long shared = -1;
                while (parser.getCurrentToken() == JsonToken.FIELD_NAME) {
                    String fieldName = parser.getCurrentName();
                    parser.nextToken();
                    int fi = Quota.FM.get(fieldName);
                    switch (fi) {
                        case -1:
                            JsonReader.skipValue(parser);
                            break;
                        case Quota.FM_quota /*0*/:
                            quota = JsonReader.readUnsignedLongField(parser, fieldName, quota);
                            break;
                        case Quota.FM_normal /*1*/:
                            normal = JsonReader.readUnsignedLongField(parser, fieldName, normal);
                            break;
                        case Quota.FM_shared /*2*/:
                            shared = JsonReader.readUnsignedLongField(parser, fieldName, shared);
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
                if (quota < 0) {
                    throw new JsonReadException("missing field \"quota\"", top);
                } else if (normal < 0) {
                    throw new JsonReadException("missing field \"normal\"", top);
                } else if (shared >= 0) {
                    return new Quota(quota, normal, shared);
                } else {
                    throw new JsonReadException("missing field \"shared\"", top);
                }
            }
        };
        public final long normal;
        public final long shared;
        public final long total;

        public Quota(long quota, long normal, long quotaShared) {
            this.total = quota;
            this.normal = normal;
            this.shared = quotaShared;
        }

        protected void dumpFields(DumpWriter out) {
            out.f("total").v(this.total);
            out.f("normal").v(this.normal);
            out.f("shared").v(this.shared);
        }

        static {
            Builder b = new Builder();
            b.add("quota", FM_quota);
            b.add("normal", FM_normal);
            b.add("shared", FM_shared);
            FM = b.build();
        }
    }

    public DbxAccountInfo(long userId, String displayName, String country, String referralLink, Quota quota, String email, NameDetails nameDetails, boolean emailVerified) {
        this.userId = userId;
        this.displayName = displayName;
        this.country = country;
        this.referralLink = referralLink;
        this.quota = quota;
        this.email = email;
        this.nameDetails = nameDetails;
        this.emailVerified = emailVerified;
    }

    protected void dumpFields(DumpWriter out) {
        out.f(OAuthActivity.USER_ID).v(this.userId);
        out.f("displayName").v(this.displayName);
        out.f("country").v(this.country);
        out.f("referralLink").v(this.referralLink);
        out.f("quota").v(this.quota);
        out.f("nameDetails").v(this.nameDetails);
        out.f(BoxUploadEmail.FIELD_EMAIL).v(this.email);
        out.f("emailVerified").v(this.emailVerified);
    }

    static {
        Builder b = new Builder();
        b.add("uid", 0);
        b.add("display_name", FM_display_name);
        b.add("country", FM_country);
        b.add("referral_link", FM_referral_link);
        b.add("quota_info", FM_quota_info);
        b.add("name_details", FM_name_details);
        b.add(BoxUploadEmail.FIELD_EMAIL, FM_email);
        b.add("email_verified", FM_email_verified);
        FM = b.build();
    }
}
