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
import java.util.HashMap;

public class MenuScreen implements Screen {
  	private final Aeropixel game;
	private OrthographicCamera camera;

	private Sprite plane;
	private ArrayList<Cloud> clouds = new ArrayList<>();
	private int xOffset = 0;
    private HashMap<String, Button> buttons = new HashMap<>();
    private String display = "Menu";


	 MenuScreen(final Aeropixel game) {
		this.game = game;

		plane = new Sprite(game.manager.get("images/plane.png", Texture.class));
        plane.rotate(270);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Aeropixel.WINDOW_SIZE.x, Aeropixel.WINDOW_SIZE.y);

        for (int i = 0; i < 80; i++) {
        	int index = MathUtils.random(0, 4);
        	float x = MathUtils.random(0, 10000);
            float y = MathUtils.random(20, 620);
            int r = MathUtils.random(1, 360);

            clouds.add(new Cloud(game.clouds.get(index), new Vector2(x, y), r));
        }

		buttons.put("Play", new Button(100, 280, 120, 60, game.batch, game.mediumFont));
        buttons.put("Instructions", new Button(100, 200, 380, 60, game.batch, game.mediumFont));
        buttons.put("Quit", new Button(100, 120, 120, 60, game.batch, game.mediumFont));
        buttons.put("Back", new Button(330, 80, 120, 60, game.batch, game.mediumFont));
        buttons.get("Back").active = false;
	 }


	@Override
	public void render(float delta) {
	 	plane.setCenter(-100, -100);
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

		for (String s : buttons.keySet()) {
			Button b = buttons.get(s);
			b.update(s);
			b.offset(xOffset, 0);
			if (b.isTouched()) {
				plane.setCenterX(b.getX() - 40 + xOffset);
				plane.setCenterY(b.getY() + b.getHeight()/2);
				if (b.isClicked()) {
					switch (s) {
						case "Play":
							startGame();
							break;
						case "Instructions":
							instruct();
							break;
						case "Quit":
							quit();
							break;
						case "Back":
							back();
							break;
					}
				}
			}
        }

		plane.draw(game.batch);

		switch (display) {
			case "Menu":
				game.largeFont.draw(game.batch, "Aeropixel", 40 + xOffset, 580);
				break;
			case "Instructions":
				renderInstructions();
				break;
		}
		game.smallFont.draw(game.batch, "a game by Ryan Shaw", 520 + xOffset, 60);

		game.batch.end();

        camera.update();
    }

    private void startGame() {
        game.currentScreen = new GameScreen(game);
        game.setScreen(game.currentScreen);
        this.dispose();
    }

    private void instruct() {
	 	buttons.get("Play").active = false;
	 	buttons.get("Instructions").active = false;
	 	buttons.get("Quit").active = false;
	 	buttons.get("Back").active = true;
	 	display = "Instructions";
    }

    private void back() {
	    buttons.get("Play").active = true;
	    buttons.get("Instructions").active = true;
	    buttons.get("Quit").active = true;
	    buttons.get("Back").active = false;
	    display = "Menu";
    }

    private void quit() {
        Gdx.app.exit();
    }


	@Override
	public void dispose() {
	}

	private void renderInstructions() {
		String s1 = "The goal in Aeropixel is to be the last plane flying.\n" +
				"Blow apart the enemy fighters,\nand stay in the air as long as possible.\n" +
				"How far you get determines your score and points attained.\n" +
				"After each sortie, you will have a chance to buy upgrades.";
		String s2 = "Controls:\nTurn: A, D    Boost: W    Fire: Space    Switch Weapons: S";
		game.mediumFont.draw(game.batch, "Instructions", 80 + xOffset, 580);
		game.smallFont.draw(game.batch, s1, 60 + xOffset, 480);
		game.smallFont.draw(game.batch, s2, 60 + xOffset, 280);
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