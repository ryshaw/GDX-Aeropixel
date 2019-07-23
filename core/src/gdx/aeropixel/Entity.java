package gdx.aeropixel;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public abstract class Entity {
	Sprite sprite;
	ArrayList<Texture> tex;
	private float dir;
	private Vector2 speed;
	private ArrayList<Rectangle> hitbox;

	Entity() {
		this.tex = new ArrayList<>();
		this.dir = 0;
		this.speed = new Vector2();
		this.hitbox = new ArrayList<>();
	}

	void update(float deltaTime) {}

	void draw(SpriteBatch batch) { sprite.draw(batch); }

	void dispose() {}

	public Vector2 getPosition() {
		float x = sprite.getX() + sprite.getWidth() / 2;
		float y = sprite.getY() + sprite.getHeight() / 2;
		return new Vector2(x, y);
	}

	@Override
	public String toString() {
		String s = "" + this.getClass();
		return s.substring(s.lastIndexOf(".") + 1);
	}
}