package gdx.aeropixel;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


public class Game extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture cloud1, cloud2;
	static OrthographicCamera camera;
	private static final Vector2 WINDOW_SIZE = new Vector2(800, 640);
	private Array<Vector2> clouds = new Array<Vector2>(); // create a sea of clouds


	@Override
	public void create() {
		batch = new SpriteBatch();
		cloud1 = new Texture("cloud1.png");
		cloud2 = new Texture("cloud2.png");

		camera = new OrthographicCamera();
  		camera.setToOrtho(false, WINDOW_SIZE.x, WINDOW_SIZE.y);

  		new Player();

  		for (int i = 0; i < 500; i++) {
  			Vector2 v = new Vector2(MathUtils.random(-6000, 6000), MathUtils.random(-6000, 6000));
  			clouds.add(v);
		}
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
        Player.sprite.draw(batch);

        for (int i = 0; i < 250; i++) {
        	batch.draw(cloud1, clouds.get(i).x, clouds.get(i).y);
			batch.draw(cloud2, clouds.get(250+i).x, clouds.get(250+i).y);
		}

		batch.end();

		Player.update();
	 	camera.update();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		cloud1.dispose();
		cloud2.dispose();
		Player.dispose();
	}
}
