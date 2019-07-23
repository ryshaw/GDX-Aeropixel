package gdx.aeropixel;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

class TextLabel extends Actor {
	private BitmapFont font;
	private String text;

	TextLabel(String t, float x, float y, int fontSize) {
		switch (fontSize) {
			case 1:
				this.font = Aeropixel.smallFont;
				break;
			case 2:
				this.font = Aeropixel.mediumFont;
				break;
			case 3:
				this.font = Aeropixel.largeFont;
				break;
		}
		this.text = t;
		setBounds(x, y, 0, 0);
	}

	public void act(float delta) { super.act(delta); }

	void setText(String t) {
		this.text = t;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) { this.font.draw(batch, this.text, this.getX(), this.getY()); }

}
