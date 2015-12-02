package lt.kentai.bachelorgame;

import com.badlogic.gdx.utils.Array;

import lt.kentai.bachelorgame.Properties.Team;
import lt.kentai.bachelorgame.model.ChampionData;
import lt.kentai.bachelorgame.model.ChampionsProperties;
import lt.kentai.bachelorgame.networking.Network.AcceptedToLobby;
import lt.kentai.bachelorgame.networking.Network.MatchReady;

/**
 * Holds teams, map, minions, towers and all stuff related to a single match.
 * 
 * @author Ernyz
 */
public class Match {

	private final int matchId;
	
	private Array<AccountConnection> blueTeam = new Array<AccountConnection>();
	private Array<AccountConnection> redTeam = new Array<AccountConnection>();
	private Array<ChampionData> champions = new Array<ChampionData>();
	
	private char[][] map;
	
	public Match(int matchId, Array<AccountConnection> matchmakedConnections) {
		this.matchId = matchId;
		fillTeams(matchmakedConnections);
	}
	
	private void fillTeams(Array<AccountConnection> matchmakedConnections) {
		matchmakedConnections.shuffle();
		
		for(int i=0; i<Properties.TeamSize; i++) {
			blueTeam.add(matchmakedConnections.pop());
			redTeam.add(matchmakedConnections.pop());
		}
		
		ChampionData champion;
		for(AccountConnection c : blueTeam) {
			champion = new ChampionData(c.connectionName, c.getID(), Team.BLUE, 0, 0);
			champions.add(champion);
			AcceptedToLobby acceptedToLobbyPacket = new AcceptedToLobby(matchId);
			acceptedToLobbyPacket.team = Team.BLUE;
			acceptedToLobbyPacket.championNames = ChampionsProperties.championNames;
			c.sendTCP(acceptedToLobbyPacket);
		}
		for(AccountConnection c : redTeam) {
			champion = new ChampionData(c.connectionName, c.getID(), Team.RED, 100, 0);
			champions.add(champion);
			AcceptedToLobby acceptedToLobbyPacket = new AcceptedToLobby(matchId);
			acceptedToLobbyPacket.team = Team.RED;
			acceptedToLobbyPacket.championNames = ChampionsProperties.championNames;
			c.sendTCP(acceptedToLobbyPacket);
		}
	}
	
	public void lockInChampion(AccountConnection lockedInConnection, String championName) {
		for(int i = 0; i < champions.size; i++) {
			if(champions.get(i).getConnectionId() == lockedInConnection.getID()) {
				setupChampion(champions.get(i), championName);
			}
		}
		
		if(ready()) {
			for(AccountConnection c : blueTeam) {
				c.sendTCP(new MatchReady(matchId));
			}
			for(AccountConnection c : redTeam) {
				c.sendTCP(new MatchReady(matchId));
			}
		}
	}
	
	private void setupChampion(ChampionData c, String name) {
		c.setChampionName(name);
		if(name.equals(ChampionsProperties.Champion1.championName)) {
			c.setSpeed(ChampionsProperties.Champion1.speed);
		} else if(name.equals(ChampionsProperties.Champion2.championName)) {
			c.setSpeed(ChampionsProperties.Champion2.speed);
		}
	}
	
	public boolean ready() {
		for(int i = 0; i < champions.size; i++) {
			if(champions.get(i).getChampionName() == null) {
				return false;
			}
		}
		return true;
	}
	
	public void sendToAllExceptUDP(final int idToExclude, Object o) {
		for(AccountConnection c : blueTeam) {
			if(idToExclude != c.getID()) {
				c.sendTCP(o);
			}
		}
		for(AccountConnection c : redTeam) {
			if(idToExclude != c.getID()) {
				c.sendTCP(o);
			}
		}
	}
	
	public void setMap(char[][] map) {
		this.map = map;
	}

	public Array<ChampionData> getChampions() {
		return champions;
	}

	public int getMatchId() {
		return matchId;
	}
	
}
