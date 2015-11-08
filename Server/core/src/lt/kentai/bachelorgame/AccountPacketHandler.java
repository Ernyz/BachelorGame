package lt.kentai.bachelorgame;

import com.esotericsoftware.minlog.Log;

import lt.kentai.bachelorgame.Network.LoginRequest;
import lt.kentai.bachelorgame.Network.LoginResult;
import lt.kentai.bachelorgame.Network.MatchInfo;
import lt.kentai.bachelorgame.Network.Matchmaking;
import lt.kentai.bachelorgame.screens.ServerScreen;

public class AccountPacketHandler {

	private ServerScreen serverScreen;
	
	public AccountPacketHandler(ServerScreen serverScreen) {
		this.serverScreen = serverScreen;
	}
	
	public void handleLoginRequest(AccountConnection accountConnection, LoginRequest loginRequest) {
		Log.info(loginRequest.username + " is trying to connect.");
		serverScreen.addMessage(loginRequest.username + " is trying to connect.");
		if(loginRequest.username != "" && loginRequest.username != null) {
			LoginResult loginResult = new LoginResult();
			loginResult.success = true;
			loginResult.message = "Login successful!";
			accountConnection.sendTCP(loginResult);
			serverScreen.addMessage(loginRequest.username + " has successfully connected!");
			accountConnection.connectionName = loginRequest.username;
		} else {
			LoginResult loginResult = new LoginResult();
			loginResult.success = false;
			loginResult.message = "Invalid login data.";
			accountConnection.sendTCP(loginResult);
			accountConnection.close();
			serverScreen.addMessage(loginRequest.username + " did not connect.");
		}
	}
	
	public void handleMatchmaking(AccountConnection accountConnection, Matchmaking m) {
		if(m.entering) {
			serverScreen.addMessage(accountConnection.connectionName + " has entered matchmaking.");
			serverScreen.getMatchmaker().addConnection(accountConnection);
		} else {
			serverScreen.addMessage(accountConnection.connectionName + " has left matchmaking.");
			serverScreen.getMatchmaker().removeConnection(accountConnection);
		}
	}
	
	public void handleMatchInfoRequest(AccountConnection accountConnection, Match match) {
		MatchInfo matchInfo = new MatchInfo();
		matchInfo.champions = match.getChampions();
		
		accountConnection.sendTCP(matchInfo);
	}
	
}
