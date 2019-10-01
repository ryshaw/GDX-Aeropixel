package gdx.aeropixel;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

class MenuStage extends Stage {
	private MenuScreen screen;
	private Group menu;

	MenuStage(MenuScreen s) {
		super(new ScreenViewport());
		this.screen = s;

		menu = new Group();
		this.addActor(menu);

		TextLabel hangar = new TextLabel("Hangar", 300, 620, 2);
		menu.addActor(hangar);
		TextLabel wpn = new TextLabel("Weapons", 50, 550, 1);
		menu.addActor(wpn);
		TextLabel spd = new TextLabel("Speed", 250, 550, 1);
		menu.addActor(spd);
		TextLabel amr = new TextLabel("Armor", 450, 550, 1);
		menu.addActor(amr);
		TextLabel agl = new TextLabel("Agility", 650, 550, 1);
		menu.addActor(agl);

		Player p = EntitySystem.getPlayer();

		TextLabel mg = new TextLabel("Machine Gun", 30, 515, 1);
		menu.addActor(mg);
		ImageLabel spdlvl = new ImageLabel("level/level" + p.speedlvl, 285, 515);
		menu.addActor(spdlvl);
		ImageLabel amrlvl = new ImageLabel("level/level" + p.armorlvl, 485, 515);
		menu.addActor(amrlvl);
		ImageLabel agllvl = new ImageLabel("level/level" + p.agilelvl, 690, 515);
		menu.addActor(agllvl);

		MenuButton launch = new MenuButton("Launch", 680, 40, 80, 30, 1);
		menu.addActor(launch);
	}

	@Override
	public void act(float delta) { super.act(delta); }

	void clicked(String command) {
		if ("Launch".equals(command)) {
			screen.startGame();
		}
	}

	@Override
	public void dispose() {
		for (Actor a : menu.getChildren()) {
			if (a.getClass() == ImageLabel.class) {
				ImageLabel b = (ImageLabel) a;
				b.dispose();
			}
		}
		super.dispose();
	}

}
