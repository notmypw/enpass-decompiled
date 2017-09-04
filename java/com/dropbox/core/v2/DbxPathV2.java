package com.dropbox.core.v2;

public class DbxPathV2 {
    public static boolean isValid(String path) {
        return findError(path) == null;
    }

    public static String findError(String path) {
        if (path.length() == 0) {
            return null;
        }
        if (!path.startsWith("/")) {
            return "expecting first character to be \"/\"";
        }
        if (path.endsWith("/")) {
            return "must not end with \"/\"";
        }
        return null;
    }

    public static String getName(String path) {
        if (path == null) {
            throw new IllegalArgumentException("'path' can't be null");
        } else if (path.length() == 0) {
            return null;
        } else {
            if (!path.startsWith("/")) {
                throw new IllegalArgumentException("Not a valid path.  Doesn't start with a \"/\": \"" + path + "\"");
            } else if (path.endsWith("/")) {
                throw new IllegalArgumentException("Not a valid path.  Ends with a \"/\": \"" + path + "\"");
            } else {
                int start = path.length() - 1;
                while (path.charAt(start) != '/') {
                    start--;
                }
                return path.substring(start + 1);
            }
        }
    }

    public static String[] split(String path) {
        if (path == null) {
            throw new IllegalArgumentException("'path' can't be null");
        } else if (path.length() == 0) {
            return new String[0];
        } else {
            if (!path.startsWith("/")) {
                throw new IllegalArgumentException("Not a valid path.  Doesn't start with a \"/\": \"" + path + "\"");
            } else if (!path.endsWith("/")) {
                return path.substring(1).split("/");
            } else {
                throw new IllegalArgumentException("Not a valid path.  Ends with a \"/\": \"" + path + "\"");
            }
        }
    }

    public static String getParent(String path) {
        if (path == null) {
            throw new IllegalArgumentException("'path' can't be null");
        } else if (path.length() == 0) {
            return null;
        } else {
            if (!path.startsWith("/")) {
                throw new IllegalArgumentException("Not a valid path.  Doesn't start with a \"/\": \"" + path + "\"");
            } else if (!path.endsWith("/")) {
                return path.substring(0, path.lastIndexOf("/"));
            } else {
                throw new IllegalArgumentException("Not a valid path.  Ends with a \"/\": \"" + path + "\"");
            }
        }
    }
}
