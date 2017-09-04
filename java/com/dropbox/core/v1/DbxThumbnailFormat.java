package com.dropbox.core.v1;

public class DbxThumbnailFormat {
    public static final DbxThumbnailFormat JPEG = new DbxThumbnailFormat("jpeg");
    public static final DbxThumbnailFormat PNG = new DbxThumbnailFormat("png");
    public final String ident;

    public DbxThumbnailFormat(String ident) {
        this.ident = ident;
    }

    public static DbxThumbnailFormat bestForFileName(String fileName, DbxThumbnailFormat fallback) {
        fileName = fileName.toLowerCase();
        if (fileName.endsWith(".png") || fileName.endsWith(".gif")) {
            return PNG;
        }
        if (fileName.endsWith(".jpeg") || fileName.endsWith(".jpg") || fileName.endsWith(".jpe")) {
            return JPEG;
        }
        return fallback;
    }
}
