package lt.kentai.bachelorgame.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;

import lt.kentai.bachelorgame.GameClient;

/**
 * Class for managing libgdx screens.
 * 
 * @author ernyz
 */
public class ScreenManager {
	
	public static void switchToLoginScreen(SpriteBatch batch, GameClient mainClass) {
		mainClass.setScreen(new LoginScreen(batch, mainClass));
	}
	
	public static void switchToMainMenuScreen(SpriteBatch batch, GameClient mainClass, Client client) {
		mainClass.setScreen(new MainMenuScreen(batch, mainClass, client));
	}
	
	public static void switchToLobbyScreen(SpriteBatch batch, GameClient mainClass, Client client) {
		mainClass.setScreen(new LobbyScreen(batch, mainClass, client));
	}
	
}
