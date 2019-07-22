package gdx.aeropixel;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import java.util.ArrayList;

class EntitySystem {
	private static EntitySystem sys;
	private AssetManager manager;
	private SpriteBatch batch;

	private ArrayList<Entity> entities, toAdd, toRemove;

	private EntitySystem(AssetManager m, SpriteBatch b) {
		entities = new ArrayList<>();
		toAdd = new ArrayList<>();
		toRemove = new ArrayList<>();
		this.manager = m;
		this.batch = b;
	}

	static void init(AssetManager m, SpriteBatch b) { sys = new EntitySystem(m, b); }

	static void addEntity(Entity e) { sys.toAdd.add(e); }

	static void removeEntity(Entity e) { sys.toRemove.add(e); }

	static void update(float deltaTime) { sys.updateSys(deltaTime); }

	private void updateSys(float deltaTime) {
		for (Entity entity : entities) {
			entity.update(deltaTime);
		}
		entities.addAll(toAdd);
		toAdd.clear();
		entities.removeAll(toRemove);
		for (Entity r : toRemove) {
			if (r instanceof Pool.Poolable) {
				Pools.free(r);
			}
		}
		toRemove.clear();
	}

	static void draw() { sys.drawSys(); }

	private void drawSys() {
		for (Entity e : entities) { e.draw(batch); } }

	static Texture getTexture(String path) { return sys.manager.get("images/" + path + ".png"); }
}
