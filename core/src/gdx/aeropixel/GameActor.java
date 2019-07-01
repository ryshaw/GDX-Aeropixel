package gdx.aeropixel;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GameActor extends Actor {
	private Texture t;

	public GameActor() {
		setBounds(0, 0, 800, 640);
		t = new Texture("images/plane.png");
	}

	@Override
	public void draw (Batch batch, float parentAlpha) {
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		batch.draw(t, 0, 0);
	}
}
