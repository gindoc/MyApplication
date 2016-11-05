package com.cwenhui.utils;

import android.util.Log;

import com.cwenhui.test.BuildConfig;

/**
 * Created by cwenhui on 2016/11/5.
 */

public class LogUtil {
    public static final String TAG = "cwenhui";

    public static void e(Object content) {
        if (BuildConfig.DEBUG) {
            log(content != null ? content.toString() : "");
        }
    }

    public static void log(String content) {
        Log.e(TAG, content);
    }
}
