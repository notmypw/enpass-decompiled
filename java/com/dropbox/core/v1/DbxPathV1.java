package com.dropbox.core.v1;

import com.dropbox.core.util.StringUtil;

public class DbxPathV1 {
    public static boolean isValid(String path) {
        return findError(path) == null;
    }

    public static String findError(String path) {
        if (!path.startsWith("/")) {
            return "must start with \"/\"";
        }
        if (path.length() == 1 || !path.endsWith("/")) {
            return null;
        }
        return "must not end with \"/\"";
    }

    public static void checkArg(String argName, String value) {
        if (value == null) {
            throw new IllegalArgumentException("'" + argName + "' should not be null");
        }
        String error = findError(value);
        if (error != null) {
            throw new IllegalArgumentException("'" + argName + "': bad path: " + error + ": " + StringUtil.jq(value));
        }
    }

    public static void checkArgNonRoot(String argName, String value) {
        if ("/".equals(value)) {
            throw new IllegalArgumentException("'" + argName + "' should not be the root path (\"/\")");
        }
        checkArg(argName, value);
    }

    public static String getName(String path) {
        if (path == null) {
            throw new IllegalArgumentException("'path' can't be null");
        } else if (!path.startsWith("/")) {
            throw new IllegalArgumentException("Not a valid path.  Doesn't start with a \"/\": \"" + path + "\"");
        } else if (path.length() <= 1 || !path.endsWith("/")) {
            int start = path.length() - 1;
            while (path.charAt(start) != '/') {
                start--;
            }
            return path.substring(start + 1);
        } else {
            throw new IllegalArgumentException("Not a valid path.  Ends with a \"/\": \"" + path + "\"");
        }
    }

    public static String[] split(String path) {
        if (path == null) {
            throw new IllegalArgumentException("'path' can't be null");
        } else if (!path.startsWith("/")) {
            throw new IllegalArgumentException("Not a valid path.  Doesn't start with a \"/\": \"" + path + "\"");
        } else if (path.length() > 1 && path.endsWith("/")) {
            throw new IllegalArgumentException("Not a valid path.  Ends with a \"/\": \"" + path + "\"");
        } else if (path.length() == 1) {
            return new String[0];
        } else {
            return path.substring(1).split("/");
        }
    }

    public static String getParent(String path) {
        if (path == null) {
            throw new IllegalArgumentException("'path' can't be null");
        } else if (!path.startsWith("/")) {
            throw new IllegalArgumentException("Not a valid path.  Doesn't start with a \"/\": \"" + path + "\"");
        } else if (path.length() <= 1 || !path.endsWith("/")) {
            int lastSlash = path.lastIndexOf("/");
            if (path.length() == 1) {
                return null;
            }
            if (lastSlash == 0) {
                return "/";
            }
            return path.substring(0, lastSlash);
        } else {
            throw new IllegalArgumentException("Not a valid path.  Ends with a \"/\": \"" + path + "\"");
        }
    }
}
