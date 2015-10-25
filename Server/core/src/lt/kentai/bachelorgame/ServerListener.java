package lt.kentai.bachelorgame;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import lt.kentai.bachelorgame.Network.*;
import lt.kentai.bachelorgame.screens.ServerScreen.GameConnection;

public class ServerListener extends Listener {
	public void received(Connection c, Object o) {
		GameConnection gameConnection = (GameConnection) c;
		//TODO: create PacketHandler class for moving packet handling out of here
		if(o instanceof LoginRequest) {
			LoginRequest loginRequest = (LoginRequest) o;
			Log.info(loginRequest.username + " is trying to connect.");
			if(loginRequest.username != "" || loginRequest.username != null) {
				LoginResult loginResult = new LoginResult();
				loginResult.success = true;
				loginResult.message = "Login successful!";
				gameConnection.sendTCP(loginResult);
			} else {
				LoginResult loginResult = new LoginResult();
				loginResult.success = false;
				loginResult.message = "Invalid login data.";
				gameConnection.sendTCP(loginResult);
				gameConnection.close();//TODO debug this later
			}
		}
	}
	
	@Override
	public void connected(Connection connection) {
		Log.info("[SERVER] Someone has connected. Connection ID: " + connection.getID());
	}
	
	@Override
	public void disconnected(Connection connection) {
		Log.info("[SERVER] Someone has disconnected. Connection ID: " + connection.getID());
	}
}