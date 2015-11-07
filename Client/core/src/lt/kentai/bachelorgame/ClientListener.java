package lt.kentai.bachelorgame;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import lt.kentai.bachelorgame.Network.*;
import lt.kentai.bachelorgame.screens.LoginScreen;
import lt.kentai.bachelorgame.screens.MainMenuScreen;
import lt.kentai.bachelorgame.screens.ScreenManager;

public class ClientListener extends Listener {
	
	public ClientListener() {
	}
	
	public void received(Connection c, Object o) {
		if(o instanceof LoginResult) {
			LoginResult loginResult = (LoginResult)o;
			if(loginResult.success) {
				//currentScreen.switchToMainMenuScreen();
				if(ScreenManager.getCurrentScreen() instanceof LoginScreen) {
					((LoginScreen) ScreenManager.getCurrentScreen()).switchToMainMenuScreen(); 
				} else {
					//TODO: Some errror or something. Warn about it
				}
			} else {
				//TODO: write failure msg to current screen
				Log.info("[CLIENT]" + loginResult.message);
			}
		} else if(o instanceof Matchmaking) {
			Matchmaking m = (Matchmaking) o;
			if(m.entering) {
				Log.info("Matchmaking entered.");
			} else {
				Log.info("Matchmaking left.");
			}
		} else if(o instanceof AcceptedToLobby) {
			if(ScreenManager.getCurrentScreen() instanceof MainMenuScreen) {
				((MainMenuScreen) ScreenManager.getCurrentScreen()).switchToLobbyScreen(); 
			} else {
				//TODO: Some errror or something. Warn about it
			}
		}
	}
}
