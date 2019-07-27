package gdx.aeropixel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pools;

import java.util.ArrayList;

class Player extends Entity {
	static Polygon hitbox;
	private static Vector2 position = new Vector2(400, 52); // locks plane
	private static Vector3 cameraCenter = new Vector3(400, 52, 0); // locks camera
	private static float rotation = 0;

	private static int[] speed = {20, 40}; // sets min and max speed

	private static float rotTime = 0; // 0 = neutral, 1 = left, -1 = right
	private static float speedTime = 0; // 0 = neutral, 1 = speedup
	private static float timeBetweenShots = 0;

	Player() {
		createSprites();
		sprite = new Sprite(tex.get(4));
		sprite.setCenter(position.x, position.y);
		createPolygon();
	}

	@Override
	void update(float delta) {
		ArrayList keysDown = GameInput.getKeyInput();

		if (keysDown.contains("D")) {
			rotTime -= 2 * Gdx.graphics.getDeltaTime();
		} else if (keysDown.contains("A")) {
			rotTime += 2 * Gdx.graphics.getDeltaTime();
		} else {
			if (MathUtils.isZero(rotTime, 0.02f)) {
				rotTime = 0;
			} else {
				rotTime += -1 * Math.signum(rotTime) * 2 * Gdx.graphics.getDeltaTime();
			}
		}

		if (keysDown.contains("W")) {
			speedTime += 0.6*Gdx.graphics.getDeltaTime();
		} else {
			speedTime -= 0.6*Gdx.graphics.getDeltaTime();
		}

		if (keysDown.contains("Space") && timeBetweenShots > 0.2) {
			shoot();
			timeBetweenShots = 0;
		}

		rotate();
		move();
		sprite.setRegion(chooseSprite());

		timeBetweenShots += Gdx.graphics.getDeltaTime();
	}

	//polygon = new Polygon(new float[]{0,0,bounds.width,0,bounds.width,bounds.height,0,bounds.height,0,0});


	private void rotate() {
		rotTime = MathUtils.clamp(rotTime, -1, 1);
		float rotSpeed = MathUtils.lerp(0, 100, rotTime); // 100 degrees per second

		float rotationDelta = rotSpeed * Gdx.graphics.getDeltaTime();
		rotation += rotationDelta;
		GameScreen.camera.rotateAround(cameraCenter, new Vector3(0, 0, 1), rotationDelta);
		sprite.setRotation(rotation);
		hitbox.setRotation(rotation);
	}

	private void move() {
		speedTime = MathUtils.clamp(speedTime, 0, 1);
		float moveSpeed = MathUtils.lerp(speed[0], speed[1], speedTime);

		Vector2 delta = GameScreen.getVelocity(rotation, moveSpeed, true);
		Vector2 push = GameScreen.getVelocity(rotation, 10 * speedTime, false);

		GameScreen.camera.translate(delta.x, delta.y);
		cameraCenter.add(delta.x, delta.y, 0);
		position.set(cameraCenter.x + push.x, cameraCenter.y + push.y);
		sprite.setCenter(position.x, position.y);
		hitbox.translate(delta.x, delta.y);
	}

	private void shoot() {
		Vector2 front = GameScreen.getVelocity(rotation, 40, false);
		Bullet b = Pools.obtain(Bullet.class);
		b.init(position.x + front.x, position.y + front.y, rotation);
		EntitySystem.addEntity(b);
	}

	static Vector2 getPos() { return position; }

	private void createSprites() {
		// 0-3: left, 4: default, 5-8: right
		for (int i = 4; i > 0; i--) {
			tex.add(EntitySystem.getTexture("left_" + i));
		}
		tex.add(EntitySystem.getTexture("plane"));
		for (int i = 1; i < 5; i++) {
			tex.add(EntitySystem.getTexture("right_" + i));
		}
	}

	private Texture chooseSprite() {
		// use rotation to determine which sprite
		int index = Math.round(8 - (rotTime + 1) * 4);
		return tex.get(index);
	}
	
	private void createPolygon() {
		float[] vertices = new float[]{0,44, 26,44, 26,64, 39,64, 39,44, 64,44, 64,32, 39,32, 39,10,
			44,10, 44,0, 20,0, 20,10, 26,10, 26,32, 0,32, 0,44};
		hitbox = new Polygon(vertices);
		hitbox.translate(position.x - 32, position.y - 32);
	}

}
