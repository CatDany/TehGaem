package catdany.android.tehgaem.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import catdany.android.tehgaem.LLog;

/**
 * Created by CatDany on 29.05.2016.
 */
public class WorldClient {

    public static WorldClient instance;

    public ArrayList<EntityClient> entityList = new ArrayList<EntityClient>();

    public WorldClient() {
        //
    }

    public void update(JSONObject json) throws JSONException {
        for (EntityClient i : entityList) {
            i.updatedThisTick = false;
            LLog.v("ALL %s", i);
        }
        JSONArray jsonEntities = json.getJSONArray("Entities");
        for (int i = 0; i < jsonEntities.length(); i++) {
            JSONObject jsonEntity = jsonEntities.getJSONObject(i);
            UUID iId = UUID.fromString(jsonEntity.getString("UUID"));
            EntityClient entity = findEntity(iId);
            if (entity == null) {
                entity = new EntityClient(this, iId);
            }
            entity.update(jsonEntity);
            LLog.v("UPDATED %s", entity);
        }
        for (EntityClient i : (ArrayList<EntityClient>)entityList.clone()) {
            if (!i.updatedThisTick) {
                i.remove();
                LLog.v("REMOVED %s", i);
            }
        }
    }

    public void addEntity(EntityClient entity) {
        entityList.add(entity);
    }

    public boolean removeEntity(EntityClient entity) {
        return entityList.remove(entity);
    }

    /**
     * Find entity for given UUID
     * @param id
     * @return {@link EntityClient} with specified UUID, or <code>null</code> if it has not been found
     */
    public EntityClient findEntity(UUID id) {
        for (EntityClient i : entityList) {
            if (i.id.equals(id)) {
                return i;
            }
        }
        return null;
    }
}
