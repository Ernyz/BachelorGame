package lt.kentai.bachelorgame.networking;

import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import lt.kentai.bachelorgame.AccountConnection;

public class NetworkingManager {

	private ServerWrapper serverWrapper;
	
	public NetworkingManager() {
		
	}
	
	public boolean init() {
		Log.set(Log.LEVEL_DEBUG);
		serverWrapper = new ServerWrapper() {
			protected Connection newConnection() {
				AccountConnection ac = new AccountConnection(); 
				ac.connectionState = AccountConnection.ConnectionState.IN_LOGIN;
				return ac;
			}
		};
		Network.register(serverWrapper);
		
		serverWrapper.addListener(new ServerListener());
		try {
			serverWrapper.bind(Network.tcpPort, Network.udpPort);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		serverWrapper.start();
		
		return true;
	}
	
	public ServerWrapper getServerWrapper() {
		return serverWrapper;
	}
	
}
