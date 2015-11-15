package lt.kentai.bachelorgame;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import lt.kentai.bachelorgame.Network.LockIn;
import lt.kentai.bachelorgame.Network.LoginRequest;
import lt.kentai.bachelorgame.Network.Matchmaking;
import lt.kentai.bachelorgame.Network.RequestForMatchInfo;
import lt.kentai.bachelorgame.screens.ServerScreen;

public class ServerListener extends Listener {
	
	private AccountPacketHandler accountPacketHandler;
	private ServerScreen serverScreen;
	
	public ServerListener(ServerScreen serverScreen) {
		this.serverScreen = serverScreen;
		accountPacketHandler = new AccountPacketHandler(serverScreen);
	}
	
	public void received(Connection c, Object o) {
		AccountConnection accountConnection = (AccountConnection) c;
		
		if(o instanceof LoginRequest) {
			LoginRequest loginRequest = (LoginRequest) o;
			accountPacketHandler.handleLoginRequest(accountConnection, loginRequest);
		} else if(o instanceof Matchmaking) {
			Matchmaking m = (Matchmaking) o;
			accountPacketHandler.handleMatchmaking(accountConnection, m);
		} else if(o instanceof RequestForMatchInfo) {
			RequestForMatchInfo matchInfoRequest = (RequestForMatchInfo) o;
			Match match = serverScreen.getMatchmaker().getMatchById(matchInfoRequest.matchId);
			accountPacketHandler.handleMatchInfoRequest(accountConnection, match);
		} else if(o instanceof LockIn) {
			LockIn lockInPacket = (LockIn) o;
			Match match = serverScreen.getMatchmaker().getMatchById(lockInPacket.matchId);
			match.lockInChampion(accountConnection, lockInPacket.championName);
		}
	}
	
	@Override
	public void disconnected(Connection connection) {
		Log.info("[SERVER] Someone has disconnected. Connection ID: " + connection.getID());
	}
}