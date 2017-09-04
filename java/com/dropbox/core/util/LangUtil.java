package com.dropbox.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class LangUtil {
    public static RuntimeException mkAssert(String messagePrefix, Throwable cause) {
        RuntimeException ae = new RuntimeException(messagePrefix + ": " + cause.getMessage());
        ae.initCause(cause);
        return ae;
    }

    public static AssertionError badType(Object a) {
        String msg;
        if (a == null) {
            msg = "bad type: null";
        } else {
            msg = "bad type: " + a.getClass().getName();
        }
        return new AssertionError(msg);
    }

    public static <T> T[] arrayConcat(T[] a, T[] b) {
        if (a == null) {
            throw new IllegalArgumentException("'a' can't be null");
        } else if (b == null) {
            throw new IllegalArgumentException("'b' can't be null");
        } else {
            T[] rn = Arrays.copyOf(a, a.length + b.length);
            System.arraycopy(b, 0, rn, a.length, b.length);
            return rn;
        }
    }

    public static <T> boolean nullableEquals(T a, T b) {
        if (a == null) {
            return b == null;
        } else {
            if (b != null) {
                return a.equals(b);
            }
            return false;
        }
    }

    public static int nullableHashCode(Object o) {
        if (o == null) {
            return 0;
        }
        return o.hashCode() + 1;
    }

    public static Date truncateMillis(Date date) {
        if (date == null) {
            return date;
        }
        long time = date.getTime();
        return new Date(time - (time % 1000));
    }

    public static List<Date> truncateMillis(List<Date> dates) {
        if (dates == null) {
            return dates;
        }
        List<Date> arrayList = new ArrayList(dates.size());
        for (Date date : dates) {
            long time = date.getTime();
            arrayList.add(new Date(time - (time % 1000)));
        }
        return arrayList;
    }
}
