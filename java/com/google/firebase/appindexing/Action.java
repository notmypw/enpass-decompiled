package com.google.firebase.appindexing;

import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.zzac;
import com.google.firebase.appindexing.internal.zza.zza;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface Action {

    public static class Builder {
        public static final String ACTIVATE_ACTION = "ActivateAction";
        public static final String ADD_ACTION = "AddAction";
        public static final String BOOKMARK_ACTION = "BookmarkAction";
        public static final String COMMENT_ACTION = "CommentAction";
        public static final String LIKE_ACTION = "LikeAction";
        public static final String LISTEN_ACTION = "ListenAction";
        public static final String SEND_ACTION = "SendAction";
        public static final String SHARE_ACTION = "ShareAction";
        public static final String STATUS_TYPE_ACTIVE = "http://schema.org/ActiveActionStatus";
        public static final String STATUS_TYPE_COMPLETED = "http://schema.org/CompletedActionStatus";
        public static final String STATUS_TYPE_FAILED = "http://schema.org/FailedActionStatus";
        public static final String VIEW_ACTION = "ViewAction";
        public static final String WATCH_ACTION = "WatchAction";
        private final String zzbXa;
        private String zzbXb;
        private String zzbXc;
        private String zzbXd;
        private zza zzbXe = Metadata.zzbXg;
        private String zzbXf;

        @Retention(RetentionPolicy.CLASS)
        public @interface StatusType {
        }

        public Builder(String str) {
            this.zzbXa = str;
        }

        public Action build() {
            zzac.zzb(this.zzbXb, "setObject is required before calling build().");
            zzac.zzb(this.zzbXc, "setObject is required before calling build().");
            return new com.google.firebase.appindexing.internal.zza(this.zzbXa, this.zzbXb, this.zzbXc, this.zzbXd, this.zzbXe, this.zzbXf);
        }

        public Builder setActionStatus(@StatusType String str) {
            zzac.zzw(str);
            this.zzbXf = str;
            return this;
        }

        public Builder setMetadata(@NonNull Builder builder) {
            zzac.zzw(builder);
            this.zzbXe = builder.zzVg();
            return this;
        }

        public Builder setObject(@NonNull String str, @NonNull String str2) {
            zzac.zzw(str);
            zzac.zzw(str2);
            this.zzbXb = str;
            this.zzbXc = str2;
            return this;
        }

        public Builder setObject(@NonNull String str, @NonNull String str2, @NonNull String str3) {
            zzac.zzw(str);
            zzac.zzw(str2);
            zzac.zzw(str3);
            this.zzbXb = str;
            this.zzbXc = str2;
            this.zzbXd = str3;
            return this;
        }
    }

    public interface Metadata {
        public static final zza zzbXg = new Builder().zzVg();

        public static class Builder {
            private boolean zzbXh = true;
            private boolean zzbXi = false;

            public Builder setUpload(boolean z) {
                this.zzbXh = z;
                return this;
            }

            public zza zzVg() {
                return new zza(this.zzbXh, null, null, null, false);
            }
        }
    }
}
