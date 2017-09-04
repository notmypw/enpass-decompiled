package com.dropbox.core.v1;

public final class DbxWriteMode {
    private static final DbxWriteMode AddInstance = new DbxWriteMode("overwrite", "false");
    private static final DbxWriteMode ForceInstance = new DbxWriteMode("overwrite", "true");
    final String[] params;

    DbxWriteMode(String... params) {
        this.params = params;
    }

    public static DbxWriteMode add() {
        return AddInstance;
    }

    public static DbxWriteMode force() {
        return ForceInstance;
    }

    public static DbxWriteMode update(String revisionToReplace) {
        return new DbxWriteMode("parent_rev", revisionToReplace);
    }
}
