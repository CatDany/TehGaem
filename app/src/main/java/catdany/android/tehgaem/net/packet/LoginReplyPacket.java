package catdany.android.tehgaem.net.packet;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import catdany.android.tehgaem.AUtils;
import catdany.android.tehgaem.LLog;
import catdany.android.tehgaem.LoginActivity;
import catdany.android.tehgaem.entity.WorldClient;
import catdany.android.tehgaem.net.TGClient;

/**
 * Created by CatDany on 29.05.2016.
 */
public final class LoginReplyPacket extends TGPacket {

    public static final int RESULT_LOGGED_IN = 0;
    public static final int RESULT_NEW_ACCOUNT = 1;
    public static final int RESULT_INCORRECT_PASSWORD = -1;
    public static final int RESULT_ALREADY_CONNECTED = -2;

    public final int resultCode;

    public LoginReplyPacket(int resultCode) {
        this.resultCode = resultCode;
    }

    @Override
    public void handleReception() {
        if (resultCode == RESULT_INCORRECT_PASSWORD) {
            AUtils.toastShort("Incorrect password.");
            try {
                TGClient.instance.close();
            } catch (IOException t) {
                LLog.e(t, "Couldn't close socket.");
            }
        } else if (resultCode == RESULT_ALREADY_CONNECTED) {
            AUtils.toastShort("Already connected.");
            try {
                TGClient.instance.close();
            } catch (IOException t) {
                LLog.e(t, "Couldn't close socket.");
            }
        } else {
            WorldClient.instance = new WorldClient();
            LoginActivity.activity.transitionToGame();
        }
    }

    @Override
    public JSONObject handleJsonSerialization() throws JSONException {
        return null;
    }

    public static LoginReplyPacket handleJsonDeserialization(JSONObject json) throws JSONException {
        return new LoginReplyPacket(json.getInt("ResultCode"));
    }
}
