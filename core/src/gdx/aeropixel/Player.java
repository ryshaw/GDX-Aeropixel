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
    private static float rotation = 0;

    private static float moveSpeed = 4;

    private static float rotTime = 0;
    private static float speedTime = 0;

    private static Array<Bullet> bullets = new Array<Bullet>();


    Player() {
        rectangle = new Rectangle(position.x - size/2, position.y - size/2, size, size);
        sprite = new Sprite(new Texture("plane.png"));
        sprite.setCenter(position.x, position.y);
        bullets = new Array<Bullet>();
    }


    static void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            rotTime -= 2*Gdx.graphics.getDeltaTime();
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            rotTime += 2*Gdx.graphics.getDeltaTime();
        } else {
            if (MathUtils.isZero(rotTime, 0.02f)) {
                rotTime = 0;
            } else {
                rotTime += -1 * Math.signum(rotTime) * 2 * Gdx.graphics.getDeltaTime();
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            speedTime += 0.6*Gdx.graphics.getDeltaTime();
        } else {
            speedTime -= 0.6*Gdx.graphics.getDeltaTime();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            shoot();
        }

        rotate();
        move();
    }

    private static void rotate() {
        rotTime = MathUtils.clamp(rotTime, -1, 1);
        float rotSpeed = MathUtils.lerp(0, 2, rotTime); // min = -2, max = 2

        rotation += rotSpeed;
        GameScreen.camera.rotateAround(cameraCenter, new Vector3(0, 0, 1), rotSpeed);
        sprite.setRotation(rotation);
    }

    private static void move() {
        speedTime = MathUtils.clamp(speedTime, 0, 1);
        moveSpeed = MathUtils.lerp(4, 10, speedTime);

        float dirX = (float) Math.sin(Math.toRadians(rotation));
        float dirY = (float) Math.cos(Math.toRadians(rotation));
        Vector2 delta = new Vector2(-moveSpeed * dirX, moveSpeed * dirY);
        Vector2 push = new Vector2(-16 * speedTime * dirX, 16 * speedTime * dirY);

        GameScreen.camera.translate(delta.x, delta.y);
        cameraCenter.add(delta.x, delta.y, 0);
        position.set(cameraCenter.x + push.x, cameraCenter.y + push.y);
        sprite.setCenter(position.x, position.y);
    }

    private static void shoot() {
        //TODO: fix bullets
        GameScreen.addProjectile(new Bullet(position.x, position.y, rotation));
    }

    static float getMoveSpeed() {
        return moveSpeed;
    }

    static void dispose() {
        sprite.getTexture().dispose();
        for (Bullet b : bullets) {
            b.dispose();
        }
    }
}
