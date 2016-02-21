package lt.kentai.bachelorgame.networking;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;
import com.esotericsoftware.minlog.Log;

public class NetworkingManager {
	
	private Client client;
	
	public NetworkingManager() {
		client = new Client();
	}
	
	public boolean init() {
		Log.set(Log.LEVEL_DEBUG);
		client.start();
		Network.register(client);
		client.addListener(new ThreadedListener(new ClientListener()));
		
		try {
			client.connect(10000, Network.serverIP, Network.tcpPort, Network.udpPort);
		} catch(IOException e) {
//			e.printStackTrace();
			Log.error(e.getMessage());
			return false;
		}
		
		return true;
	}

	public Client getClient() {
		return client;
	}
	
}
