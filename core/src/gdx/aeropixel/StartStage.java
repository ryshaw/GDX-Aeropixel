package gdx.aeropixel;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

class StartStage extends Stage {
	private StartScreen screen;
	private Group mainGroup, instrGroup;

	StartStage(StartScreen s) {
		super(new ScreenViewport());
		this.screen = s;


		this.mainGroup = new Group();
		this.instrGroup = new Group();
		this.addActor(mainGroup);

		Button play = new Button("Play", 100, 280, 120, 60, 2);
		Button instr = new Button("How to Play", 100, 200, 300, 60, 2);
		Button quit = new Button("Quit", 100, 120, 120, 60, 2);
		Button back = new Button("Back", 330, 80, 120, 60, 2);
		mainGroup.addActor(play);
		mainGroup.addActor(instr);
		mainGroup.addActor(quit);
		instrGroup.addActor(back);

		TextLabel title = new TextLabel("Aeropixel", 40, 580,  3);
		TextLabel credits = new TextLabel("a game by Ryan Shaw", 520, 60, 1);
		mainGroup.addActor(title);
		mainGroup.addActor(credits);

		String s1 = "The goal in Aeropixel is to be the last plane flying.\n" +
				"Blow apart the enemy fighters,\nand stay in the air as long as possible.\n" +
				"How far you get determines your score and points attained.\n" +
				"After each sortie, you will have a chance to buy upgrades.";
		String s2 = "Controls:\nTurn: A, D    Boost: W    Fire: Space    Switch Weapons: S";

		TextLabel instructions = new TextLabel("How to Play", 80, 580, 2);
		TextLabel i1 = new TextLabel(s1, 60, 480, 1);
		TextLabel i2 = new TextLabel(s2, 60, 280, 1);
		instrGroup.addActor(instructions);
		instrGroup.addActor(i1);
		instrGroup.addActor(i2);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

	void clicked(String command) {
		switch (command) {
			case "Play":
				screen.startGame();
				break;
			case "How to Play":
				instruct();
				break;
			case "Quit":
				screen.quit();
				break;
			case "Back":
				back();
				break;
		}
	}

	private void instruct() {
		this.clear();
		this.addActor(instrGroup);
	}

	private void back() {
		this.clear();
		this.addActor(mainGroup);
	}

	@Override
	public void dispose() {
		for (Actor a : mainGroup.getChildren()) {
			if (a.getClass() == Button.class) {
				Button b = (Button) a;
				b.dispose();
			}
		}
		for (Actor a : instrGroup.getChildren()) {
			if (a.getClass() == Button.class) {
				Button b = (Button) a;
				b.dispose();
			}
		}

		super.dispose();
	}

}
