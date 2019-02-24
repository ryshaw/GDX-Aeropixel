package gdx.aeropixel;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


public class Game extends ApplicationAdapter {
	private SpriteBatch batch;
	private BitmapFont debugFont;
	private Texture cloud1, cloud2;
	static OrthographicCamera camera;
	private static final Vector2 WINDOW_SIZE = new Vector2(800, 640);
	private static final Vector2 MAP_SIZE = new Vector2(-8000, 8000);
	private Array<Vector2> clouds = new Array<Vector2>(); // create a sea of clouds


	@Override
	public void create() {
		batch = new SpriteBatch();
		FreeTypeFontGenerator generator;
		generator = new FreeTypeFontGenerator(Gdx.files.internal("PixelOperator-Bold.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 24;
		parameter.color = Color.BLACK;
		debugFont = generator.generateFont(parameter);
		generator.dispose();

		cloud1 = new Texture("cloud1.png");
		cloud2 = new Texture("cloud2.png");

		camera = new OrthographicCamera();
  		camera.setToOrtho(false, WINDOW_SIZE.x, WINDOW_SIZE.y);

  		for (int i = 0; i < 1000; i++) {
  			float randomX = MathUtils.random(MAP_SIZE.x, MAP_SIZE.y);
			float randomY = MathUtils.random(MAP_SIZE.x, MAP_SIZE.y);

  			Vector2 v = new Vector2(randomX, randomY);
  			clouds.add(v);
		}

		new Player();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.95f, 0.95f, 0.95f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
        for (int i = 0; i < 1000/2; i++) {
        	batch.draw(cloud1, clouds.get(i).x, clouds.get(i).y);
			batch.draw(cloud2, clouds.get(1000/2+i).x, clouds.get(1000/2+i).y);
		}
		Player.sprite.draw(batch);

        String s1 = "Speed: " + Math.round(Player.getMoveSpeed()*10)/10f;
        String s2 = "Rotation: " + Player.getRotation();
        debugFont.draw(batch, s1, Player.sprite.getX() + 80, Player.sprite.getY() + 80);
		debugFont.draw(batch, s2, Player.sprite.getX() + 80, Player.sprite.getY() + 60);
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
		debugFont.dispose();
	}
}
