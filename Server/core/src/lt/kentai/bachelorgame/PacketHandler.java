package lt.kentai.bachelorgame;

import com.esotericsoftware.minlog.Log;

import lt.kentai.bachelorgame.Network.LoginRequest;
import lt.kentai.bachelorgame.Network.LoginResult;
import lt.kentai.bachelorgame.screens.ServerScreen;
import lt.kentai.bachelorgame.screens.ServerScreen.GameConnection;

public class PacketHandler {

	private ServerScreen serverScreen;
	
	public PacketHandler(ServerScreen serverScreen) {
		this.serverScreen = serverScreen;
	}
	
	public void handleLoginRequest(GameConnection gameConnection, LoginRequest loginRequest) {
		Log.info(loginRequest.username + " is trying to connect.");
		serverScreen.addMessage(loginRequest.username + " is trying to connect.");
		if(loginRequest.username != "" && loginRequest.username != null) {
			LoginResult loginResult = new LoginResult();
			loginResult.success = true;
			loginResult.message = "Login successful!";
			gameConnection.sendTCP(loginResult);
			serverScreen.addMessage(loginRequest.username + " has successfully connected!");
			gameConnection.name = loginRequest.username;
		} else {
			LoginResult loginResult = new LoginResult();
			loginResult.success = false;
			loginResult.message = "Invalid login data.";
			gameConnection.sendTCP(loginResult);
			gameConnection.close();
			serverScreen.addMessage(loginRequest.username + " did not connect.");
		}
	}
	
}
