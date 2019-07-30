package gdx.aeropixel;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
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

	static ArrayList<Entity> getEntities() { return sys.entities; }

	static void init(AssetManager m, SpriteBatch b) { sys = new EntitySystem(m, b); }

	static void addEntity(Entity e) { sys.toAdd.add(e); }

	static void removeEntity(Entity e) { sys.toRemove.add(e); }

	static void update(float deltaTime) { sys.updateSys(deltaTime); }

	private void updateSys(float deltaTime) {
		for (Entity entity : entities) {
			entity.update(deltaTime);
			checkCollisions(entity);
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

	static Enemy getEnemy() { return sys.getEnemySys(); }

	private Enemy getEnemySys() {
		for (Entity e : entities) {
			if (e instanceof Enemy) {
				return (Enemy) e;
			}
		}
		return null;
	}

	private void checkCollisions(Entity e1) {
		for (Entity e2 : entities) {
			if (!e1.equals(e2)) {
				for (Polygon p1 : e1.hitbox) {
					for (Polygon p2 : e2.hitbox) {
						if (Intersector.overlapConvexPolygons(p1, p2)) {
							e1.handleCollision(e2);
						}
					}
				}
			}
		}
	}

}
