package lt.kentai.bachelorgame;

import java.util.HashMap;

import com.badlogic.gdx.utils.Array;

import lt.kentai.bachelorgame.Properties.Team;
import lt.kentai.bachelorgame.model.ChampionData;
import lt.kentai.bachelorgame.model.ChampionsProperties;
import lt.kentai.bachelorgame.networking.Network.AcceptedToLobby;
import lt.kentai.bachelorgame.networking.Network.ChampionSelectResponse;
import lt.kentai.bachelorgame.networking.Network.MatchReady;

/**
 * Holds teams, map, minions, towers and all stuff related to a single match.
 * 
 * @author Ernyz
 */
public class Match {

	private final int matchId;
	
	private HashMap<Integer, Team> connectionIds = new HashMap<Integer, Team>();
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
			connectionIds.put(blueTeam.get(i).getID(), Team.BLUE);
			redTeam.add(matchmakedConnections.pop());
			connectionIds.put(redTeam.get(i).getID(), Team.RED);
		}
		
		ChampionData champion;
		for(AccountConnection c : blueTeam) {
			champion = new ChampionData(c.connectionName, c.getID(), Team.BLUE, 0, 0);
			champions.add(champion);
			AcceptedToLobby acceptedToLobbyPacket = new AcceptedToLobby(matchId);
			acceptedToLobbyPacket.team = Team.BLUE;
			acceptedToLobbyPacket.connectionIds = connectionIds;
			acceptedToLobbyPacket.championNames = ChampionsProperties.championNames;
			c.sendTCP(acceptedToLobbyPacket);
		}
		for(AccountConnection c : redTeam) {
			champion = new ChampionData(c.connectionName, c.getID(), Team.RED, 100, 0);
			champions.add(champion);
			AcceptedToLobby acceptedToLobbyPacket = new AcceptedToLobby(matchId);
			acceptedToLobbyPacket.team = Team.RED;
			acceptedToLobbyPacket.connectionIds = connectionIds;
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
	
	/**
	 * Gets called when player tries to select a champion in lobby. */
	public void processChampionSelection(final int connectionId, String championName) {
		//Check if champion is not taken yet
		for(int i = 0; i < champions.size; i++) {
			if(champions.get(i).getChampionName() != null && champions.get(i).getChampionName().equals(championName)) {
				//Send negative response
				sendToAllInTeamTCP(champions.get(i).getTeam(), new ChampionSelectResponse(connectionId, championName, false));
				return;
			}
		}
		//If champion is free, send confirmation to everyone IN THE TEAM
		sendToAllInTeamTCP(connectionIds.get(connectionId), new ChampionSelectResponse(connectionId, championName, true));
	}
	
	public void sendToAllInTeamTCP(Team team, Object o) {
		if(team == Team.BLUE) {
			for(AccountConnection c : blueTeam) {
				c.sendTCP(o);
			}
		} else {
			for(AccountConnection c : redTeam) {
				c.sendTCP(o);
			}
		}
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
