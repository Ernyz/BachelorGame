package lt.kentai.bachelorgame;

import com.esotericsoftware.kryonet.Connection;

import lt.kentai.bachelorgame.Properties.Team;

public class AccountConnection extends Connection {
	public String connectionName;
	public Team team;  //XXX: Is storing team in the connection really the best option?
}
