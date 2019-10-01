package gdx.aeropixel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class MenuScreen implements Screen {
  	private final Aeropixel game;
	private final Sprite plane;
	private OrthographicCamera camera;
	private MenuStage stage;

	 MenuScreen(final Aeropixel game) {
		this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Aeropixel.WINDOW_SIZE.x, Aeropixel.WINDOW_SIZE.y);

        stage = new MenuStage(this);
        Gdx.input.setInputProcessor(stage);
        plane = new Sprite(new Texture("images/plane_large.png"));
        plane.setCenter(340, 60);
	 }


	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		plane.draw(game.batch);
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