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
    }

    @Override
    void update(float dT) {
        Vector2 delta = GameScreen.getVelocity(direction, 400, true);
        position.add(delta.x, delta.y);
        sprite.setCenter(position.x, position.y);
        for (Polygon p : hitbox) {
            p.setPosition(position.x, position.y);
        }
        if (Math.abs(sprite.getX()) > GameScreen.MAP_SIZE.y || Math.abs(sprite.getY()) > GameScreen.MAP_SIZE.y) {
            this.destroy();
        }
    }

    @Override
    void handleCollision(Entity e) {
        e.health -= 1;
        System.out.println(e + " " + e.health);
        this.destroy();
    }

    @Override
    void destroy() { EntitySystem.removeEntity(this); }

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