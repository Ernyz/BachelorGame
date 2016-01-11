package lt.kentai.bachelorgame.networking;

import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import lt.kentai.bachelorgame.AccountConnection;

public class NetworkingManager {

	private Server server;
	
	public NetworkingManager() {
		
	}
	
	public boolean init() {
		Log.set(Log.LEVEL_DEBUG);
		server = new Server() {
			protected Connection newConnection() {
				AccountConnection ac = new AccountConnection(); 
				ac.connectionState = AccountConnection.ConnectionState.IN_LOGIN;
				return ac;
			}
		};
		Network.register(server);
		
		server.addListener(new ServerListener());
		try {
			server.bind(Network.tcpPort, Network.udpPort);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		server.start();
		
		return true;
	}
	
	public Server getServer() {
		return server;
	}
	
}
