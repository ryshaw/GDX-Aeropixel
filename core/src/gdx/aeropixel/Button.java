package gdx.aeropixel;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

class Button {
	private Rectangle rect;
	private SpriteBatch batch;
	private boolean touched, clicked;
	boolean active;
	private BitmapFont font;
	Command cmd;
	private Vector2 offset;

	Button(float x, float y, float w, float h, SpriteBatch b, BitmapFont f) {
		this.rect = new Rectangle(x, y, w, h);
		this.batch = b;
		this.font = f;
		this.offset = new Vector2(0, 0);
		this.active = true;
	}

	void update(String text) {
		this.touched = false;
		this.clicked = false;

		if (!active) return;

		Vector2 p = GameInput.getMousePos();
		boolean c = GameInput.getMouseClicked();
		if (this.rect.contains(p)) {
			this.touched = true;
		}
		if (touched && c) {
			this.clicked = true;
		}

		this.font.draw(batch, text, rect.getX() + offset.x, rect.getY() + offset.y + (rect.getHeight()*0.8f));
	}

	void offset(float x, float y) {
		this.offset = new Vector2(x, y);
	}

	float getX() {
		return this.rect.getX();
	}

	float getY() {
		return this.rect.getY();
	}

	float getHeight() {
		return this.rect.getHeight();
	}

	boolean isTouched() {
		return touched;
	}

	boolean isClicked() {
		return clicked;
	}
}
