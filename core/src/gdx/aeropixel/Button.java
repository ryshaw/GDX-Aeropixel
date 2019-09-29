package gdx.aeropixel;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

class Button extends Actor {
	private BitmapFont font;
	private String text;
	private Sprite plane;
	private boolean mouseover;

	Button(String t, float x, float y, float w, float h, int fontSize) {
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
		setBounds(x, y, w, h);

		ClickListener onClick = new ClickListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Button b = (Button) event.getTarget();
				StartStage s = (StartStage) b.getParent().getStage();
				s.clicked(b.text);
				return true;
			}

			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) { mouseover = true; }

			public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) { mouseover = false; }
		};
		addListener(onClick);

		plane = new Sprite(new Texture("images/player/plane.png"));
		plane.rotate(270);
		plane.setCenter(x - 40, y + h/2);
		mouseover = false;
	}

	public void act(float delta) { super.act(delta); }

	@Override
	public void draw(Batch batch, float parentAlpha) {
		this.font.draw(batch, this.text, this.getX(), this.getY() + (this.getHeight()*0.8f));
		// this.getHeight()*0.8f so that text is inside button
		if (mouseover) this.plane.draw(batch);
	}

	void dispose() { this.plane.getTexture().dispose(); }
}
