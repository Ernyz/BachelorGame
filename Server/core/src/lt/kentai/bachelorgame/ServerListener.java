package lt.kentai.bachelorgame;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

public class ServerListener extends Listener {
	public void received(Connection c, Object o) {
		
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