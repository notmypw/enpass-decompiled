package com.dropbox.core;

import com.dropbox.core.util.IOUtil;
import com.dropbox.core.util.StringUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

public class DbxSdkVersion {
    private static final String ResourceName = "/sdk-version.txt";
    public static final String Version = loadVersion();

    private static final class LoadException extends Exception {
        private static final long serialVersionUID = 0;

        public LoadException(String message) {
            super(message);
        }
    }

    private static String loadLineFromResource() throws LoadException {
        InputStream in;
        try {
            in = DbxSdkVersion.class.getResourceAsStream(ResourceName);
            if (in == null) {
                throw new LoadException("Not found.");
            }
            BufferedReader bin = new BufferedReader(IOUtil.utf8Reader(in));
            String version = bin.readLine();
            if (version == null) {
                throw new LoadException("No lines.");
            }
            String secondLine = bin.readLine();
            if (secondLine != null) {
                throw new LoadException("Found more than one line.  Second line: " + StringUtil.jq(secondLine));
            }
            IOUtil.closeInput(in);
            return version;
        } catch (IOException ex) {
            throw new LoadException(ex.getMessage());
        } catch (Throwable th) {
            IOUtil.closeInput(in);
        }
    }

    private static String loadVersion() {
        try {
            String version = loadLineFromResource();
            if (Pattern.compile("[0-9]+(?:\\.[0-9]+)*(?:-[-_A-Za-z0-9]+)?").matcher(version).matches()) {
                return version;
            }
            throw new LoadException("Text doesn't follow expected pattern: " + StringUtil.jq(version));
        } catch (LoadException ex) {
            throw new RuntimeException("Error loading version from resource \"sdk-version.txt\": " + ex.getMessage());
        }
    }
}
