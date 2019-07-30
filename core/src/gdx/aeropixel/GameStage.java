package gdx.aeropixel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

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
		num = new TextLabel("Entities:", 120, 630, 1);

		hud.addActor(e);
		hud.addActor(p);
		hud.addActor(fps);
		hud.addActor(num);
	}

	@Override
	public void act(float delta) {
		Enemy enemy = EntitySystem.getEnemy();
		if (enemy != null) {
			Vector2 enemyPos = new Vector2(MathUtils.round(enemy.position.x), MathUtils.round(enemy.position.y));
			e.setText("Enemy: " + enemyPos + " " + enemy.state);
		}
		Vector2 playerPos = Player.position;
		playerPos = new Vector2(MathUtils.round(playerPos.x), MathUtils.round(playerPos.y));
		p.setText("Player: " + playerPos);
		fps.setText(Gdx.graphics.getFramesPerSecond() + " fps");
		num.setText("Entities: " + EntitySystem.getEntities().size());
		super.act(delta);
	}

	@Override
	public void dispose() { super.dispose(); }

}
