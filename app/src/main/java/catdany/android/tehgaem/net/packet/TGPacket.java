package catdany.android.tehgaem.net.packet;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by CatDany on 28.05.2016.
 */
public abstract class TGPacket {

    /**
     * Serialize this packet to JSON format
     * @return
     */
    public abstract JSONObject handleJsonSerialization() throws JSONException;

    /**
     * Called when a packet is received
     */
    public abstract void handleReception();

    public final JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("PacketType", getClass().getName());
        json.put("PacketInfo", handleJsonSerialization());
        return json;
    }

    @SuppressWarnings("unused")
    public static TGPacket handleJsonDeserialization(JSONObject json) throws JSONException {
        return null;
    }

    /**
     * Get packet object from JSON
     * @param json Serialized packet
     * @return Deserialized packet
     * @throws JSONException JSON parse error occurred
     * @throws ClassNotFoundException PacketType in JSONObject is not a valid class
     * @throws IllegalAccessException Unable to access {@link #handleJsonDeserialization(JSONObject)} method for the packet class
     * @throws NoSuchMethodException Method {@link #handleJsonDeserialization(JSONObject)} was not declared in the packet class
     * @throws InvocationTargetException {@link JSONException} occurred during {@link #handleJsonDeserialization(JSONObject)}
     * @throws ClassCastException
     */
    public static TGPacket fromJson(JSONObject json) throws JSONException, ClassNotFoundException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, ClassCastException {
        Class<? extends TGPacket> clazz = Class.forName(json.getString("PacketType")).asSubclass(TGPacket.class);
        Method deserializeMethod = clazz.getDeclaredMethod("handleJsonDeserialization", JSONObject.class);
        return (TGPacket)deserializeMethod.invoke(null, json.getJSONObject("PacketInfo"));
    }
}
