package gdx.aeropixel;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

class ImageLabel extends Actor {
	private Sprite image;

	ImageLabel(String img, float x, float y) {
		image = new Sprite(new Texture("images/" + img + ".png"));
		image.setCenter(x, y);
		setBounds(x, y, 0, 0);
	}

	public void act(float delta) { super.act(delta); }

	void setImage(String img) {
		image.getTexture().dispose();
		image.setTexture(new Texture("images/" + img + ".png"));
	}

	@Override
	public void draw(Batch batch, float parentAlpha) { this.image.draw(batch); }

	void dispose() { this.image.getTexture().dispose(); }

}
