package com.github.clans.fab;

import android.content.Context;
import android.os.Build.VERSION;

final class Util {
    private Util() {
    }

    static int dpToPx(Context context, float dp) {
        return Math.round(dp * context.getResources().getDisplayMetrics().density);
    }

    static boolean hasJellyBean() {
        return VERSION.SDK_INT >= 16;
    }

    static boolean hasLollipop() {
        return VERSION.SDK_INT >= 21;
    }
}
