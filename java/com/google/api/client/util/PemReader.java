package com.google.api.client.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.regex.Pattern;

@Beta
public final class PemReader {
    private static final Pattern BEGIN_PATTERN = Pattern.compile("-----BEGIN ([A-Z ]+)-----");
    private static final Pattern END_PATTERN = Pattern.compile("-----END ([A-Z ]+)-----");
    private BufferedReader reader;

    public static final class Section {
        private final byte[] base64decodedBytes;
        private final String title;

        Section(String title, byte[] base64decodedBytes) {
            this.title = (String) Preconditions.checkNotNull(title);
            this.base64decodedBytes = (byte[]) Preconditions.checkNotNull(base64decodedBytes);
        }

        public String getTitle() {
            return this.title;
        }

        public byte[] getBase64DecodedBytes() {
            return this.base64decodedBytes;
        }
    }

    public PemReader(Reader reader) {
        this.reader = new BufferedReader(reader);
    }

    public Section readNextSection() throws IOException {
        return readNextSection(null);
    }

    public Section readNextSection(String titleToLookFor) throws IOException {
        String title = null;
        StringBuilder keyBuilder = null;
        while (true) {
            String line = this.reader.readLine();
            if (line == null) {
                break;
            } else if (keyBuilder == null) {
                m = BEGIN_PATTERN.matcher(line);
                if (m.matches()) {
                    String curTitle = m.group(1);
                    if (titleToLookFor == null || curTitle.equals(titleToLookFor)) {
                        keyBuilder = new StringBuilder();
                        title = curTitle;
                    }
                }
            } else {
                m = END_PATTERN.matcher(line);
                if (m.matches()) {
                    Preconditions.checkArgument(m.group(1).equals(title), "end tag (%s) doesn't match begin tag (%s)", m.group(1), title);
                    return new Section(title, Base64.decodeBase64(keyBuilder.toString()));
                }
                keyBuilder.append(line);
            }
        }
        Preconditions.checkArgument(title == null, "missing end tag (%s)", title);
        return null;
    }

    public static Section readFirstSectionAndClose(Reader reader) throws IOException {
        return readFirstSectionAndClose(reader, null);
    }

    public static Section readFirstSectionAndClose(Reader reader, String titleToLookFor) throws IOException {
        PemReader pemReader = new PemReader(reader);
        try {
            Section readNextSection = pemReader.readNextSection(titleToLookFor);
            return readNextSection;
        } finally {
            pemReader.close();
        }
    }

    public void close() throws IOException {
        this.reader.close();
    }
}
