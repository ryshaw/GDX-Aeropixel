package gdx.aeropixel;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Aeropixel extends Game {

	SpriteBatch batch;
    BitmapFont titleFont, debugFont, menuFont;
    Screen currentScreen;

	@Override
	public void create() {
		batch = new SpriteBatch();
        FreeTypeFontGenerator generator;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("PixelOperator-Bold.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 24;
        parameter.color = Color.BLACK;
        debugFont = generator.generateFont(parameter);

        parameter.size = 80;
        parameter.color = Color.BLACK;
        titleFont = generator.generateFont(parameter);

        parameter.size = 60;
        parameter.color = Color.BLACK;
		menuFont = generator.generateFont(parameter);

        generator.dispose();
        this.currentScreen = new MainScreen(this);
        this.setScreen(currentScreen);
	}

	@Override
	public void render() {
    	super.render();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		titleFont.dispose();
		debugFont.dispose();
		currentScreen.dispose();
	}
}
