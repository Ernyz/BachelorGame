package lt.kentai.bachelorgame;

import com.esotericsoftware.kryonet.Connection;

public class AccountConnection extends Connection {
	public String connectionName;
	public boolean lockedIn=false;
	
	public static enum ConnectionState {
		IN_LOGIN,
		IN_MAIN_MENU,
		IN_MATCHMAKING,
		IN_CHAMPION_SELECT,
		IN_GAME
	}
	public ConnectionState connectionState;
}
