package lt.kentai.bachelorgame;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import lt.kentai.bachelorgame.Network.LoginResult;
import lt.kentai.bachelorgame.screens.LoginScreen;

public class ClientListener extends Listener {
	
	private LoginScreen loginScreen;
	
	public ClientListener(LoginScreen loginScreen) {
		this.loginScreen = loginScreen;
	}
	
	public void received(Connection c, Object o) {
		if(o instanceof LoginResult) {
			LoginResult loginResult = (LoginResult)o;
			if(loginResult.success) {
				//TODO: change to MainMenuScreen
				loginScreen.switchToMainMenuScreen();
			} else {
				//TODO: write failure msg to current screen
				Log.info("[CLIENT]" + loginResult.message);
			}
		}
	}
}
