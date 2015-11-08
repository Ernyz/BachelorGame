package lt.kentai.bachelorgame;

import com.badlogic.gdx.utils.Array;

import lt.kentai.bachelorgame.Network.AcceptedToLobby;
import lt.kentai.bachelorgame.Properties.Team;

/**
 * Holds teams, map, minions, towers and all stuff related to a single match.
 * 
 * @author ernyz
 */
public class Match {

	private Array<AccountConnection> blueTeam = new Array<AccountConnection>();
	private Array<AccountConnection> redTeam = new Array<AccountConnection>();
	
	public Match(Array<AccountConnection> matchmakedConnections) {
		fillTeams(matchmakedConnections);
	}
	
	private void fillTeams(Array<AccountConnection> matchmakedConnections) {
		matchmakedConnections.shuffle();
		
		for(int i=0; i<Properties.TeamSize; i++) {
			blueTeam.add(matchmakedConnections.pop());
			redTeam.add(matchmakedConnections.pop());
		}
		
		for(AccountConnection c : blueTeam) {
			c.team = Team.BLUE;
			c.x = 0;
			c.y = 0;
			AcceptedToLobby acceptedToLobbyPacket = new AcceptedToLobby();
			acceptedToLobbyPacket.team = c.team;
			c.sendTCP(acceptedToLobbyPacket);
		}
		for(AccountConnection c : redTeam) {
			c.team = Team.RED;
			c.x = 100;
			c.y = 0;
			AcceptedToLobby acceptedToLobbyPacket = new AcceptedToLobby();
			acceptedToLobbyPacket.team = c.team;
			c.sendTCP(acceptedToLobbyPacket);
		}
	}

	public Array<AccountConnection> getBlueTeam() {
		return blueTeam;
	}

	public Array<AccountConnection> getRedTeam() {
		return redTeam;
	}
	
}
