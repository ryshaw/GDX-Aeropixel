package gdx.aeropixel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

class Player {
    private static Rectangle rectangle;
    static Sprite sprite;
    private static Vector2 position = new Vector2(400, 52);
    private static Vector3 cameraCenter = new Vector3(400, 52, 0);
    private static float size = 64;
    static float rotation = 0;

    private static float moveSpeed = 400;

    private static float rotTime = 0;
    private static float speedTime = 0;
    private static float timeBetweenShots = 0;

    private static Array<Bullet> bullets;


    Player() {
        rectangle = new Rectangle(position.x - size/2, position.y - size/2, size, size);
        sprite = new Sprite(new Texture("plane.png"));
        sprite.setCenter(position.x, position.y);
        bullets = new Array<Bullet>();
    }


    static void update() {
        //TODO: work on input processing. currently only can utilize two buttons at a time
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
            rotTime -= 2 * Gdx.graphics.getDeltaTime();
        } else if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
            rotTime += 2 * Gdx.graphics.getDeltaTime();
        } else {
            if (MathUtils.isZero(rotTime, 0.02f)) {
                rotTime = 0;
            } else {
                rotTime += -1 * Math.signum(rotTime) * 2 * Gdx.graphics.getDeltaTime();
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) {
            speedTime += 0.6*Gdx.graphics.getDeltaTime();
        } else {
            speedTime -= 0.6*Gdx.graphics.getDeltaTime();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && timeBetweenShots > 0.2) {
            shoot();
            timeBetweenShots = 0;
        }

        rotate();
        move();

        timeBetweenShots += Gdx.graphics.getDeltaTime();
    }

    private static void rotate() {
        rotTime = MathUtils.clamp(rotTime, -1, 1);
        float rotSpeed = MathUtils.lerp(0, 100, rotTime); // 100 degrees per second

        float rotationDelta = rotSpeed * Gdx.graphics.getDeltaTime();
        rotation += rotationDelta;
        GameScreen.camera.rotateAround(cameraCenter, new Vector3(0, 0, 1), rotationDelta);
        sprite.setRotation(rotation);
    }

    private static void move() {
        speedTime = MathUtils.clamp(speedTime, 0, 1);
        moveSpeed = MathUtils.lerp(400, 600, speedTime);

        Vector2 delta = GameScreen.getVelocity(rotation, moveSpeed, true);
        Vector2 push = GameScreen.getVelocity(rotation, 10 * speedTime, false);

        GameScreen.camera.translate(delta.x, delta.y);
        cameraCenter.add(delta.x, delta.y, 0);
        position.set(cameraCenter.x + push.x, cameraCenter.y + push.y);
        sprite.setCenter(position.x, position.y);
    }

    private static void shoot() {
        Vector2 front = GameScreen.getVelocity(rotation, 40, false);
        GameScreen.addProjectile(new Bullet(position.x + front.x, position.y + front.y, rotation));
    }

    static void dispose() {
        sprite.getTexture().dispose();
        for (Bullet b : bullets) {
            b.dispose();
        }
    }
}
