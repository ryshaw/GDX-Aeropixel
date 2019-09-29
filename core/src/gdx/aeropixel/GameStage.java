package gdx.aeropixel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

class GameStage extends Stage {
	private GameScreen screen;
	private Group hud;
	private TextLabel e, p, fps, num;

	GameStage(GameScreen s) {
		super(new ScreenViewport());
		this.screen = s;

		this.hud = new Group();
		this.addActor(hud);

		e = new TextLabel("Enemy: ", 2, 570,  1);
		p = new TextLabel("Player: ", 2, 600,  1);
		fps = new TextLabel("fps", 2, 630, 1);
		num = new TextLabel("Enemies: ", 120, 630, 1);

		hud.addActor(e);
		hud.addActor(p);
		hud.addActor(fps);
		hud.addActor(num);
	}

	@Override
	public void act(float delta) {
		ArrayList<Enemy> enemies = EntitySystem.getEnemies();
		if (!enemies.isEmpty()) {
			Enemy enemy = EntitySystem.getEnemies().get(0);
			Vector2 enemyPos = new Vector2(MathUtils.round(enemy.position.x), MathUtils.round(enemy.position.y));
			e.setText("Enemy: " + enemyPos + " " + enemy.state);
		}
		Player player = EntitySystem.getPlayer();
		Vector2 pos = player.position;
		pos = new Vector2(MathUtils.round(pos.x), MathUtils.round(pos.y));
		p.setText("Player: " + pos);
		fps.setText(Gdx.graphics.getFramesPerSecond() + " fps");
		num.setText("Enemies: " + enemies.size());

		if (enemies.isEmpty()) {
			hud.addActor(new TextLabel("You Win!", 180, 400, 3));
			hud.addActor(new TextLabel("Press R to continue", 280, 300, 1));
		}
		if (player.health <= 0) {
			hud.addActor(new TextLabel("You Lose!", 160, 400, 3));
			hud.addActor(new TextLabel("Press R to continue", 280, 300, 1));
		}

		super.act(delta);
	}

	@Override
	public void dispose() { super.dispose(); }

}
