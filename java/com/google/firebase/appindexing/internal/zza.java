package com.google.firebase.appindexing.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.firebase.appindexing.Action;

public class zza extends com.google.android.gms.common.internal.safeparcel.zza implements Action {
    public static final Creator<zza> CREATOR = new zzb();
    private final String zzbXa;
    private final String zzbXb;
    private final String zzbXc;
    private final String zzbXd;
    private final zza zzbXe;
    private final String zzbXf;

    public static class zza extends com.google.android.gms.common.internal.safeparcel.zza {
        public static final Creator<zza> CREATOR = new zzo();
        private int zzahV = 0;
        private final String zzaiu;
        private final boolean zzbXh;
        private final boolean zzbXi;
        private final String zzbXp;
        private final byte[] zzbXq;

        zza(int i, boolean z, String str, String str2, byte[] bArr, boolean z2) {
            this.zzahV = i;
            this.zzbXh = z;
            this.zzbXp = str;
            this.zzaiu = str2;
            this.zzbXq = bArr;
            this.zzbXi = z2;
        }

        public zza(boolean z, String str, String str2, byte[] bArr, boolean z2) {
            this.zzbXh = z;
            this.zzbXp = str;
            this.zzaiu = str2;
            this.zzbXq = bArr;
            this.zzbXi = z2;
        }

        public String getAccountName() {
            return this.zzaiu;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("MetadataImpl { ");
            stringBuilder.append("{ eventStatus: '").append(this.zzahV).append("' } ");
            stringBuilder.append("{ uploadable: '").append(this.zzbXh).append("' } ");
            if (this.zzbXp != null) {
                stringBuilder.append("{ completionToken: '").append(this.zzbXp).append("' } ");
            }
            if (this.zzaiu != null) {
                stringBuilder.append("{ accountName: '").append(this.zzaiu).append("' } ");
            }
            if (this.zzbXq != null) {
                stringBuilder.append("{ ssbContext: [ ");
                for (byte toHexString : this.zzbXq) {
                    stringBuilder.append("0x").append(Integer.toHexString(toHexString)).append(" ");
                }
                stringBuilder.append("] } ");
            }
            stringBuilder.append("{ contextOnly: '").append(this.zzbXi).append("' } ");
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        public void writeToParcel(Parcel parcel, int i) {
            zzo.zza(this, parcel, i);
        }

        public int zzVr() {
            return this.zzahV;
        }

        public boolean zzVs() {
            return this.zzbXh;
        }

        public String zzVt() {
            return this.zzbXp;
        }

        public byte[] zzVu() {
            return this.zzbXq;
        }

        public boolean zzVv() {
            return this.zzbXi;
        }

        public void zzpY(int i) {
            this.zzahV = i;
        }
    }

    public zza(String str, String str2, String str3, String str4, zza com_google_firebase_appindexing_internal_zza_zza, String str5) {
        this.zzbXa = str;
        this.zzbXb = str2;
        this.zzbXc = str3;
        this.zzbXd = str4;
        this.zzbXe = com_google_firebase_appindexing_internal_zza_zza;
        this.zzbXf = str5;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ActionImpl { ");
        stringBuilder.append("{ actionType: '").append(this.zzbXa).append("' } ");
        stringBuilder.append("{ objectName: '").append(this.zzbXb).append("' } ");
        stringBuilder.append("{ objectUrl: '").append(this.zzbXc).append("' } ");
        if (this.zzbXd != null) {
            stringBuilder.append("{ objectSameAs: '").append(this.zzbXd).append("' } ");
        }
        if (this.zzbXe != null) {
            stringBuilder.append("{ metadata: '").append(this.zzbXe.toString()).append("' } ");
        }
        if (this.zzbXf != null) {
            stringBuilder.append("{ actionStatus: '").append(this.zzbXf).append("' } ");
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzb.zza(this, parcel, i);
    }

    public String zzVl() {
        return this.zzbXa;
    }

    public String zzVm() {
        return this.zzbXb;
    }

    public String zzVn() {
        return this.zzbXc;
    }

    public String zzVo() {
        return this.zzbXd;
    }

    public zza zzVp() {
        return this.zzbXe;
    }

    public String zzVq() {
        return this.zzbXf;
    }
}
