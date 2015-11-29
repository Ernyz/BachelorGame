package lt.kentai.bachelorgame;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import lt.kentai.bachelorgame.screens.ServerScreen;

public class ConsoleInputManager {

	private ServerScreen serverScreen;
	
	public ConsoleInputManager(ServerScreen serverScreen) {
		this.serverScreen = serverScreen;
	}
	
	public void manageInput(Server server, String inputText) {
		if(inputText.equals("exit")) {
			server.stop();
			Gdx.app.exit();
		} else if(inputText.equals("lc")) {
			if(server.getConnections().length > 0) {
				for(Connection c : server.getConnections()) {
					serverScreen.addMessage(c.toString() + "; Player name: " + ((AccountConnection)c).connectionName);
				}
			} else {
				serverScreen.addMessage("There are no connections at the moment.");
			}
		} else if(inputText.equals("lm")) {
			serverScreen.addMessage("Connections in matchmaking:");
			for(int i = 0; i < serverScreen.getMatchmaker().getConnectionsInMatchmaking().size; i++) {
				serverScreen.addMessage(serverScreen.getMatchmaker().getConnectionsInMatchmaking().get(i).connectionName+" ID: "+serverScreen.getMatchmaker().getConnectionsInMatchmaking().get(i).getID());
			}
			serverScreen.addMessage("---");
		} else {
			serverScreen.addMessage("Command not found: " + inputText + ".");
		}
	}
	
}
