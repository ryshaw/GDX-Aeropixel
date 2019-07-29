package gdx.aeropixel;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

public class Enemy extends Entity implements Pool.Poolable {
	private float timeBetweenShots;

	Enemy() { super(); }

	void init(float x, float y, float dir) {
		sprite = new Sprite(EntitySystem.getTexture("enemy"));
		sprite.setCenter(x, y);
		position = new Vector2(x, y);
		sprite.rotate(dir);
		direction = dir;
		createHitbox();
		health = 2;
		timeBetweenShots = 0;
	}

	@Override
	void update(float delta) {
		position = new Vector2(position.x, position.y + 20 * delta);
		sprite.setCenter(position.x, position.y);

		for (Polygon p : hitbox) {
			p.setPosition(position.x, position.y);
		}

		if (health <= 0) destroy();

		timeBetweenShots += delta;
		if (timeBetweenShots > 0.5f) {
			shoot();
			timeBetweenShots = 0;
		}
	}

	private void shoot() {
		Vector2 front = GameScreen.getVelocity(direction, 40, false);
		Bullet b = Pools.obtain(Bullet.class);
		b.init(position.x + front.x, position.y + front.y, direction);
		EntitySystem.addEntity(b);
	}

	@Override
	void destroy() { EntitySystem.removeEntity(this); }


	private void createHitbox() {
		// to get a perfect hitbox, the right x-coordinate must be increased by 1
		// wings: width of 64 & length of 12; fuselage: width of 12 & length of 64
		Polygon wings = new Polygon(new float[]{-32,12, -32,0, 33,0, 33,12});
		wings.translate(position.x, position.y);
		wings.rotate(direction);
		Polygon fuselage = new Polygon(new float[]{-6,32, -6,-32, 7,-32, 7,32});
		fuselage.translate(position.x, position.y);
		fuselage.rotate(direction);
		hitbox = new Polygon[]{wings, fuselage};
	}

	@Override
	public void reset() { sprite.setCenter(0, 0); }
}
