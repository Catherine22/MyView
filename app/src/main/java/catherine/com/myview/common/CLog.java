package catherine.com.myview.common;

import android.util.Log;

/**
 * Created by Catherine on 2016/12/15.
 * Soft-World Inc.
 * catherine919@soft-world.com.tw
 */
@SuppressWarnings("unused")
public class CLog {

    public static String getTag() {
        String tag = "";
        final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        for (int i = 0; i < ste.length; i++) {
            if (ste[i].getMethodName().equals("getTag")) {
                tag = "(" + ste[i + 1].getFileName() + ":" + ste[i + 1].getLineNumber() + ")";
            }
        }
        return tag;
    }


    public static void v(String tab, String message) {
        if (Config.showDebugLog) {
            Log.v(tab, message);
        }
    }

    public static void d(String tab, String message) {
        if (Config.showDebugLog) {
            Log.d(tab, message);
        }
    }

    public static void w(String tab, String message) {
        if (Config.showDebugLog) {
            Log.w(tab, message);
        }
    }

    public static void e(String tab, String message) {
        if (Config.showDebugLog) {
            Log.e(tab, message);
        }
    }

    public static void i(String tab, String message) {
        if (Config.showDebugLog) {
            Log.i(tab, message);
        }
    }

    public static class out {
        public static void println(String message) {
            if (Config.showDebugLog) {
                System.out.println(message);
            }
        }
    }
}
