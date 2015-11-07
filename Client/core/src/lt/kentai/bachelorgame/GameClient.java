package lt.kentai.bachelorgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import lt.kentai.bachelorgame.screens.ScreenManager;

public class GameClient extends Game {
	private SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		ScreenManager.switchToLoginScreen(batch, this);
	}

	@Override
	public void render () {
		super.render();
	}
}
