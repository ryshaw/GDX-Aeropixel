package gdx.aeropixel;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

class Bullet extends Entity implements Pool.Poolable {

    Bullet() {  super(); }

    void init(float x, float y, float dir) {
        sprite = new Sprite(EntitySystem.getTexture("bullet"));
        sprite.setCenter(x, y);
        position = new Vector2(x, y);
        sprite.rotate(dir);
        direction = dir;
        createHitbox();
        update(0.001f); // needs to update at least once or will be outpaced by plane
    }

    @Override
    void update(float dT) {
        int speed = 1000;
        Vector2 delta = getVelocity(direction, speed, true);
        position.add(delta.x, delta.y);
        sprite.setCenter(position.x, position.y);

        for (Polygon p : hitbox) {
            p.setPosition(position.x - delta.x, position.y - delta.y); // tuned for high speeds
        }
    }

    @Override
    void handleCollision(Entity e) {
        e.health -= 1;
        System.out.println(e + " " + e.health);
        this.destroy();
    }

    @Override
    void handleOutOfBounds() {
        if (Math.abs(position.x) > GameScreen.MAP_SIZE.y + 1000 || Math.abs(position.y) > GameScreen.MAP_SIZE.y + 1000) {
            this.destroy();
        }
    }

    private void createHitbox() {
        // 2x4 rectangle
        Polygon rect = new Polygon(new float[]{-1,-2, 2,-2, 2,2, -1,2});
        rect.translate(position.x, position.y);
        rect.rotate(direction);
        hitbox = new Polygon[]{rect};
    }

    @Override
    public void reset() { sprite.setCenter(0, 0); }
}