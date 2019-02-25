package gdx.aeropixel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class MainScreen implements Screen {
  	private final Aeropixel game;
	private Texture cloud1, cloud2;
    private static final Vector2 WINDOW_SIZE = new Vector2(700, 600);
   	private Array<Vector2> clouds = new Array<Vector2>(); // create a sea of clouds

    private OrthographicCamera camera;


	 MainScreen(final Aeropixel game) {
		this.game = game;

        cloud1 = new Texture("cloud1.png");
        cloud2 = new Texture("cloud2.png");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, WINDOW_SIZE.x, WINDOW_SIZE.y);

        for (int i = 0; i < 4; i++) {
            float randomX = MathUtils.random(0, WINDOW_SIZE.x);
            float randomY = MathUtils.random(0, WINDOW_SIZE.y);

            Vector2 v = new Vector2(randomX, randomY);
            clouds.add(v);
        }
	 }


	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        for (int i = 0; i < 2; i++) {
            game.batch.draw(cloud1, clouds.get(i).x, clouds.get(i).y);
            game.batch.draw(cloud2, clouds.get(2+i).x, clouds.get(2+i).y);
        }

        String s1 = "Aeropixel";
        String s2 = "Press Space to begin";
        game.titleFont.draw(game.batch, s1, 60, 560);
        game.titleFont.draw(game.batch, s2, 60, 100);

        game.batch.end();

        camera.update();

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            game.currentScreen = new GameScreen(game);
            game.setScreen(game.currentScreen);
            this.dispose();
        }
	}


	@Override
	public void dispose() {
        cloud1.dispose();
        cloud2.dispose();
	}


    @Override
   	public void resize(int width, int height) {
   	}

   	@Override
   	public void show() {
   	}

   	@Override
   	public void hide() {
   	}

   	@Override
   	public void pause() {
   	}

   	@Override
   	public void resume() {
   	}

}