package catdany.android.tehgaem;

import android.util.Log;

/**
 * Created by CatDany on 28.05.2016.
 */
public class LLog {

    public static final String LOG_PREFIX = "TehGaem";

    private static String prefix() {
        return LOG_PREFIX + "-" + Thread.currentThread().getName();
    }

    public static void v(String format, Object... args) {
        Log.v(prefix(), String.format(format, args));
    }

    public static void i(String format, Object... args) {
        Log.i(prefix(), String.format(format, args));
    }

    public static void w(String format, Object... args) {
        Log.w(prefix(), String.format(format, args));
    }

    public static void w(Throwable t, String format, Object... args) {
        Log.w(prefix(), String.format(format, args), t);
    }

    public static void e(String format, Object... args) {
        Log.e(prefix(), String.format(format, args));
    }

    public static void e(Throwable t, String format, Object... args) {
        Log.e(prefix(), String.format(format, args), t);
    }
}
