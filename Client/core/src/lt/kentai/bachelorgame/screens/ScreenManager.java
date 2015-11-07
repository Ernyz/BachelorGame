package lt.kentai.bachelorgame.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;

import lt.kentai.bachelorgame.GameClient;

/**
 * Class for managing libgdx screens.
 * 
 * @author Ernyz
 */
public class ScreenManager {
	
	private static Object currentScreen;
	
	public static void switchToLoginScreen(SpriteBatch batch, GameClient mainClass) {
		currentScreen = new LoginScreen(batch, mainClass);
		mainClass.setScreen((LoginScreen) currentScreen);
	}
	
	public static void switchToMainMenuScreen(SpriteBatch batch, GameClient mainClass, Client client) {
		currentScreen = new MainMenuScreen(batch, mainClass, client);
		mainClass.setScreen((MainMenuScreen) currentScreen);
	}
	
	public static void switchToLobbyScreen(SpriteBatch batch, GameClient mainClass, Client client) {
		currentScreen = new LobbyScreen(batch, mainClass, client);	
		mainClass.setScreen((LobbyScreen) currentScreen);
	}

	public static Object getCurrentScreen() {
		return currentScreen;
	}
	
}
