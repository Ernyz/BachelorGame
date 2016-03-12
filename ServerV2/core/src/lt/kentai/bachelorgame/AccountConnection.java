package lt.kentai.bachelorgame;

import com.esotericsoftware.kryonet.Connection;

import lt.kentai.bachelorgame.utils.UInt;

public class AccountConnection extends Connection {
	public String connectionName;
	
	public UInt localSequenceNumber = new UInt(0);
	public UInt remoteSequenceNumber = new UInt(0);
	public int numberOfAcks = 32;
	public boolean[] ackBitfield = new boolean[numberOfAcks];
	
	public static enum ConnectionState {
		IN_LOGIN,
		IN_MAIN_MENU,
		IN_MATCHMAKING,
		IN_CHAMPION_SELECT,
		IN_GAME
	}
	public ConnectionState connectionState;
}
