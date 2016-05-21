package lt.kentai.bachelorgame.view;

import com.badlogic.gdx.InputProcessor;
import com.esotericsoftware.kryonet.Client;

import lt.kentai.bachelorgame.Match;

public class InputView implements InputProcessor {
	
	private Client client;
	private Match match;
	
	private boolean[] input = new boolean[256];
	
	public InputView(Client client, Match match) {
		this.client = client;
		this.match = match;
	}

	@Override
	public boolean keyDown(int keycode) {
		input[keycode] = true;
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		input[keycode] = false;
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
	
	public boolean[] getInput() {
		return input;
	}
	
}
