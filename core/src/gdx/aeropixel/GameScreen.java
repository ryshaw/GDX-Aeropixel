package gdx.aeropixel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class GameScreen implements Screen {
  	private final Aeropixel game;
   	static final Vector2 MAP_SIZE = new Vector2(-8000, 8000);
   	private ArrayList<Cloud> clouds = new ArrayList<>();
   	private GameStage stage = new GameStage(this);

    static OrthographicCamera camera;
    private ShapeRenderer renderer = new ShapeRenderer();

	GameScreen(final Aeropixel game) {
		this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Aeropixel.WINDOW_SIZE.x, Aeropixel.WINDOW_SIZE.y);

		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(new GameInput());
		Gdx.input.setInputProcessor(multiplexer);

		for (int i = 0; i < 1000; i++) {
			int index = MathUtils.random(0, 4);
			float x = MathUtils.random(MAP_SIZE.x, MAP_SIZE.y);
			float y = MathUtils.random(MAP_SIZE.x, MAP_SIZE.y);
			int r = MathUtils.random(1, 360);

			clouds.add(new Cloud(game.clouds.get(index), new Vector2(x, y), r));
		}

		EntitySystem.init(game.manager, game.batch);
        EntitySystem.addEntity(new Player());
		Enemy enemy1 = new Enemy();
        enemy1.init(200, 400, 0);
        EntitySystem.addEntity(enemy1);
		Enemy enemy2 = new Enemy();
		enemy2.init(600, 400, 0);
		EntitySystem.addEntity(enemy2);
	}


	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
		for (Cloud c : clouds) c.sprite.draw(game.batch);
		EntitySystem.draw();
        game.batch.end();

		EntitySystem.update(delta);

		stage.act(delta);
		stage.draw();

		renderer.setProjectionMatrix(camera.combined);

		renderer.begin(ShapeRenderer.ShapeType.Line);
		renderer.setColor(1, 0, 0, 1);
		ArrayList<Entity> entities = EntitySystem.getEntities();
		for (Entity e : entities) {
			for (Polygon p : e.hitbox) {
				renderer.polygon(p.getTransformedVertices());
			}
		}
		renderer.line(-8000, -8000, 8000, -8000);
		renderer.line(8000, -8000, 8000, 8000);
		renderer.line(8000, 8000, -8000, 8000);
		renderer.line(-8000, 8000, -8000, -8000);

		renderer.end();

		camera.update();

		if (EntitySystem.getEnemies().isEmpty() || EntitySystem.getPlayer().health <= 0) {
			if (GameInput.getKeyInput().contains("R")) {
				game.currentScreen = new MenuScreen(game);
				game.setScreen(game.currentScreen);
				this.dispose();
			}
		}
	}


    @Override
   	public void dispose() {}

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