package lt.kentai.bachelorgame.screens;

import com.badlogic.gdx.Screen;

import lt.kentai.bachelorgame.GameClientV2;
import lt.kentai.bachelorgame.Network.AcceptedToLobby;

public class ScreenManager {

	private GameClientV2 mainClass;
	
	private LoginScreen loginScreen;
	private MainMenuScreen mainMenuScreen;
	private LobbyScreen lobbyScreen;
	private GameScreen gameScreen;
	
	public ScreenManager(GameClientV2 mainClass) {
		this.mainClass = mainClass;
	}
	
	public void switchToLoginScreen() {
//		if(loginScreen != null) {
//			loginScreen.dispose();
//		}
		loginScreen = new LoginScreen();
		if(mainClass.getScreen() != null) {
			mainClass.getScreen().dispose();
		}
		mainClass.setScreen(loginScreen);
	}
	
	public void switchToMainMenuScreen(boolean placePlayerInMatchmaking) {
//		if(mainMenuScreen != null) {
//			mainMenuScreen.dispose();
//		}
		mainMenuScreen = new MainMenuScreen();
		if(mainClass.getScreen() != null) {
			mainClass.getScreen().dispose();
		}
		mainClass.setScreen(mainMenuScreen);
		
		if(placePlayerInMatchmaking) {
			mainMenuScreen.reenterMatchmaking();
		}
	}
	
	public void switchToLobbyScreen(AcceptedToLobby lobbyInfo) {
//		if(lobbyScreen != null) {
//			lobbyScreen.dispose();
//		}
		lobbyScreen = new LobbyScreen(lobbyInfo);
		if(mainClass.getScreen() != null) {
			mainClass.getScreen().dispose();
		}
		mainClass.setScreen(lobbyScreen);
	}
	
	public void switchToGameScreen(final int matchId) {
//		if(gameScreen != null) {
//			gameScreen.dispose();
//		}
		gameScreen = new GameScreen(matchId, GameClientV2.getBatch());
		if(mainClass.getScreen() != null) {
			mainClass.getScreen().dispose();
		}
		mainClass.setScreen(gameScreen);
	}
	
	public Screen getCurrentScreen() {
		return mainClass.getScreen();
	}

	public LoginScreen getLoginScreen() {
		return loginScreen;
	}

	public MainMenuScreen getMainMenuScreen() {
		return mainMenuScreen;
	}

	public LobbyScreen getLobbyScreen() {
		return lobbyScreen;
	}

	public GameScreen getGameScreen() {
		return gameScreen;
	}
	
}
