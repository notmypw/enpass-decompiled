package com.dropbox.core.stone;

import com.fasterxml.jackson.core.JsonFactory;
import com.github.clans.fab.BuildConfig;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

final class Util {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final JsonFactory JSON = new JsonFactory();
    private static final int LONG_FORMAT_LENGTH = DATE_TIME_FORMAT.replace("'", BuildConfig.FLAVOR).length();
    private static final int SHORT_FORMAT_LENGTH = DATE_FORMAT.replace("'", BuildConfig.FLAVOR).length();
    private static final TimeZone UTC = TimeZone.getTimeZone("UTC");

    Util() {
    }

    public static String formatTimestamp(Date timestamp) {
        DateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT);
        format.setCalendar(new GregorianCalendar(UTC));
        return format.format(timestamp);
    }

    public static Date parseTimestamp(String timestamp) throws ParseException {
        DateFormat format;
        int length = timestamp.length();
        if (length == LONG_FORMAT_LENGTH) {
            format = new SimpleDateFormat(DATE_TIME_FORMAT);
        } else if (length == SHORT_FORMAT_LENGTH) {
            format = new SimpleDateFormat(DATE_FORMAT);
        } else {
            throw new ParseException("timestamp has unexpected format: '" + timestamp + "'", 0);
        }
        format.setCalendar(new GregorianCalendar(UTC));
        return format.parse(timestamp);
    }
}
