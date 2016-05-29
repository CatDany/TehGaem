package catdany.android.tehgaem.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

import catdany.android.tehgaem.LLog;

/**
 * Created by CatDany on 29.05.2016.
 */
public class EntityClient {

    public final UUID id;
    public final WorldClient world;

    public double posX = 0;
    public double posY = 0;
    public double radius = 0;

    boolean updatedThisTick = false;

    public EntityClient(WorldClient world, UUID id) {
        this.world = world;
        world.addEntity(this);
        this.id = id;
    }

    public boolean remove() {
        return world.removeEntity(this);
    }

    public void update(JSONObject json) throws JSONException {
        this.posX = json.getDouble("PosX");
        this.posY = json.getDouble("PosY");
        this.radius = json.getDouble("Radius");
        this.updatedThisTick = true;
    }
}
