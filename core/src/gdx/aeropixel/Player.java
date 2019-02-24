package gdx.aeropixel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

class Player {
    private static Texture texture;
    private static Rectangle rectangle;
    static Sprite sprite;
    private static Vector2 position = new Vector2(400, 52); // center is (400,52)
    private static Vector3 cameraCenter = new Vector3(400, 52, 0);
    private static float size = 64;
    private static int rotation = 0;

    private static float rotSpeed = 2;
    private static float moveSpeed = 5;

    private static float speedTime = 0;


    Player() {
        texture = new Texture("plane.png");
        rectangle = new Rectangle(position.x - size/2, position.y - size/2, size, size);
        sprite = new Sprite(texture);
        sprite.setCenter(position.x, position.y);
    }


    static void update() {
        //TODO: Rotation speed lerping, similar to with the engine speed.
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            rotation -= rotSpeed;
            Game.camera.rotateAround(cameraCenter, new Vector3(0, 0, 1), -rotSpeed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            rotation += rotSpeed;
            Game.camera.rotateAround(cameraCenter, new Vector3(0, 0, 1), rotSpeed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            speedTime += 0.8*Gdx.graphics.getDeltaTime();
        } else {
            speedTime -= 0.8*Gdx.graphics.getDeltaTime();
        }
        speedTime = MathUtils.clamp(speedTime, 0, 1);
        moveSpeed = MathUtils.lerp(5, 10, speedTime);

        move();
        sprite.setCenter(position.x, position.y);
        sprite.setRotation(rotation);
    }

    private static void move() {
        //TODO: Camera offset for plane when going fast.
        float xDelta = -moveSpeed * (float) Math.sin(Math.toRadians(rotation));
        float yDelta = moveSpeed * (float) Math.cos(Math.toRadians(rotation));
        float xPush = speedTime * (float) Math.sin(Math.toRadians(rotation));
        float yPush = speedTime * (float) Math.cos(Math.toRadians(rotation));
        Vector2 offset = new Vector2(xPush, yPush);

        position.add(xDelta, yDelta);
        Game.camera.translate(xDelta, yDelta);
        cameraCenter.add(xDelta, yDelta, 0);
    }

    static float getMoveSpeed() {
        return moveSpeed;
    }

    static float getRotation() {
        return rotation;
    }

    static Vector2 getCenter() {
        return position;
    }


    static void dispose() {
        texture.dispose();
    }
}
