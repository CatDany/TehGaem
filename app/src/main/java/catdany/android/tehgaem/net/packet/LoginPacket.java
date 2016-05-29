package catdany.android.tehgaem.net.packet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by CatDany on 28.05.2016.
 */
public final class LoginPacket extends TGPacket {

    public final UUID uuid;
    public final String password;

    public LoginPacket(UUID uuid, String password) {
        this.uuid = uuid;
        this.password = password;
    }

    public static LoginPacket handleJsonDeserialization(JSONObject json) throws JSONException {
        return new LoginPacket(
                UUID.fromString(json.getString("UUID")),
                json.getString("Password")
        );
    }

    @Override
    public JSONObject handleJsonSerialization() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("UUID", uuid.toString());
        json.put("Password", password);
        return json;
    }

    @Override
    public void handleReception() {}
}
