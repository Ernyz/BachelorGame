package lt.kentai.bachelorgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import lt.kentai.bachelorgame.screens.ServerScreen;

public class BachelorGameServer extends Game {
	SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new ServerScreen(batch));
	}

	@Override
	public void render () {
		super.render();
	}
}
