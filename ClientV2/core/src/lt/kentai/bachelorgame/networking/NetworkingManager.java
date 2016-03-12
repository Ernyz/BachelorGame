package lt.kentai.bachelorgame.networking;

import java.io.IOException;

import com.esotericsoftware.kryonet.Listener.ThreadedListener;
import com.esotericsoftware.minlog.Log;

public class NetworkingManager {
	
	private ClientWrapper clientWrapper;
	
	public NetworkingManager() {
		clientWrapper = new ClientWrapper();
	}
	
	public boolean init() {
		Log.set(Log.LEVEL_DEBUG);
		clientWrapper.start();
		Network.register(clientWrapper);
		clientWrapper.addListener(new ThreadedListener(new ClientListener()));
		
		try {
			clientWrapper.connect(10000, Network.serverIP, Network.tcpPort, Network.udpPort);
		} catch(IOException e) {
//			e.printStackTrace();
			Log.error(e.getMessage());
			return false;
		}
		
		return true;
	}

	public ClientWrapper getClientWrapper() {
		return clientWrapper;
	}
	
}
