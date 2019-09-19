package gdx.aeropixel;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import java.util.ArrayList;

public class Enemy extends Entity implements Pool.Poolable {
	private float timeBetweenShots;
	private float rotTime = 0; // 0 = neutral, 1 = left, -1 = right
	EnemyState state = EnemyState.FORWARD;

	Enemy() { super(); }

	void init(float x, float y, float dir) {
		createSprites();
		sprite = new Sprite(tex.get(4)); // neutral pose
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
		if (health <= 0) destroy();

		switch (state) {
			case FORWARD: // go back to moving forward
				if (MathUtils.isZero(rotTime, 0.02f)) {
					rotTime = 0;
				} else {
					rotTime += -1 * Math.signum(rotTime) * 2 * delta;
				}
				break;
			case LEFT: // turn left
				rotTime += 2 * delta;
				break;
			case RIGHT: // turn right
				rotTime -= 2 * delta;
				break;
		}

		timeBetweenShots += delta;
		if (timeBetweenShots > 0.2f) {
			shoot();
			timeBetweenShots = 0;
		}

		move();
		rotate(delta);

		sprite.setRegion(chooseSprite());
		state = chooseState(state);
		bounds[0] = 0; bounds[1] = 0;
	}

	private void move() {
		int speed = 200;
		Vector2 delta = getVelocity(direction, speed, true);
		delta = boundDelta(delta);
		position.add(delta);
		sprite.setCenter(position.x, position.y);
		for (Polygon p : hitbox) {
			p.setPosition(position.x - delta.x, position.y - delta.y); // tuned for high speeds
		}
	}

	private void rotate(float delta) {
		rotTime = MathUtils.clamp(rotTime, -1, 1);
		float rotSpeed = MathUtils.lerp(0, 100, rotTime); // 100 degrees per second

		float rotationDelta = rotSpeed * delta;
		direction += rotationDelta;
		sprite.setRotation(direction);
		for (Polygon p : hitbox) {
			p.setRotation(direction);
		}
		float rotScale = Math.abs(rotTime) / 2;
		hitbox[0].setScale(1 - rotScale, 1); // scales wing hitbox
	}

	private void shoot() {
		Vector2 front = getVelocity(direction, 40, false);
		Bullet b = Pools.obtain(Bullet.class);
		b.init(position.x + front.x, position.y + front.y, direction);
		EntitySystem.addEntity(b);
	}

	private EnemyState chooseState(EnemyState oldState) {
		float r = MathUtils.random(0f, 1f);
		switch (oldState) {
			case FORWARD:
				if (between(r, 0, 0.98f)) {
					return EnemyState.FORWARD;
				} else if (between(r, 0.98f, 0.99f)) {
					return EnemyState.LEFT;
				} else if (between(r, 0.99f, 1)){
					return EnemyState.RIGHT;
				}
				break;
			case LEFT:
				if (between(r, 0, 0.97f)) {
					return EnemyState.LEFT;
				} else if (between(r, 0.97f, 1)) {
					return EnemyState.FORWARD;
				}
				break;
			case RIGHT:
				if (between(r, 0, 0.97f)) {
					return EnemyState.RIGHT;
				} else if (between(r, 0.97f, 1)) {
					return EnemyState.FORWARD;
				}
				break;
		}
		return oldState;
	}

	private boolean between(float i, float min, float max) {
		return (i >= min && i < max); // inclusive start, exclusive end
	}

	private void createSprites() {
		tex = new ArrayList<>();
		// 0-3: left, 4: default, 5-8: right
		for (int i = 4; i > 0; i--) {
			tex.add(EntitySystem.getTexture("enemy_left_" + i));
		}
		tex.add(EntitySystem.getTexture("enemy"));
		for (int i = 1; i < 5; i++) {
			tex.add(EntitySystem.getTexture("enemy_right_" + i));
		}
	}

	private Texture chooseSprite() {
		// use rotation to determine which sprite
		int index = Math.round(8 - (rotTime + 1) * 4);
		return tex.get(index);
	}

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
