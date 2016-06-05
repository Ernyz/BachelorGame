package lt.kentai.bachelorgame.networking;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import lt.kentai.bachelorgame.AccountConnection;
import lt.kentai.bachelorgame.AccountConnection.ConnectionState;
import lt.kentai.bachelorgame.Properties;
import lt.kentai.bachelorgame.controller.PlayerInputManager;
import lt.kentai.bachelorgame.GameServerV2;
import lt.kentai.bachelorgame.Match;
import lt.kentai.bachelorgame.database_managment.dto.User;
import lt.kentai.bachelorgame.database_managment.service.UserService;
import lt.kentai.bachelorgame.networking.Network.ChampionSelect;
import lt.kentai.bachelorgame.networking.Network.LockIn;
import lt.kentai.bachelorgame.networking.Network.LoginRequest;
import lt.kentai.bachelorgame.networking.Network.LoginResult;
import lt.kentai.bachelorgame.networking.Network.MatchInfo;
import lt.kentai.bachelorgame.networking.Network.Matchmaking;
import lt.kentai.bachelorgame.networking.Network.PlayerLeftGame;
import lt.kentai.bachelorgame.networking.Network.RequestForMatchInfo;
import lt.kentai.bachelorgame.networking.Network.UserInput;
import lt.kentai.bachelorgame.screens.ServerScreen;

public class ServerListener extends Listener {

	private UserService userService;

	public ServerListener() {
		userService = new UserService();

	}
	
	@Override
	public void disconnected(Connection c) {

		final AccountConnection accountConnection = (AccountConnection) c;
//		final int connectionId = accountConnection.getID();
		System.out.println("Disc");
		Gdx.app.postRunnable(new Runnable() {
			public void run() {
				//Remove player from matchmaking if in matchmaking phase
				if(accountConnection.connectionState == ConnectionState.IN_MATCHMAKING) {
					GameServerV2.getServerScreen().getMatchmaker().removeConnectionFromMatchmaking(accountConnection);
				}
				//Remove player from lobby if in champion selection phase
				if(accountConnection.connectionState == ConnectionState.IN_CHAMPION_SELECT) {
					final Match match = GameServerV2.getServerScreen().getMatchmaker().getMatchByConnectionId(accountConnection.getID());
					System.out.println("match id:" + match.getMatchId());
					if(match != null) {
						System.out.println("destroy match : "+ match.getMatchId());

						for(AccountConnection ac : match.getAllConnections()) {
							if(ac.isConnected()) {
								ac.sendTCP(new Network.PlayerLeftMatchmaking());
							}
						}
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
		
		if(o instanceof LoginRequest) {
			final LoginRequest loginRequest = (LoginRequest) o;
			Log.info(loginRequest.username + " is trying to connect.");
			if(true){//TODO debug for demologin
				LoginResult loginResult = new LoginResult();
				loginResult.success = true;
				loginResult.message = "Login "+(loginResult.success?"successful!":"failed");
				accountConnection.sendTCP(loginResult);
				accountConnection.connectionName = loginRequest.username;
				accountConnection.connectionState = ConnectionState.IN_MAIN_MENU;
				Gdx.app.postRunnable(new Runnable() {
					public void run() {
						GameServerV2.getServerScreen().addMessage(loginRequest.username + " has successfully connected!");
					}
				});
				return;
			}
			LoginResult loginResult = new LoginResult();
			loginResult.success = userService.loginUser(new User(loginRequest.username,loginRequest.password));
			loginResult.message = "Login "+(loginResult.success?"successful!":"failed");
			accountConnection.sendTCP(loginResult);
			if(loginResult.success){
				accountConnection.connectionName = loginRequest.username;
				accountConnection.connectionState = ConnectionState.IN_MAIN_MENU;
				Gdx.app.postRunnable(new Runnable() {
					public void run() {
						GameServerV2.getServerScreen().addMessage(loginRequest.username + " has successfully connected!");
					}
				});
			}else{
				accountConnection.connectionState = ConnectionState.IN_LOGIN;
				Gdx.app.postRunnable(new Runnable() {
					public void run() {
						GameServerV2.getServerScreen().addMessage(loginRequest.username + " did not connect.");
					}
				});
			}
//
//
//			if(loginRequest.username != "" && loginRequest.username != null) {
//				//DB check if exists
//				LoginResult loginResult = new LoginResult();
//				loginResult.success = userService.loginUser(new User(loginRequest.username,loginRequest.password));
//				loginResult.message = "Login "+(loginResult.success?"successful!":"failed");
//				accountConnection.sendTCP(loginResult);
//				accountConnection.connectionName = loginRequest.username;
//				accountConnection.connectionState = ConnectionState.IN_MAIN_MENU;
//				Gdx.app.postRunnable(new Runnable() {
//					public void run() {
//						GameServerV2.getServerScreen().addMessage(loginRequest.username + " has successfully connected!");
//					}
//				});
//			} else {
//				LoginResult loginResult = new LoginResult();
//				loginResult.success = false;
//				loginResult.message = "Invalid login data.";
//				accountConnection.sendTCP(loginResult);
//				accountConnection.connectionState = ConnectionState.IN_LOGIN;
//				Gdx.app.postRunnable(new Runnable() {
//					public void run() {
//						GameServerV2.getServerScreen().addMessage(loginRequest.username + " did not connect.");
//					}
//				});
//			}
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
					matchInfo.champions = match.getChampionDataArray();
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
		} else if(o instanceof UserInput) {
			final int connectionId = c.getID();
			final int matchId = ((UserInput) o).matchId;
			final UserInput userInput = (UserInput) o;
			Gdx.app.postRunnable(new Runnable() {
				public void run() {
					//PlayerInputManager.applyInput(connectionId, userInput,
					//		GameServerV2.getServerScreen().getMatchmaker().getMatchById(matchId));
					GameServerV2.getServerScreen().getMatchmaker().getMatchById(matchId).
						getInputPacketManager().addPacket(connectionId, userInput);
				}
			});
		}else if(o instanceof Network.RegistrationRequest){
			userService.registerUser(new User(((Network.RegistrationRequest) o).username,((Network.RegistrationRequest) o).password,((Network.RegistrationRequest) o).email));
		}
	}
	
}
