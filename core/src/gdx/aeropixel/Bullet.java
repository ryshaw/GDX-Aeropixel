package gdx.aeropixel;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

class Bullet {
    Sprite sprite;
    private float x, y, direction;
    private int speed = 3000;

    Bullet(float x, float y, float direction) {
        sprite = new Sprite(new Texture("images/bullet.png"));
        this.x = x;
        this.y = y;
        this.direction = direction;
        sprite.setCenter(x, y);
        sprite.rotate(direction);
    }

    void update() {
        Vector2 delta = GameScreen.getVelocity(direction, speed, true);
        x += delta.x;
        y += delta.y;
        sprite.setCenter(x, y);
    }

    Vector2 getPosition() { return new Vector2(x, y); }

    void dispose() { sprite.getTexture().dispose(); }
}
