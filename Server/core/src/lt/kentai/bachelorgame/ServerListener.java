package lt.kentai.bachelorgame;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import lt.kentai.bachelorgame.Network.LoginRequest;
import lt.kentai.bachelorgame.screens.ServerScreen;
import lt.kentai.bachelorgame.screens.ServerScreen.GameConnection;

public class ServerListener extends Listener {
	
	private PacketHandler packetHandler;
	
	public ServerListener(ServerScreen serverScreen) {
		packetHandler = new PacketHandler(serverScreen);
	}
	
	public void received(Connection c, Object o) {
		GameConnection gameConnection = (GameConnection) c;
		if(o instanceof LoginRequest) {
			LoginRequest loginRequest = (LoginRequest) o;
			packetHandler.handleLoginRequest(gameConnection, loginRequest);
		}
	}
	
	@Override
	public void disconnected(Connection connection) {
		Log.info("[SERVER] Someone has disconnected. Connection ID: " + connection.getID());
	}
}