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
			serverScreen.addMessage("Connections online: ");
			for(Connection c : server.getConnections()) {
				serverScreen.addMessage(c.toString() + ". Player name: " + ((AccountConnection)c).connectionName);
			}
			serverScreen.addMessage("---");
		} else if(inputText.equals("lmm")) {
			serverScreen.addMessage("Connections in matchmaking:");
			for(int i = 0; i < serverScreen.getMatchmaker().getConnectionsInMatchmaking().size; i++) {
				serverScreen.addMessage(serverScreen.getMatchmaker().getConnectionsInMatchmaking().get(i).connectionName+" ID: "+serverScreen.getMatchmaker().getConnectionsInMatchmaking().get(i).getID());
			}
			serverScreen.addMessage("---");
		} else if(inputText.equals("lm")) {
			serverScreen.addMessage("Active matches("+serverScreen.getMatchmaker().getMatchArray().size+"):");
			for(int i = 0; i < serverScreen.getMatchmaker().getMatchArray().size; i++) {
				serverScreen.addMessage("Match: ID="+serverScreen.getMatchmaker().getMatchArray().get(i).getMatchId());
			}
			serverScreen.addMessage("---");
		} else if(inputText.equals("help")) {
			serverScreen.addMessage("List of commands:");
			serverScreen.addMessage("lc - lists connections");
			serverScreen.addMessage("lmm - lists connections in matchmaking");
			serverScreen.addMessage("lm - lists matches");
			serverScreen.addMessage("exit - exits the server");
		} else {
			serverScreen.addMessage("Command not found: " + inputText + ".");
		}
	}
}
