package gdx.aeropixel;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

class Bullet extends Entity implements Pool.Poolable {

    Bullet() {
        super();
    }

    void init(float x, float y, float dir) {
        sprite = new Sprite(EntitySystem.getTexture("bullet"));
        sprite.setCenter(x, y);
        sprite.rotate(dir);
    }

    @Override
    void update(float dT) {
        Vector2 delta = GameScreen.getVelocity(sprite.getRotation(), 2000, true);
        sprite.translate(delta.x, delta.y);
        if (Math.abs(sprite.getX()) > GameScreen.MAP_SIZE.y || Math.abs(sprite.getY()) > GameScreen.MAP_SIZE.y) {
            EntitySystem.removeEntity(this);
        }
    }

    @Override
    public void reset() { sprite.setCenter(0, 0); }
}