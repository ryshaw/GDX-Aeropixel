package gdx.aeropixel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Iterator;

public class GameScreen implements Screen {
  	private final Aeropixel game;
	private Texture cloud1, cloud2;
   	private static final Vector2 MAP_SIZE = new Vector2(-8000, 8000);
   	private Array<Vector2> clouds = new Array<>();
    private static ArrayList<Bullet> bullets;

    static OrthographicCamera camera;


	GameScreen(final Aeropixel game) {
		this.game = game;

        cloud1 = new Texture("cloud1.png");
        cloud2 = new Texture("cloud2.png");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Aeropixel.WINDOW_SIZE.x, Aeropixel.WINDOW_SIZE.y);

        for (int i = 0; i < 2000; i++) {
            float randomX = MathUtils.random(MAP_SIZE.x, MAP_SIZE.y);
            float randomY = MathUtils.random(MAP_SIZE.x, MAP_SIZE.y);

            Vector2 v = new Vector2(randomX, randomY);
            clouds.add(v);
        }

        bullets = new ArrayList<>();
        new Player();
	}


	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor(0.999f, 0.999f, 0.999f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        for (int i = 0; i < 2000/2; i++) {
            game.batch.draw(cloud1, clouds.get(i).x, clouds.get(i).y);
            game.batch.draw(cloud2, clouds.get(2000/2+i).x, clouds.get(2000/2+i).y);
        }
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
	     cloud1.dispose();
	     cloud2.dispose();
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