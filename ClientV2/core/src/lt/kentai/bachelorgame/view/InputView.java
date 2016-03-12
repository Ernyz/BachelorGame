package lt.kentai.bachelorgame.view;

import com.badlogic.gdx.InputProcessor;

import lt.kentai.bachelorgame.Match;
import lt.kentai.bachelorgame.networking.ClientWrapper;

public class InputView implements InputProcessor {
	
	private ClientWrapper client;
	private Match match;
	
	public InputView(ClientWrapper client, Match match) {
		this.client = client;
		this.match = match;
	}

	@Override
	public boolean keyDown(int keycode) {
//		if(Gdx.input.isKeyPressed(Keys.W)) {
//			match.getPlayer().moveBy(delta, 0f, 1f);
//			client.sendUDP(new MoveChampion(match.matchId, 0f, 1f));
//		}
//		if(Gdx.input.isKeyPressed(Keys.A)) {
//			match.getPlayer().moveBy(delta, -1f, 0f);
//			client.sendUDP(new MoveChampion(match.matchId, -1f, 0f));
//		}
//		if(Gdx.input.isKeyPressed(Keys.S)) {
//			match.getPlayer().moveBy(delta, 0f, -1f);
//			client.sendUDP(new MoveChampion(match.matchId, 0f, -1f));
//		}
//		if(Gdx.input.isKeyPressed(Keys.D)) {
//			match.getPlayer().moveBy(delta, 1f, 0f);
//			client.sendUDP(new MoveChampion(match.matchId, 1f, 0f));
//		}
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
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
	
}
