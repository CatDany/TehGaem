package catdany.android.tehgaem.net.packet;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by CatDany on 29.05.2016.
 */
public final class MovementPacket extends TGPacket {

    public final double posX;
    public final double posY;

    public MovementPacket(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
    }

    @Override
    public JSONObject handleJsonSerialization() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("PosX", posX);
        json.put("PosY", posY);
        return json;
    }

    @Override
    public void handleReception() {}

    public static MovementPacket handleJsonDeserialization(JSONObject json) throws JSONException {
        return null;
    }
}
