package gdx.aeropixel;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Cloud {
	Sprite sprite;

	Cloud(Texture t, Vector2 p, int r) {
		this.sprite = new Sprite(t);
		this.sprite.rotate(r);
		this.sprite.setCenter(p.x, p.y);
	}
}
