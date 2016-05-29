package catdany.android.tehgaem;

import android.widget.Toast;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Android utils
 * Created by CatDany on 28.05.2016.
 */
public class AUtils {

    /**
     * Charset used for encoding/decoding information from socket streams
     */
    public static Charset SOCKET_CHARSET = Charset.forName("UTF-8");
    public static final int SERVER_PORT = 10200;

    /**
     * Display a toast pop-up with a given duration
     * @param duration
     * @param format
     * @param args
     */
    public static void toast(final int duration, final String format, final Object... args) {
        LoginActivity.activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.activity, String.format(format, args), duration).show();
            }
        });
    }

    /**
     * Display a toast pop-up with Toast#LENGTH_SHORT duration
     * @param format
     * @param args
     */
    public static void toastShort(final String format, final Object... args) {
        toast(Toast.LENGTH_SHORT, format, args);
    }

    /**
     * Display a toast pop-up with Toast#LENGTH_LONG duration
     * @param format
     * @param args
     */
    public static void toastLong(final String format, final Object... args) {
        toast(Toast.LENGTH_LONG, format, args);
    }
}
