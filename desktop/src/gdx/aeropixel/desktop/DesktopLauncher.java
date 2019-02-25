package gdx.aeropixel.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import gdx.aeropixel.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Aeropixel";
  		config.width = 800;
  		config.height = 640;
  		config.vSyncEnabled = true;
  		new LwjglApplication(new Game(), config);
	}
}
