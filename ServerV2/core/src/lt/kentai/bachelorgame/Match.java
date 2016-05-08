package lt.kentai.bachelorgame;

import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.minlog.Log;

import lt.kentai.bachelorgame.AccountConnection.ConnectionState;
import lt.kentai.bachelorgame.Properties.Team;
import lt.kentai.bachelorgame.model.ChampionData;
import lt.kentai.bachelorgame.model.ChampionsProperties;
import lt.kentai.bachelorgame.networking.Network;
import lt.kentai.bachelorgame.networking.Network.AcceptedToLobby;
import lt.kentai.bachelorgame.networking.Network.AllLockedIn;
import lt.kentai.bachelorgame.networking.Network.ChampionSelectResponse;
import lt.kentai.bachelorgame.networking.Network.MatchReady;

/**
 * Holds teams, map, minions, towers and all stuff related to a single match.
 *
 * @author Ernyz
 */
public class Match {

	private final int matchId;
	private int seed;

	private float matchTimer = 0f;
	private float accumulator = 0f;

	private enum MatchState {
		SELECTING_CHAMPIONS, PREPARING, LOADING, IN_GAME
	}
	private MatchState matchState;

	private HashMap<Integer, Team> connectionIds = new HashMap<Integer, Team>();
	private Array<AccountConnection> blueTeam = new Array<AccountConnection>();
	private Array<AccountConnection> redTeam = new Array<AccountConnection>();
	private Array<Integer> lockedInConnections = new Array<Integer>();
	private Array<ChampionData> champions = new Array<ChampionData>();

	private char[][] map;

	public Match(int matchId, Array<AccountConnection> matchmakedConnections) {
		seed = new Random().nextInt();
		this.matchId = matchId;
		matchState = MatchState.SELECTING_CHAMPIONS;
		fillTeams(matchmakedConnections);
	}

	public void update(float delta) {
		matchTimer += delta;
		if(matchState == MatchState.IN_GAME) {
			accumulator += delta;
			while(accumulator >= Properties.FRAME_TIME) {
				accumulator -= Properties.FRAME_TIME;
			}
		} else if(matchState == MatchState.SELECTING_CHAMPIONS) {
			if(matchTimer >= 20f) {
				matchTimer = 0f;
				matchState = MatchState.PREPARING;
				sendToAllTCP(new AllLockedIn());  //XXX:Dublicated code
			}
		} else if(matchState == MatchState.PREPARING) {
//			System.out.println(matchTimer + " " + matchState);
            if(Properties.TeamSize*2!=lockedInConnections.size){
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        Network.PlayerAFKCheckFail afkCheckFail = new Network.PlayerAFKCheckFail();
                        Array<Integer> l = new Array<>();
                        for (AccountConnection ac : getAllConnections()){
                            if(!ac.lockedIn){
                                l.add(ac.getID());
                            }
                            ac.lockedIn=false;
                        }
                        afkCheckFail.ids = new int[l.size];
                        for(int i = 0; i < l.size; i++) afkCheckFail.ids[i] = l.get(i);

                        for(AccountConnection ac : getAllConnections()) {
                            if(ac.isConnected()) {
                                ac.sendTCP(afkCheckFail);
                            }
                        }
                        GameServerV2.getServerScreen().getMatchmaker().destroyMatch(matchId);
                    }
                });
                return;
            }

			if(matchTimer >= 5f) {
				if(ready()) {
					sendMatchReady();
					matchState = MatchState.LOADING;
					matchTimer = 0f;
				} else {
					//TODO: disband the match
					Log.info("People not ready");
                    sendToAllTCP(new Network.PlayerLeftLobby());
				}
			}
		} else if(matchState == MatchState.LOADING) {

		}
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
		if(championName == null || championName.equals(""))
            return;
		for(int i = 0; i < champions.size; i++) {
			if(champions.get(i).getConnectionId() == lockedInConnection.getID()) {
				setupChampion(champions.get(i), championName);
				if(!lockedInConnections.contains(lockedInConnection.getID(), true)) {
					lockedInConnections.add(lockedInConnection.getID());
                    lockedInConnection.lockedIn = true;
				}
			}
		}

		if(matchState != MatchState.PREPARING && ready()) {
			matchTimer = 0f;
			matchState = MatchState.PREPARING;
			sendToAllTCP(new AllLockedIn());
		}
	}

	private void sendMatchReady() {
		for(AccountConnection c : blueTeam) {
			c.connectionState = ConnectionState.IN_GAME;
            c.lockedIn=false;
			c.sendTCP(new MatchReady(matchId));
		}
		for(AccountConnection c : redTeam) {
			c.connectionState = ConnectionState.IN_GAME;
            c.lockedIn =false;
			c.sendTCP(new MatchReady(matchId));
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
		if(lockedInConnections.size == Properties.TeamSize*2) {
			return true;
		}
		return false;
	}

	/**
	 * Gets called when player tries to select a champion in lobby. */
	public void processChampionSelection(final int connectionId, String championName) {
		Log.debug("ProcessChampionSelection: Match ID: " + matchId);
		//Check if champion is not taken yet
		for(int i = 0; i < champions.size; i++) {
			if(champions.get(i).getChampionName() != null
					&& !champions.get(i).getChampionName().equals("")
					&& champions.get(i).getChampionName().equals(championName)
					&& champions.get(i).getTeam() == connectionIds.get(connectionId)) {  //This line allows same champions in opposing teams
				//Send negative response
				sendToAllInTeamTCP(champions.get(i).getTeam(), new ChampionSelectResponse(connectionId, championName, false));
				return;
			}
		}
		//If champion is free, send confirmation to everyone IN THE TEAM
		for(int i = 0; i < champions.size; i++) {
			if(champions.get(i).getConnectionId() == connectionId) {
				champions.get(i).setChampionName(championName);
			}
		}
		sendToAllInTeamTCP(connectionIds.get(connectionId), new ChampionSelectResponse(connectionId, championName, true));
	}

	public void sendToAllTCP(Object o) {
		for(AccountConnection c : blueTeam) {
			if(c.isConnected()) {
				c.sendTCP(o);
			}
		}
		for(AccountConnection c : redTeam) {
			if(c.isConnected()) {
				c.sendTCP(o);
			}
		}
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
				c.sendUDP(o);
			}
		}
		for(AccountConnection c : redTeam) {
			if(idToExclude != c.getID()) {
				c.sendUDP(o);
			}
		}
	}

	public boolean hasConnection(final int connectionId) {
		for(AccountConnection c : blueTeam) {
			if(c.getID() == connectionId) {
				return true;
			}
		}
		for(AccountConnection c : redTeam) {
			if(c.getID() == connectionId) {
				return true;
			}
		}

		return false;
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

	public Array<AccountConnection> getAllConnections() {
		Array<AccountConnection> connections = new Array<AccountConnection>();
		connections.addAll(blueTeam);
		connections.addAll(redTeam);

		return connections;
	}

	public int getSeed() {
		return seed;
	}
}
