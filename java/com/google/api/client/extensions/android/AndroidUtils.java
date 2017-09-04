package com.google.api.client.extensions.android;

import android.os.Build.VERSION;
import com.google.api.client.util.Beta;
import com.google.api.client.util.Preconditions;

@Beta
public class AndroidUtils {
    public static boolean isMinimumSdkLevel(int minimumSdkLevel) {
        return VERSION.SDK_INT >= minimumSdkLevel;
    }

    public static void checkMinimumSdkLevel(int minimumSdkLevel) {
        Preconditions.checkArgument(isMinimumSdkLevel(minimumSdkLevel), "running on Android SDK level %s but requires minimum %s", Integer.valueOf(VERSION.SDK_INT), Integer.valueOf(minimumSdkLevel));
    }

    private AndroidUtils() {
    }
}
