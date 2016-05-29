package catdany.android.tehgaem.net.packet;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by CatDany on 29.05.2016.
 */
public class BulletPacket extends TGPacket {

    public final double targetX;
    public final double targetY;

    public BulletPacket(double targetX, double targetY) {
        this.targetX = targetX;
        this.targetY = targetY;
    }

    @Override
    public void handleReception() {}

    @Override
    public JSONObject handleJsonSerialization() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("TargetX", targetX);
        json.put("TargetY", targetY);
        return json;
    }

    public static BulletPacket handleJsonDeserialization(JSONObject json) throws JSONException {
        return null;
    }
}
