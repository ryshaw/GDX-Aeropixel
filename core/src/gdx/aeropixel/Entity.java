package gdx.aeropixel;

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

	Entity() {}

	void update(float deltaTime) {}

	void draw(SpriteBatch batch) { sprite.draw(batch); }

	void handleCollision(Entity otherEntity) {}

	void destroy() { EntitySystem.removeEntity(this); }


	@Override
	public String toString() {
		String s = "" + this.getClass();
		return s.substring(s.lastIndexOf(".") + 1);
	}
}