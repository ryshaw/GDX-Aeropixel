package gdx.aeropixel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class StartScreen implements Screen {
  	private final Aeropixel game;
	private OrthographicCamera camera;
	private StartStage stage;

	private ArrayList<Cloud> clouds = new ArrayList<>();
	private int xOffset = 0;


	 StartScreen(final Aeropixel game) {
		this.game = game;



        camera = new OrthographicCamera();
        camera.setToOrtho(false, Aeropixel.WINDOW_SIZE.x, Aeropixel.WINDOW_SIZE.y);

        stage = new StartStage(this);
        Gdx.input.setInputProcessor(stage);

        for (int i = 0; i < 80; i++) {
        	int index = MathUtils.random(0, 4);
        	float x = MathUtils.random(0, 10000);
            float y = MathUtils.random(20, 620);
            int r = MathUtils.random(1, 360);

            clouds.add(new Cloud(game.clouds.get(index), new Vector2(x, y), r));
        }
	 }


	@Override
	public void render(float delta) {
		camera.translate(2, 0);
		xOffset += 2;

		if (xOffset == 10000) {
			xOffset = -800;
			camera.translate(-10800, 0);
		}

		Gdx.gl.glClearColor(1f, 1f, 1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		for (Cloud c : clouds) c.sprite.draw(game.batch);
		game.batch.end();

		stage.act(delta);
		stage.draw();

        camera.update();
    }

    void startGame() {
        game.currentScreen = new GameScreen(game);
        game.setScreen(game.currentScreen);
        this.dispose();
    }

    void quit() { Gdx.app.exit(); }


	@Override
	public void dispose() { stage.dispose(); }

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