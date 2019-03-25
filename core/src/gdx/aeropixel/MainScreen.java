package gdx.aeropixel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class MainScreen implements Screen {
  	private final Aeropixel game;
	private Texture cloud1, cloud2;
	private Sprite plane;
    private static final Vector2 WINDOW_SIZE = new Vector2(800, 640);
   	private ArrayList<Vector2> clouds = new ArrayList<>(); // create a sea of clouds
    private float yPos = -100;
    private int xOffset = 0;
    private ArrayList<Button> buttons;

    private OrthographicCamera camera;


	 MainScreen(final Aeropixel game) {
		this.game = game;
		MenuInput menuInput = new MenuInput();
		Gdx.input.setInputProcessor(menuInput);

        cloud1 = new Texture("cloud1.png");
        cloud2 = new Texture("cloud2.png");
        plane = new Sprite(new Texture("plane.png"));
        plane.rotate(270);
        plane.setCenter(-100, -100);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, WINDOW_SIZE.x, WINDOW_SIZE.y);

        for (int i = 0; i < 100; i++) {
            float randomX = MathUtils.random(0, 8000);
            float randomY = MathUtils.random(-40, 640);

            Vector2 v = new Vector2(randomX, randomY);
            clouds.add(v);
        }

        buttons = new ArrayList<>();
        buttons.add(new Button(60, 280, 380, 60, "Play", game.batch, game.menuFont, Command.PLAY));
        buttons.add(new Button(60, 200, 380, 60, "Instructions", game.batch, game.menuFont, Command.INSTR));
        buttons.add(new Button(60, 120, 380, 60, "Quit", game.batch, game.menuFont, Command.QUIT));
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

        String s1 = "Aeropixel";
        String s2 = "Play";
        String s3 = "Instructions";
        String s4 = "Quit";
        Rectangle r2 = new Rectangle(60 + xOffset, 280, 380, 60);
        Rectangle r3 = new Rectangle(60 + xOffset, 200, 380, 60);
        Rectangle r4 = new Rectangle(60 + xOffset, 120, 380, 60);

        /*if (r2.contains(Gdx.input.getX() + xOffset, WINDOW_SIZE.y - Gdx.input.getY())) {
            yPos = 300;
            if (Gdx.input.isTouched()) {
                startGame();
            }
        } else if (r3.contains(Gdx.input.getX() + xOffset, WINDOW_SIZE.y - Gdx.input.getY())) {
            yPos = 220;
        } else if (r4.contains(Gdx.input.getX() + xOffset, WINDOW_SIZE.y - Gdx.input.getY())) {
            yPos = 140;
            if (Gdx.input.isTouched()) {
                quit();
            }
        } else {
            yPos = -100; // default
        }*/
        game.titleFont.draw(game.batch, s1, 60 + xOffset, 580);

        for (Button b : buttons) {
        	b.update();
        	b.offset(1, 0);
        	if (b.isTouched()) {
        		yPos = b.getPosition().y;
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