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

	Button(float x, float y, float w, float h, String t, SpriteBatch b, BitmapFont f, Command c) {
		this.rect = new Rectangle(x, y, w, h);
		this.text = t;
		this.batch = b;
		this.font = f;
		this.cmd = c;
	}

	void update() {
		this.touched = false;
		this.clicked = false;

		Vector2 p = MenuInput.getMousePos();
		boolean c = MenuInput.getMouseClicked();
		if (this.rect.contains(p)) {
			this.touched = true;
			System.out.println("mouseover");
		}
		if (touched && c) {
			this.clicked = true;
		}

		this.font.draw(batch, text, rect.getX(), rect.getY());
	}

	void offset(float x, float y) {
		this.rect.setPosition(rect.getX() + x, rect.getY() + y);
	}

	Vector2 getPosition() {
		return new Vector2(this.rect.getX(), this.rect.getY());
	}

	boolean isTouched() {
		return touched;
	}

	boolean isClicked() {
		return clicked;
	}
}
