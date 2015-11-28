package lt.kentai.bachelorgame.screens;

import lt.kentai.bachelorgame.GameClientV2;

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
		if(loginScreen == null) {
			loginScreen = new LoginScreen();
		}
		mainClass.setScreen(loginScreen);
	}
	
	public void switchToMainMenuScreen() {
		if(mainMenuScreen == null) {
			mainMenuScreen = new MainMenuScreen();
		}
		mainClass.setScreen(mainMenuScreen);
	}
	
	public void switchToLobbyScreen(final int matchId, String[] championNames) {
		if(lobbyScreen == null) {
			lobbyScreen = new LobbyScreen(matchId, championNames);
		}
		mainClass.setScreen(lobbyScreen);
	}
	
	public void switchToGameScreen(final int matchId) {
		if(gameScreen == null) {
			gameScreen = new GameScreen(matchId, GameClientV2.getBatch());
		}
		mainClass.setScreen(gameScreen);
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
