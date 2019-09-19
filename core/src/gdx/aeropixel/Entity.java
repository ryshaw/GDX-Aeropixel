package gdx.aeropixel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public abstract class Entity {
	Sprite sprite;
	Vector2 position; // always center of sprite
	int health;
	ArrayList<Texture> tex;
	float direction;
	Polygon[] hitbox;
	int[] bounds = {0, 0}; // [x,y]: [0,0] in bounds, [1,0] x too high, [0,-1] y too low

	Entity() {}

	void update(float deltaTime) {}

	void draw(SpriteBatch batch) { sprite.draw(batch); }

	void handleCollision(Entity otherEntity) {}

	void destroy() { EntitySystem.removeEntity(this); }

	void handleOutOfBounds() {
		if (position.x > GameScreen.MAP_SIZE.y) { // x too large
			bounds[0] = 1;
		}
		if (position.x < GameScreen.MAP_SIZE.x) { // x too small
			bounds[0] = -1;
		}
		if (position.y > GameScreen.MAP_SIZE.y) { // y too large
			bounds[1] = 1;
		}
		if (position.y < GameScreen.MAP_SIZE.x) { // y too small
			bounds[1] = -1;
		}
	}

	Vector2 boundDelta(Vector2 d) {
		Vector2 delta = new Vector2(d);
		if (d.x > 0 && bounds[0] == 1) {
			delta.x = 0;
		} else if (d.x < 0 && bounds[0] == -1) {
			delta.x = 0;
		}
		if (d.y > 0 && bounds[1] == 1) {
			delta.y = 0;
		} else if (d.y < 0 && bounds[1] == -1) {
			delta.y = 0;
		}
		return delta;
	}

	Vector2 getVelocity(float direction, float speed, boolean scale) {
		float dirX = (float) Math.sin(Math.toRadians(direction));
		float dirY = (float) Math.cos(Math.toRadians(direction));
		Vector2 delta = new Vector2(-speed * dirX, speed * dirY);
		if (scale) {
			return delta.scl(Gdx.graphics.getDeltaTime());
		} else {
			return delta;
		}
	}


	@Override
	public String toString() {
		String s = "" + this.getClass();
		return s.substring(s.lastIndexOf(".") + 1);
	}
}