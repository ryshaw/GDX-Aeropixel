package gdx.aeropixel;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

class Bullet {
    Sprite sprite;
    private float x, y, direction;
    private int speed = 20;

    Bullet(float x, float y, float direction) {
        sprite = new Sprite(new Texture("bullet.png"));
        this.x = x;
        this.y = y;
        this.direction = direction;
        sprite.setCenter(x, y);
    }

    void update() {
        float dirX = speed * (float) Math.sin(Math.toRadians(direction));
        float dirY = speed * (float) Math.cos(Math.toRadians(direction));
        sprite.setCenter(x + dirX, y + dirY);
    }

    void dispose() {
        sprite.getTexture().dispose();
    }
}
