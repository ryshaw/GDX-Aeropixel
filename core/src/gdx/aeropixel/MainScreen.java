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
	private Sprite plane;
	private ArrayList<Cloud> clouds = new ArrayList<>();
	private int xOffset = 0;
    private ArrayList<Button> buttons;

    private OrthographicCamera camera;


	 MainScreen(final Aeropixel game) {
		this.game = game;

		 ArrayList<Texture> cloudTextures = new ArrayList<>();
		 for (int i = 1; i < 6; i++) {
			String file = "cloud" + i + ".png";
			cloudTextures.add(new Texture(file));
		}

		plane = new Sprite(new Texture("plane.png"));
        plane.rotate(270);
        plane.setCenter(-100, -100);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Aeropixel.WINDOW_SIZE.x, Aeropixel.WINDOW_SIZE.y);

        for (int i = 0; i < 80; i++) {
        	int index = MathUtils.random(0, 4);
            float x = MathUtils.random(0, 10000);
            float y = MathUtils.random(20, 620);
            int r = MathUtils.random(1, 360);

            clouds.add(new Cloud(cloudTextures.get(index), new Vector2(x, y), r));
        }

        buttons = new ArrayList<>();
        buttons.add(new Button(100, 280, 120, 60, "Play", game.batch, game.menuFont, Command.PLAY));
        buttons.add(new Button(100, 200, 380, 60, "Instructions", game.batch, game.menuFont, Command.INSTR));
        buttons.add(new Button(100, 120, 120, 60, "Quit", game.batch, game.menuFont, Command.QUIT));
	 }


	@Override
	public void render(float delta) {
        camera.translate(2, 0);
        xOffset += 2;

        if (xOffset == 10000) {
        	xOffset = -800;
        	camera.translate(-10800, 0);
        }

        Gdx.gl.glClearColor(0.96f, 0.96f, 0.96f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        for (Cloud c : clouds) {
        	c.sprite.draw(game.batch);
        }
		plane.draw(game.batch);

        game.titleFont.draw(game.batch, "Aeropixel", 60 + xOffset, 580);

		float yPos = -100;
        for (Button b : buttons) {
        	b.update();
        	b.offset(xOffset, 0);
        	if (b.isTouched()) {
        		yPos = b.getPosition().y + b.getSize().x/2;
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
        for (Cloud c : clouds) {
        	c.sprite.getTexture().dispose();
        }
        plane.getTexture().dispose();
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