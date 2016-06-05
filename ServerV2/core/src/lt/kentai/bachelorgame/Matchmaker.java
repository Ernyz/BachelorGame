package lt.kentai.bachelorgame;

import com.badlogic.gdx.utils.Array;

import lt.kentai.bachelorgame.AccountConnection.ConnectionState;
import lt.kentai.bachelorgame.level_generation.LevelGenerator;
import lt.kentai.bachelorgame.networking.Network.Matchmaking;
import lt.kentai.bachelorgame.networking.Network.PlayerLeftMatchmaking;

public class Matchmaker {
	
	private int matchIdCounter = 0;
	
	private Array<Match> matchArray;
	private Array<AccountConnection> connectionsInMatchmaking;
	
	private LevelGenerator levelGenerator;
	
	public Matchmaker(Array<Match> matchArray) {
		this.matchArray = matchArray;
		connectionsInMatchmaking = new Array<AccountConnection>();
		
		levelGenerator = new LevelGenerator();
	}
	
	public void updateMatches(float delta) {
		for(Match m : matchArray) {
			m.update(delta);
		}
	}
	
	public void addConnection(AccountConnection c) {
		connectionsInMatchmaking.insert(0, c);
		Matchmaking m = new Matchmaking();
		m.entering = true;
		c.sendTCP(m);
		
		if(connectionsInMatchmaking.size >= Properties.TeamSize *2) {
			createNewMatch();
		}
	}
	
	public void removeConnectionFromMatchmaking(AccountConnection c) {
		connectionsInMatchmaking.removeValue(c, false);
		Matchmaking m = new Matchmaking();
		m.entering = false;
		if(c.isConnected()) {
			c.sendTCP(m);
		}
	}
	
	private void createNewMatch() {
		AccountConnection matchmakedConnection;
		Array<AccountConnection> matchmakedConnections = new Array<AccountConnection>();
		for(int i=0; i<Properties.TeamSize*2; i++) {
			matchmakedConnection = connectionsInMatchmaking.pop();
			matchmakedConnection.connectionState = ConnectionState.IN_CHAMPION_SELECT;
			matchmakedConnections.add(matchmakedConnection);
		}
		Match match = new Match(matchIdCounter);
		levelGenerator.setSEED(match.getSeed());
		char[][] map = levelGenerator.generateLevel();
		match.setMap(map);
		match.fillTeams(matchmakedConnections);
		matchIdCounter++;
		matchArray.add(match);
	}
	
	public void destroyMatch(int matchId) {
		Match match = getMatchById(matchId);
		/*for(AccountConnection ac : match.getAllConnections()) {
			if(ac.isConnected()) {
				ac.sendTCP(new PlayerLeftMatchmaking());
			}
		}*/
		matchArray.removeValue(match, false);
	}
	
	public Match getMatchById(int id) {
		for(Match m : matchArray) {
			if(m.getMatchId() == id) {
				return m;
			}
		}
		return null;
	}
	
	public Match getMatchByConnectionId(final int playerId) {
		Match match = null;
		for(int i = 0; i < matchArray.size; i++) {
			if(matchArray.get(i).hasConnection(playerId)) {
				match = matchArray.get(i);
			}
		}
		
		return match;
	}

	public Array<AccountConnection> getConnectionsInMatchmaking() {
		return connectionsInMatchmaking;
	}

	public Array<Match> getMatchArray() {
		return matchArray;
	}
	
	
}
