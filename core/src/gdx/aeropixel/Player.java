package gdx.aeropixel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
    private static float moveSpeed = 6;


    Player() {
        texture = new Texture("plane.png");
        rectangle = new Rectangle(position.x - size/2, position.y - size/2, size, size);
        sprite = new Sprite(texture);
        sprite.setCenter(position.x, position.y);
    }


    static void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            rotation -= rotSpeed;
            Game.camera.rotateAround(cameraCenter, new Vector3(0, 0, 1), -rotSpeed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            rotation += rotSpeed;
            Game.camera.rotateAround(cameraCenter, new Vector3(0, 0, 1), rotSpeed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            float xDelta = -moveSpeed * (float) Math.sin(Math.toRadians(rotation));
            float yDelta = moveSpeed * (float) Math.cos(Math.toRadians(rotation));

            position.add(xDelta, yDelta);
            Game.camera.translate(xDelta, yDelta);
            cameraCenter.add(xDelta, yDelta, 0);
        }

        sprite.setCenter(position.x, position.y);
        sprite.setRotation(rotation);
    }


    static void dispose() {
        texture.dispose();
    }
}
