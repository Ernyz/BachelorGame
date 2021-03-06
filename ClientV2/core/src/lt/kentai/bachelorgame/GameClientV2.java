package lt.kentai.bachelorgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.minlog.Log;

import lt.kentai.bachelorgame.screens.LoginScreen;
import lt.kentai.bachelorgame.screens.ScreenManager;

public class GameClientV2 extends Game {
	private static SpriteBatch batch;
	private static ScreenManager screenManager;
	private static NetworkingManager networkingManager;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		screenManager = new ScreenManager(this);
		networkingManager = new NetworkingManager();
		
		if(networkingManager.init()) {
			Log.info("Connected to server.");
			screenManager.switchToLoginScreen();
			LoginScreen loginScreen = (LoginScreen) GameClientV2.getScreenManager().getCurrentScreen();
			loginScreen.setServerStatus("Server online");
		} else {
			Log.info("Could not connect to server.");
			screenManager.switchToLoginScreen();
			LoginScreen loginScreen = (LoginScreen) GameClientV2.getScreenManager().getCurrentScreen();
			loginScreen.setServerStatus("Server offline");
		}
	}

	@Override
	public void render () {
		super.render();
	}

	public static SpriteBatch getBatch() {
		return batch;
	}

	public static ScreenManager getScreenManager() {
		return screenManager;
	}
	
	public static NetworkingManager getNetworkingManager() {
		return networkingManager;
	}

}
