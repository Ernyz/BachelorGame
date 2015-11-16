package lt.kentai.bachelorgame;

import com.badlogic.gdx.utils.Array;

import lt.kentai.bachelorgame.Network.AcceptedToLobby;
import lt.kentai.bachelorgame.Properties.Team;
import lt.kentai.bachelorgame.model.ChampionData;
import lt.kentai.bachelorgame.model.ChampionInfo;

/**
 * Holds teams, map, minions, towers and all stuff related to a single match.
 * 
 * @author ernyz
 */
public class Match {

	private final int matchId;
	
	/* 
	 * TODO: Should probably remove separation to teams,
	 * since it only makes hard to iterate and gives no real convenience.
	 */
	private Array<AccountConnection> blueTeam = new Array<AccountConnection>();
	private Array<AccountConnection> redTeam = new Array<AccountConnection>();
	private Array<ChampionData> champions = new Array<ChampionData>();
	
	private char[][] map;
	
	public Match(int matchId, Array<AccountConnection> matchmakedConnections) {
		this.matchId = matchId;
		fillTeams(matchmakedConnections);  //TODO: Maybe matchmaker should do this?..
	}
	
	private void fillTeams(Array<AccountConnection> matchmakedConnections) {
		matchmakedConnections.shuffle();
		
		for(int i=0; i<Properties.TeamSize; i++) {
			blueTeam.add(matchmakedConnections.pop());
			redTeam.add(matchmakedConnections.pop());
		}
		
		ChampionData champion;
		for(AccountConnection c : blueTeam) {
			c.team = Team.BLUE;
			champion = new ChampionData(c.connectionName, c.getID(), c.team, 0, 0);
			champions.add(champion);
			AcceptedToLobby acceptedToLobbyPacket = new AcceptedToLobby(matchId);
			acceptedToLobbyPacket.team = c.team;
			acceptedToLobbyPacket.championNames = ChampionInfo.championNames;
			c.sendTCP(acceptedToLobbyPacket);
		}
		for(AccountConnection c : redTeam) {
			c.team = Team.RED;
			champion = new ChampionData(c.connectionName, c.getID(), c.team, 100, 0);
			champions.add(champion);
			AcceptedToLobby acceptedToLobbyPacket = new AcceptedToLobby(matchId);
			acceptedToLobbyPacket.team = c.team;
			acceptedToLobbyPacket.championNames = ChampionInfo.championNames;
			c.sendTCP(acceptedToLobbyPacket);
		}
	}
	
	public void lockInChampion(AccountConnection lockedInConnection, String championName) {
//		for(ChampionData cd : champions) {
//			if(cd.getConnectionId() == lockedInConnection.getID()) {
//				cd.setChampionName(championName);
//			}
//		}
		for(int i = 0; i < champions.size; i++) {
			if(champions.get(i).getConnectionId() == lockedInConnection.getID()) {
				System.out.println(champions.get(i).getAccountName());
				champions.get(i).setChampionName(championName);
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
