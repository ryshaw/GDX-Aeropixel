package gdx.aeropixel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class MainScreen implements Screen {
  	private final Aeropixel game;
	private Texture cloud1, cloud2;
	private Sprite plane;
   	private ArrayList<Vector2> clouds = new ArrayList<>(); // create a sea of clouds
	private int xOffset = 0;
    private ArrayList<Button> buttons;

    private OrthographicCamera camera;


	 MainScreen(final Aeropixel game) {
		this.game = game;

        cloud1 = new Texture("cloud1.png");
        cloud2 = new Texture("cloud2.png");
        plane = new Sprite(new Texture("plane.png"));
        plane.rotate(270);
        plane.setCenter(-100, -100);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Aeropixel.WINDOW_SIZE.x, Aeropixel.WINDOW_SIZE.y);

        for (int i = 0; i < 100; i++) {
            float randomX = MathUtils.random(0, 8000);
            float randomY = MathUtils.random(-40, 640);

            Vector2 v = new Vector2(randomX, randomY);
            clouds.add(v);
        }

        buttons = new ArrayList<>();
        buttons.add(new Button(100, 280, 120, 60, "Play", game.batch, game.menuFont, Command.PLAY));
        buttons.add(new Button(100, 200, 380, 60, "Instructions", game.batch, game.menuFont, Command.INSTR));
        buttons.add(new Button(100, 120, 120, 60, "Quit", game.batch, game.menuFont, Command.QUIT));
	 }


	@Override
	public void render(float delta) {
        camera.translate(1, 0);
        xOffset += 1;

        Gdx.gl.glClearColor(0.96f, 0.96f, 0.96f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        for (int i = 1; i < clouds.size() / 2; i++) {
            game.batch.draw(cloud1, clouds.get(i).x, clouds.get(i).y);
            game.batch.draw(cloud2, clouds.get(clouds.size() - i).x, clouds.get(clouds.size() - i).y);
        }
        plane.draw(game.batch);

        game.titleFont.draw(game.batch, "Aeropixel", 60 + xOffset, 580);

		float yPos = -100;
        for (Button b : buttons) {
        	b.update();
        	b.offset(xOffset, 0);
        	if (b.isTouched()) {
        		yPos = b.getPosition().y+ b.getSize().x/2;
        		if (b.isClicked()) {
        			switch (b.cmd) {
				        case PLAY:
				        	startGame();
				        	break;
				        case INSTR:
				        	//TODO: instructions
					        break;
				        case QUIT:
				        	quit();
				        	break;
			        }
		        }
	        }
        }

        game.batch.end();

        camera.update();

        plane.setCenter(60 + xOffset, yPos);
    }

    private void startGame() {
        game.currentScreen = new GameScreen(game);
        game.setScreen(game.currentScreen);
        this.dispose();
    }

    private void quit() {
        Gdx.app.exit();
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