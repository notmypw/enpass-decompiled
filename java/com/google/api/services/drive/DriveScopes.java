package com.google.api.services.drive;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class DriveScopes {
    public static final String DRIVE = "https://www.googleapis.com/auth/drive";
    public static final String DRIVE_APPDATA = "https://www.googleapis.com/auth/drive.appdata";
    public static final String DRIVE_APPS_READONLY = "https://www.googleapis.com/auth/drive.apps.readonly";
    public static final String DRIVE_FILE = "https://www.googleapis.com/auth/drive.file";
    public static final String DRIVE_METADATA_READONLY = "https://www.googleapis.com/auth/drive.metadata.readonly";
    public static final String DRIVE_READONLY = "https://www.googleapis.com/auth/drive.readonly";
    public static final String DRIVE_SCRIPTS = "https://www.googleapis.com/auth/drive.scripts";

    public static Set<String> all() {
        Set hashSet = new HashSet();
        hashSet.add(DRIVE);
        hashSet.add(DRIVE_APPDATA);
        hashSet.add(DRIVE_APPS_READONLY);
        hashSet.add(DRIVE_FILE);
        hashSet.add(DRIVE_METADATA_READONLY);
        hashSet.add(DRIVE_READONLY);
        hashSet.add(DRIVE_SCRIPTS);
        return Collections.unmodifiableSet(hashSet);
    }

    private DriveScopes() {
    }
}
