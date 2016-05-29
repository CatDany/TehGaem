package catdany.android.tehgaem.net.packet;

import org.json.JSONException;
import org.json.JSONObject;

import catdany.android.tehgaem.GamePlaceholderActivity;
import catdany.android.tehgaem.GameView;
import catdany.android.tehgaem.LLog;
import catdany.android.tehgaem.entity.WorldClient;

/**
 * Created by CatDany on 29.05.2016.
 */
public final class WorldSyncPacket extends TGPacket {

    public final JSONObject worldInfo;

    public WorldSyncPacket(JSONObject worldInfo) {
        this.worldInfo = worldInfo;
    }

    @Override
    public JSONObject handleJsonSerialization() throws JSONException {
        return null;
    }

    @Override
    public void handleReception() {
        if (WorldClient.instance != null && GamePlaceholderActivity.instance != null) {
            try {
                WorldClient.instance.update(worldInfo);
                GameView.invalidateOnUI();
            } catch (JSONException t) {
                LLog.e(t, "Couldn't parse WorldSyncPacket.");
            }
        }
    }

    public static WorldSyncPacket handleJsonDeserialization(JSONObject json) throws JSONException {
        return new WorldSyncPacket(json);
    }
}
