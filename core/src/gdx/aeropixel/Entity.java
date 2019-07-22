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
	private Vector2 pos;
	private float dir;
	private Vector2 speed;
	private ArrayList<Rectangle> hitbox;

	Entity() {
		this.tex = new ArrayList<>();
		this.pos = new Vector2();
		this.dir = 0;
		this.speed = new Vector2();
		this.hitbox = new ArrayList<>();
	}

	void update(float deltaTime) {}

	void draw(SpriteBatch batch) { sprite.draw(batch); }

	void dispose() {}

	public Vector2 getPos() { return pos; }

	void setPos(Vector2 pos) { this.pos = pos; }

	public float getDirection() { return dir; }

	public void setDirection(float dir) { this.dir = dir; }

	public Vector2 getSpeed() { return speed; }

	public void setSpeed(Vector2 speed) { this.speed = speed; }

	public ArrayList<Rectangle> getHitbox() { return hitbox; }

	public void setHitbox(ArrayList<Rectangle> hitbox) { this.hitbox = hitbox; }

	@Override
	public String toString() {
		String s = "" + this.getClass();
		return s.substring(s.lastIndexOf(".") + 1);
	}
}