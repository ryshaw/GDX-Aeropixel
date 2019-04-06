package gdx.aeropixel;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Aeropixel extends Game {

	AssetManager manager;
	SpriteBatch batch;
    BitmapFont smallFont, mediumFont, largeFont;
    ArrayList<Texture> clouds = new ArrayList<>();
    Screen currentScreen;
    static final Vector2 WINDOW_SIZE = new Vector2(800, 640);

	@Override
	public void create() {
		manager = new AssetManager();
		FileHandle[] files = Gdx.files.internal("images").list();
		for (FileHandle f : files) {
			manager.load("images/" + f.name(), Texture.class);
		}
		manager.finishLoading();

		batch = new SpriteBatch();
        FreeTypeFontGenerator generatorBold = new FreeTypeFontGenerator(Gdx.files.internal("PixelOperator-Bold.ttf"));
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("PixelOperator.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();

        parameter.size = 32;
        parameter.color = Color.BLACK;
        smallFont = generator.generateFont(parameter);

        parameter.size = 64;
        parameter.color = Color.BLACK;
		mediumFont = generatorBold.generateFont(parameter);

		parameter.size = 128;
		parameter.color = Color.BLACK;
		largeFont = generatorBold.generateFont(parameter);

		generatorBold.dispose();

        GameInput input = new GameInput();
        Gdx.input.setInputProcessor(input);

		for (int i = 1; i < 6; i++) {
			String file = "images/cloud" + i + ".png";
			clouds.add(manager.get(file, Texture.class));
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
		manager.dispose();
		batch.dispose();
		smallFont.dispose();
		mediumFont.dispose();
		largeFont.dispose();
		currentScreen.dispose();
	}
}
