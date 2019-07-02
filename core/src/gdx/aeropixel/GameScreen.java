package gdx.aeropixel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Iterator;

public class GameScreen implements Screen {
  	private final Aeropixel game;
   	private static final Vector2 MAP_SIZE = new Vector2(-8000, 8000);
   	private ArrayList<Cloud> clouds = new ArrayList<>();
    private static ArrayList<Bullet> bullets;

    static OrthographicCamera camera;


	GameScreen(final Aeropixel game) {
		this.game = game;
		Gdx.input.setInputProcessor(new GameInput());

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Aeropixel.WINDOW_SIZE.x, Aeropixel.WINDOW_SIZE.y);

		for (int i = 0; i < 1000; i++) {
			int index = MathUtils.random(0, 4);
			float x = MathUtils.random(MAP_SIZE.x, MAP_SIZE.y);
			float y = MathUtils.random(MAP_SIZE.x, MAP_SIZE.y);
			int r = MathUtils.random(1, 360);

			clouds.add(new Cloud(game.clouds.get(index), new Vector2(x, y), r));
		}

        bullets = new ArrayList<>();
        new Player(game.manager);
	}


	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

		for (Cloud c : clouds) c.sprite.draw(game.batch);

		Player.sprite.draw(game.batch);
        for (Bullet b : bullets) b.sprite.draw(game.batch);

        game.batch.end();

        for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext();) {
            Bullet b = iterator.next();
            Vector2 p = b.getPosition();
            if (Math.abs(p.x) > MAP_SIZE.y || Math.abs(p.y) > MAP_SIZE.y) {
                iterator.remove();
            } else {
                b.update();
            }
        }


        Player.update();
        camera.update();
	}


	static void addProjectile(Bullet b) {
	     bullets.add(b);
    }


    static Vector2 getVelocity(float direction, float speed, boolean scale) {
        float dirX = (float) Math.sin(Math.toRadians(direction));
        float dirY = (float) Math.cos(Math.toRadians(direction));
        Vector2 delta = new Vector2(-speed * dirX, speed * dirY);
        if (scale) {
            return delta.scl(Gdx.graphics.getDeltaTime());
        } else {
            return delta;
        }
    }


    @Override
   	public void dispose() {
	     Player.dispose();
	     for (Bullet b : bullets) b.dispose();
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