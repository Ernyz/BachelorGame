package lt.kentai.bachelorgame;

import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryonet.Server;

import lt.kentai.bachelorgame.Network.Matchmaking;
import lt.kentai.bachelorgame.screens.ServerScreen.GameConnection;

public class Matchmaker {
	
	private Server server;
	private Array<GameConnection> connectionsInMatchmaking;
	private int teamSize = 1;
	
	public Matchmaker(Server server) {
		this.server = server;
		connectionsInMatchmaking = new Array<GameConnection>();
	}
	
	public void addConnection(GameConnection c) {
		connectionsInMatchmaking.insert(0, c);
		Matchmaking m = new Matchmaking();
		m.entering = true;
		c.sendTCP(m);
		//Improve this logic later
		if(connectionsInMatchmaking.size >= teamSize *2) {
			GameConnection matchmakedConnection;
			for(int i=0; i<teamSize*2; i++) {
				matchmakedConnection = connectionsInMatchmaking.pop();
				//TODO: send "accepted to lobby" or smth like that
			}
		}
	}

	public void removeConnection(GameConnection c) {
		connectionsInMatchmaking.removeValue(c, false);
		Matchmaking m = new Matchmaking();
		m.entering = false;
		c.sendTCP(m);
	}
	
}
