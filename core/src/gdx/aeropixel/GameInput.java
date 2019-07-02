package gdx.aeropixel;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class GameInput extends InputAdapter {
	private static ArrayList<String> keysDown = new ArrayList<>();
	private ArrayList<String> possibleInputs = new ArrayList<>(Arrays.asList("W", "A", "D", "Space"));

	@Override
	public boolean keyDown(int keycode) {
		String key = Input.Keys.toString(keycode);
		if (possibleInputs.contains(key)) {
			keysDown.add(key);
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		String key = Input.Keys.toString(keycode);
		if (possibleInputs.contains(key)) {
			keysDown.remove(key);
		}
		return true;
	}

	static ArrayList getKeyInput() { return keysDown; }
}
