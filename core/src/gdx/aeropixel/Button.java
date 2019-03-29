package gdx.aeropixel;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Button {
	private Rectangle rect;
	private String text;
	private SpriteBatch batch;
	private boolean touched, clicked;
	private BitmapFont font;
	Command cmd;
	private Vector2 offset;

	Button(float x, float y, float w, float h, String t, SpriteBatch b, BitmapFont f, Command c) {
		this.rect = new Rectangle(x, y, w, h);
		this.text = t;
		this.batch = b;
		this.font = f;
		this.cmd = c;
		this.offset = new Vector2(0, 0);
	}

	void update() {
		this.touched = false;
		this.clicked = false;

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

	Vector2 getPosition() {
		return new Vector2(this.rect.getX(), this.rect.getY());
	}

	Vector2 getSize() {
		return new Vector2(this.rect.getHeight(), this.rect.getWidth());
	}

	boolean isTouched() {
		return touched;
	}

	boolean isClicked() {
		return clicked;
	}
}
