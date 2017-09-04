package com.dropbox.core.json;

import com.box.androidsdk.content.BoxConstants;
import com.dropbox.core.util.LangUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public abstract class JsonWriter<T> {
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final TimeZone UTC = TimeZone.getTimeZone("UTC");
    private static final String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec", null};
    private static final String[] weekdays = new String[]{null, "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

    public abstract void write(T t, JsonGenerator jsonGenerator) throws IOException;

    public static String formatDate(Date date) {
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        df.setTimeZone(UTC);
        return df.format(date);
    }

    public void write(T value, JsonGenerator g, int level) throws IOException {
        write(value, g);
    }

    public void writeFields(T t, JsonGenerator g) throws IOException {
    }

    public final String writeToString(T value, boolean indent) {
        JsonGenerator g;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            g = JsonReader.jsonFactory.createGenerator(out);
            if (indent) {
                g = g.useDefaultPrettyPrinter();
            }
            write(value, g);
            g.flush();
            return new String(out.toByteArray(), "UTF-8");
        } catch (IOException ex) {
            throw LangUtil.mkAssert("Impossible", ex);
        } catch (Throwable th) {
            g.flush();
        }
    }

    public final String writeToString(T value) {
        return writeToString(value, true);
    }

    public final void writeToStream(T value, OutputStream out, boolean indent) throws IOException {
        JsonGenerator g = JsonReader.jsonFactory.createGenerator(out);
        if (indent) {
            g = g.useDefaultPrettyPrinter();
        }
        try {
            write(value, g);
        } finally {
            g.flush();
        }
    }

    public final void writeToStream(T value, OutputStream out) throws IOException {
        writeToStream(value, out, true);
    }

    public final void writeToFile(T value, File file, boolean indent) throws IOException {
        FileOutputStream fout = new FileOutputStream(file);
        try {
            writeToStream(value, fout, indent);
        } finally {
            fout.close();
        }
    }

    public final void writeToFile(T value, File file) throws IOException {
        writeToFile((Object) value, file, true);
    }

    public final void writeToFile(T value, String fileName, boolean indent) throws IOException {
        writeToFile((Object) value, new File(fileName), indent);
    }

    public final void writeToFile(T value, String fileName) throws IOException {
        writeToFile((Object) value, fileName, true);
    }

    public final void writeDateIso(Date date, JsonGenerator g) throws IOException {
        g.writeString(formatDate(date));
    }

    public final void writeDate(Date date, JsonGenerator g) throws IOException {
        GregorianCalendar c = new GregorianCalendar(JsonDateReader.UTC);
        c.setTime(date);
        String year = Integer.toString(c.get(1));
        String month = months[c.get(2)];
        String day = zeroPad(Integer.toString(c.get(5)), 2);
        String hour = zeroPad(Integer.toString(c.get(11)), 2);
        String minute = zeroPad(Integer.toString(c.get(12)), 2);
        String second = zeroPad(Integer.toString(c.get(13)), 2);
        String weekday = weekdays[c.get(7)];
        StringBuilder buf = new StringBuilder();
        buf.append(weekday).append(", ");
        buf.append(day).append(" ").append(month).append(" ").append(year).append(" ");
        buf.append(hour).append(":").append(minute).append(":").append(second).append(" +0000");
        g.writeString(buf.toString());
    }

    private static String zeroPad(String v, int desiredLength) {
        while (v.length() < desiredLength) {
            v = BoxConstants.ROOT_FOLDER_ID + v;
        }
        return v;
    }
}
