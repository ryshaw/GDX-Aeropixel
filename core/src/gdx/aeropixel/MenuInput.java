package gdx.aeropixel;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

public class MenuInput extends InputAdapter {
	private static Vector2 mousePosition = new Vector2();
	private static boolean mouseClicked = false;

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		mouseClicked = true;
		return true;
	}

	@Override
	public boolean mouseMoved(int x, int y) {
		mousePosition.set(x, y);
		return true;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		mouseClicked = false;
		return true;
	}

	static Vector2 getMousePos() {
		return mousePosition;
	}

	static boolean getMouseClicked() {
		return mouseClicked;
	}
}
