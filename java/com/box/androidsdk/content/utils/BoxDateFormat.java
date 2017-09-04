package com.box.androidsdk.content.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public final class BoxDateFormat {
    private static final ThreadLocal<DateFormat> THREAD_LOCAL_DATE_FORMAT = new ThreadLocal<DateFormat>() {
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        }
    };
    private static final ThreadLocal<DateFormat> THREAD_LOCAL_HEADER_DATE_FORMAT = new ThreadLocal<DateFormat>() {
        protected DateFormat initialValue() {
            return new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
        }
    };
    private static final ThreadLocal<DateFormat> THREAD_LOCAL_ROUND_TO_DAY_DATE_FORMAT = new ThreadLocal<DateFormat>() {
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    private BoxDateFormat() {
    }

    public static Date parse(String dateString) throws ParseException {
        return ((DateFormat) THREAD_LOCAL_DATE_FORMAT.get()).parse(dateString);
    }

    public static String format(Date date) {
        String format = ((DateFormat) THREAD_LOCAL_DATE_FORMAT.get()).format(date);
        return format.substring(0, 22) + ":" + format.substring(22);
    }

    public static Date parseRoundToDay(String dateString) throws ParseException {
        return ((DateFormat) THREAD_LOCAL_ROUND_TO_DAY_DATE_FORMAT.get()).parse(dateString);
    }

    public static String formatRoundToDay(Date date) {
        return ((DateFormat) THREAD_LOCAL_ROUND_TO_DAY_DATE_FORMAT.get()).format(date);
    }

    public static Date parseHeaderDate(String dateString) throws ParseException {
        return ((DateFormat) THREAD_LOCAL_HEADER_DATE_FORMAT.get()).parse(dateString);
    }

    public static String getTimeRangeString(Date fromDate, Date toDate) {
        if (fromDate == null && toDate == null) {
            return null;
        }
        StringBuilder sbr = new StringBuilder();
        if (fromDate != null) {
            sbr.append(format(fromDate));
        }
        sbr.append(",");
        if (toDate != null) {
            sbr.append(format(toDate));
        }
        return sbr.toString();
    }

    public static Date[] getTimeRangeDates(String timeRangeString) {
        if (SdkUtils.isEmptyString(timeRangeString)) {
            return null;
        }
        String[] dateStrings = timeRangeString.split(",");
        Date[] dates = new Date[2];
        try {
            dates[0] = parse(dateStrings[0]);
        } catch (ParseException e) {
        } catch (ArrayIndexOutOfBoundsException e2) {
        }
        try {
            dates[1] = parse(dateStrings[1]);
            return dates;
        } catch (ParseException e3) {
            return dates;
        } catch (ArrayIndexOutOfBoundsException e4) {
            return dates;
        }
    }

    public static Date convertToDay(Date date) throws ParseException {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("PST"));
        calendar.setTime(date);
        return parseRoundToDay(formatRoundToDay(calendar.getTime()));
    }
}
