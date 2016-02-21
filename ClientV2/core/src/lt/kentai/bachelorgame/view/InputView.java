package lt.kentai.bachelorgame.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.esotericsoftware.kryonet.Client;

import lt.kentai.bachelorgame.Match;
import lt.kentai.bachelorgame.networking.Network.MoveChampion;

public class InputView implements InputProcessor {
	
	private Client client;
	private Match match;
	
	public InputView(Client client, Match match) {
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
