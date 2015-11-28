package lt.kentai.bachelorgame;

import com.badlogic.gdx.Game;

import lt.kentai.bachelorgame.networking.NetworkingManager;
import lt.kentai.bachelorgame.screens.ServerScreen;

public class GameServerV2 extends Game {
	
	private static NetworkingManager networkingManager;
	private static ServerScreen serverScreen;
	
	@Override
	public void create () {
		networkingManager = new NetworkingManager();
		serverScreen = new ServerScreen();
		
		if(networkingManager.init()) {
			this.setScreen(serverScreen);
		}
	}

	@Override
	public void render () {
		super.render();
	}

	public static NetworkingManager getNetworkingManager() {
		return networkingManager;
	}

	public static ServerScreen getServerScreen() {
		return serverScreen;
	}
	
}
