package gdx.aeropixel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen {
  	private final Aeropixel game;
	private Texture cloud1, cloud2;
    private static final Vector2 WINDOW_SIZE = new Vector2(800, 640);
   	private static final Vector2 MAP_SIZE = new Vector2(-8000, 8000);
   	private Array<Vector2> clouds = new Array<Vector2>(); // create a sea of clouds
    private static Array<Bullet> bullets;

    static OrthographicCamera camera;


	 GameScreen(final Aeropixel game) {
		this.game = game;

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

        bullets = new Array<Bullet>();
        new Player();
	}


	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor(0.95f, 0.95f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        for (int i = 0; i < 1000/2; i++) {
            game.batch.draw(cloud1, clouds.get(i).x, clouds.get(i).y);
            game.batch.draw(cloud2, clouds.get(1000/2+i).x, clouds.get(1000/2+i).y);
        }
        Player.sprite.draw(game.batch);

        for (Bullet b : bullets) {
            b.sprite.draw(game.batch);
        }

        //String s1 = "Speed: " + Math.round(Player.getMoveSpeed()*10)/10f;
        //game.debugFont.draw(game.batch, s1, Player.sprite.getX() + 80, Player.sprite.getY() + 80);
        game.batch.end();

        for (Bullet b : bullets) {
            b.update();
        }
        Player.update();
        camera.update();
	}

	static void addProjectile(Bullet b) {
	     bullets.add(b);
    }


	@Override
	public void dispose() {
        cloud1.dispose();
        cloud2.dispose();
        Player.dispose();
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