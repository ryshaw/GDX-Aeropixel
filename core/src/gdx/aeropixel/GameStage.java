package gdx.aeropixel;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

class GameStage extends Stage {
	private GameScreen screen;
	private Group hud;
	private TextLabel e, p;

	GameStage(GameScreen s) {
		super(new ScreenViewport());
		this.screen = s;

		this.hud = new Group();
		this.addActor(hud);

		e = new TextLabel("Enemy: ", 10, 30,  1);
		p = new TextLabel("Player: ", 10, 50,  1);

		hud.addActor(e);
		hud.addActor(p);
	}

	@Override
	public void act(float delta) {
		Vector2 enemyPos = EntitySystem.getEnemyPos();
		enemyPos = new Vector2(MathUtils.round(enemyPos.x), MathUtils.round(enemyPos.y));
		e.setText("Enemy " + enemyPos);
		Vector2 playerPos = Player.getPos();
		playerPos = new Vector2(MathUtils.round(playerPos.x), MathUtils.round(playerPos.y));
		p.setText("Player: " + playerPos);
		super.act(delta);
	}

	@Override
	public void dispose() { super.dispose(); }

}
