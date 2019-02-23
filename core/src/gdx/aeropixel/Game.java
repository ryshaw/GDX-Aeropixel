package gdx.aeropixel;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Game extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture plane, cloud1, cloud2;
	private OrthographicCamera camera;
	private Rectangle player;
	private float rotation = 0;
	private Sprite sprite;

	@Override
	public void create() {
		batch = new SpriteBatch();
		plane = new Texture("plane.png");
		cloud1 = new Texture("cloud1.png");
		cloud2 = new Texture("cloud2.png");

		camera = new OrthographicCamera();
  		camera.setToOrtho(false, 800, 640);

  		player = new Rectangle();
  		player.x = 800f/2 - 64f/2;
  		player.y = 20;
  		player.width = 64;
  		player.height = 64;

  		sprite = new Sprite(plane);
  		sprite.setPosition(player.x, player.y);

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
        sprite.draw(batch);
		batch.draw(cloud1, 200, 300);
		batch.draw(cloud2, 500, 460);

		batch.end();

		Vector3 center = new Vector3((int) player.x+32,(int) player.y+32,0);
		if (Gdx.input.isKeyPressed(Keys.D)) {
		    rotation -= 1f;
			camera.rotateAround(center, new Vector3(0, 0, 1), -1f);
		}
		if (Gdx.input.isKeyPressed(Keys.A)) {
		    rotation += 1f;
			camera.rotateAround(center, new Vector3(0, 0, 1), 1f);
		}
		if (Gdx.input.isKeyPressed(Keys.W)) {
			sprite.translateY(1);
		}
		if (Gdx.input.isKeyPressed(Keys.S)) {
            sprite.translateY(-1);
		}

		sprite.setRotation(rotation);

	 	camera.update();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		plane.dispose();
		cloud1.dispose();
		cloud2.dispose();
	}
}
