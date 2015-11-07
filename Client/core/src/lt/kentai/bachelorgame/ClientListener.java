package lt.kentai.bachelorgame;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import lt.kentai.bachelorgame.Network.*;
import lt.kentai.bachelorgame.screens.LoginScreen;

public class ClientListener extends Listener {
	
	private LoginScreen currentScreen;
	
	public ClientListener(LoginScreen currentScreen) {
		this.currentScreen = currentScreen;
	}
	
	public void received(Connection c, Object o) {
		if(o instanceof LoginResult) {
			LoginResult loginResult = (LoginResult)o;
			if(loginResult.success) {
				currentScreen.switchToMainMenuScreen();
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
			
		}
	}
}
