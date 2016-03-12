package lt.kentai.bachelorgame.networking;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import lt.kentai.bachelorgame.AccountConnection;
import lt.kentai.bachelorgame.AccountConnection.ConnectionState;
import lt.kentai.bachelorgame.GameServerV2;
import lt.kentai.bachelorgame.Match;
import lt.kentai.bachelorgame.networking.Network.ChampionSelect;
import lt.kentai.bachelorgame.networking.Network.LockIn;
import lt.kentai.bachelorgame.networking.Network.LoginRequest;
import lt.kentai.bachelorgame.networking.Network.LoginResult;
import lt.kentai.bachelorgame.networking.Network.MatchInfo;
import lt.kentai.bachelorgame.networking.Network.Matchmaking;
import lt.kentai.bachelorgame.networking.Network.MoveChampion;
import lt.kentai.bachelorgame.networking.Network.PacketHeader;
import lt.kentai.bachelorgame.networking.Network.PlayerLeftGame;
import lt.kentai.bachelorgame.networking.Network.RequestForMatchInfo;

public class ServerListener extends Listener {

	public ServerListener() {
		
	}
	
	@Override
	public void disconnected(Connection c) {
		final AccountConnection accountConnection = (AccountConnection) c;
//		final int connectionId = accountConnection.getID();
		Gdx.app.postRunnable(new Runnable() {
			public void run() {
				//Remove player from matchmaking if in matchmaking phase
				if(accountConnection.connectionState == ConnectionState.IN_MATCHMAKING) {
					GameServerV2.getServerScreen().getMatchmaker().removeConnectionFromMatchmaking(accountConnection);
				}
				//Remove player from lobby if in champion selection phase
				if(accountConnection.connectionState == ConnectionState.IN_CHAMPION_SELECT) {
					final Match match = GameServerV2.getServerScreen().getMatchmaker().getMatchByConnectionId(accountConnection.getID());
					if(match != null) {
						GameServerV2.getServerScreen().getMatchmaker().destroyMatch(match.getMatchId());
					}
				}
				//Warn other players if the game is in progress
				if(accountConnection.connectionState == ConnectionState.IN_GAME) {
					//Send warning that player disconnected from the game
					final Match match = GameServerV2.getServerScreen().getMatchmaker().getMatchByConnectionId(accountConnection.getID());
					if(match != null) {
						match.sendToAllTCP(new PlayerLeftGame());
						int activeConnections = 0;
						for(int i = 0; i < match.getAllConnections().size; i++) {
							if(match.getAllConnections().get(i).isConnected()) {
								activeConnections++;
							}
						}
						if(activeConnections <= 0) {
							GameServerV2.getServerScreen().getMatchmaker().destroyMatch(match.getMatchId());
						}
					}
				}
			}
		});
	}
	
	@Override
	public void received(Connection c, Object o) {
		//We know that all connections are account connections
		final AccountConnection accountConnection = (AccountConnection) c;
		
		if(o instanceof PacketHeader) {
			final PacketHeader packet = (PacketHeader) o;
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
	//				ServerWrapper serverWrapper = GameServerV2.getNetworkingManager().getServerWrapper();
					//Update remote sequence number
					if(packet.sequenceNumber > accountConnection.remoteSequenceNumber.getValue()) {
						accountConnection.remoteSequenceNumber.setValue(packet.sequenceNumber);
					}
					//Mark appropriate ackBitfield bit
					int index = accountConnection.remoteSequenceNumber.getValue() - packet.sequenceNumber - 1;
					if(-1 < index && index < accountConnection.numberOfAcks) {
						accountConnection.ackBitfield[index] = true;
					}
					//TODO: Read received packet ackBitfield and decide what to do with lost packets
					for(int i = 0; i < accountConnection.numberOfAcks; i++) {
						//packet.ackBitfield[i]
					}
				}
			});
		}
		
		if(o instanceof LoginRequest) {
			final LoginRequest loginRequest = (LoginRequest) o;
			Log.info(loginRequest.username + " is trying to connect.");
			if(loginRequest.username != "" && loginRequest.username != null) {
				LoginResult loginResult = new LoginResult();
				loginResult.success = true;
				loginResult.message = "Login successful!";
				accountConnection.sendTCP(loginResult);
				accountConnection.connectionName = loginRequest.username;
				accountConnection.connectionState = ConnectionState.IN_MAIN_MENU;
				Gdx.app.postRunnable(new Runnable() {
					public void run() {
						GameServerV2.getServerScreen().addMessage(loginRequest.username + " has successfully connected!");
					}
				});
			} else {
				LoginResult loginResult = new LoginResult();
				loginResult.success = false;
				loginResult.message = "Invalid login data.";
				accountConnection.sendTCP(loginResult);
				accountConnection.connectionState = ConnectionState.IN_LOGIN;
				Gdx.app.postRunnable(new Runnable() {
					public void run() {
						GameServerV2.getServerScreen().addMessage(loginRequest.username + " did not connect.");
					}
				});
			}
		} else if(o instanceof Matchmaking) {
			Matchmaking matchmaking = (Matchmaking) o;
			if(matchmaking.entering) {
				accountConnection.connectionState = ConnectionState.IN_MATCHMAKING;
				Gdx.app.postRunnable(new Runnable() {
					public void run() {
						GameServerV2.getServerScreen().addMessage(accountConnection.connectionName + " has entered matchmaking.");
						GameServerV2.getServerScreen().getMatchmaker().addConnection(accountConnection);
					}
				});
			} else {
				accountConnection.connectionState = ConnectionState.IN_MAIN_MENU;
				Gdx.app.postRunnable(new Runnable() {
					public void run() {
						GameServerV2.getServerScreen().addMessage(accountConnection.connectionName + " has left matchmaking.");
						GameServerV2.getServerScreen().getMatchmaker().removeConnectionFromMatchmaking(accountConnection);
					}
				});
			}
		} else if(o instanceof LockIn) {
			final LockIn lockInPacket = (LockIn) o;
			Gdx.app.postRunnable(new Runnable() {
				public void run() {
					Match match = GameServerV2.getServerScreen().getMatchmaker().getMatchById(lockInPacket.matchId);
					match.lockInChampion(accountConnection, lockInPacket.championName);
				}
			});
		} else if(o instanceof RequestForMatchInfo) {
			final int matchId = ((RequestForMatchInfo) o).matchId;
			Gdx.app.postRunnable(new Runnable() {
				public void run() {
					Match match = GameServerV2.getServerScreen().getMatchmaker().getMatchById(matchId);
					MatchInfo matchInfo = new MatchInfo();
					matchInfo.champions = match.getChampions();
					matchInfo.seed = match.getSeed();
					accountConnection.sendTCP(matchInfo);
				}
			});
		} else if(o instanceof ChampionSelect) {
			final int matchId = ((ChampionSelect) o).matchId;
			final String name = ((ChampionSelect) o).name;
			Gdx.app.postRunnable(new Runnable() {
				public void run() {
					GameServerV2.getServerScreen().getMatchmaker().getMatchById(matchId).processChampionSelection(accountConnection.getID(), name);
				}
			});
		}
		
		else if(o instanceof MoveChampion) {
			final MoveChampion moveChampion = (MoveChampion) o;
			Gdx.app.postRunnable(new Runnable() {
				public void run() {
//					GameServerV2.getServerScreen().getMatchmaker().getMatchById(moveChampion.matchId).sendToAllExceptUDP(accountConnection.getID(), moveChampion);
				}
			});
		}
		
	}
	
}
