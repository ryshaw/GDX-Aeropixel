package gdx.aeropixel;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Pool;

public class Enemy extends Entity implements Pool.Poolable {

	Enemy() { super();	}

	void init(float x, float y, float dir) {
		sprite = new Sprite(EntitySystem.getTexture("enemy"));
		sprite.setCenter(x, y);
		sprite.rotate(dir);
	}

	@Override
	void update(float delta) {
		sprite.translateY(200 * delta);
	}

	@Override
	public void reset() { sprite.setCenter(0, 0); }
}
