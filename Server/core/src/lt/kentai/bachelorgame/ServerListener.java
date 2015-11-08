package lt.kentai.bachelorgame;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import lt.kentai.bachelorgame.Network.LoginRequest;
import lt.kentai.bachelorgame.Network.Matchmaking;
import lt.kentai.bachelorgame.Network.RequestForMatchInfo;
import lt.kentai.bachelorgame.screens.ServerScreen;

public class ServerListener extends Listener {
	
	private AccountPacketHandler accountPacketHandler;
	
	public ServerListener(ServerScreen serverScreen) {
		accountPacketHandler = new AccountPacketHandler(serverScreen);
	}
	
	public void received(Connection c, Object o) {
		//AccountConnection gameConnection = (AccountConnection) c;
		AccountConnection accountConnection = null;
//		GameConnection gameConnection = null;
		if(c instanceof AccountConnection) {
			accountConnection = (AccountConnection) c;
			if(o instanceof LoginRequest) {
				LoginRequest loginRequest = (LoginRequest) o;
				accountPacketHandler.handleLoginRequest(accountConnection, loginRequest);
			} else if(o instanceof Matchmaking) {
				Matchmaking m = (Matchmaking) o;
				accountPacketHandler.handleMatchmaking(accountConnection, m);
			} else if(o instanceof RequestForMatchInfo) {
				accountPacketHandler.handleMatchInfoRequest(accountConnection);
			}
		}/* else if(c instanceof GameConnection) {
			gameConnection = (GameConnection) c;
		}*/
	}
	
	@Override
	public void disconnected(Connection connection) {
		Log.info("[SERVER] Someone has disconnected. Connection ID: " + connection.getID());
	}
}