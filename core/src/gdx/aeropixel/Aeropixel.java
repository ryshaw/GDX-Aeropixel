package gdx.aeropixel;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Aeropixel extends Game {

	SpriteBatch batch;
    BitmapFont smallFont, mediumFont, largeFont;
    ArrayList<Texture> clouds = new ArrayList<>();
    Screen currentScreen;
    static final Vector2 WINDOW_SIZE = new Vector2(800, 640);

	@Override
	public void create() {
		batch = new SpriteBatch();
        FreeTypeFontGenerator generatorBold = new FreeTypeFontGenerator(Gdx.files.internal("PixelOperator-Bold.ttf"));
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("PixelOPerator.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();

        parameter.size = 30;
        parameter.color = Color.BLACK;
        smallFont = generator.generateFont(parameter);

        parameter.size = 60;
        parameter.color = Color.BLACK;
		mediumFont = generatorBold.generateFont(parameter);

		parameter.size = 90;
		parameter.color = Color.BLACK;
		largeFont = generatorBold.generateFont(parameter);

		generatorBold.dispose();

        GameInput input = new GameInput();
        Gdx.input.setInputProcessor(input);

		for (int i = 1; i < 6; i++) {
			String file = "cloud" + i + ".png";
			clouds.add(new Texture(file));
		}

        this.currentScreen = new MenuScreen(this);
        this.setScreen(currentScreen);
	}

	@Override
	public void render() {
    	super.render();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		smallFont.dispose();
		mediumFont.dispose();
		largeFont.dispose();
		for (Texture t : clouds) {
			t.dispose();
		}
		currentScreen.dispose();
	}
}
