package gdx.aeropixel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

class Bullet {
    Sprite sprite;
    private float x, y, direction;
    private int speed = 100;

    Bullet(float x, float y, float direction) {
        sprite = new Sprite(new Texture("bullet.png"));
        this.x = x;
        this.y = y;
        this.direction = direction;
        sprite.setCenter(x, y);
        sprite.rotate(direction);
    }

    void update() {
        float dirX = -speed * (float) Math.sin(Math.toRadians(direction));
        float dirY = speed * (float) Math.cos(Math.toRadians(direction));
        x += dirX;
        y += dirY;
        sprite.setCenter(x, y);
    }

    void dispose() {
        sprite.getTexture().dispose();
    }
}
