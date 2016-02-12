package lt.kentai.bachelorgame;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import lt.kentai.bachelorgame.Network.AcceptedToLobby;
import lt.kentai.bachelorgame.Network.AllLockedIn;
import lt.kentai.bachelorgame.Network.ChampionSelectResponse;
import lt.kentai.bachelorgame.Network.LoginResult;
import lt.kentai.bachelorgame.Network.MatchInfo;
import lt.kentai.bachelorgame.Network.MatchReady;
import lt.kentai.bachelorgame.Network.Matchmaking;
import lt.kentai.bachelorgame.Network.MoveChampion;
import lt.kentai.bachelorgame.Network.PlayerLeftGame;
import lt.kentai.bachelorgame.Network.PlayerLeftMatchmaking;
import lt.kentai.bachelorgame.screens.LoginScreen;
import lt.kentai.bachelorgame.ui.LoginFailureDialog;

public class ClientListener extends Listener {

	public ClientListener() {
		
	}
	
	public void received(Connection c, Object o) {
		if(o instanceof LoginResult) {
			LoginResult loginResult = (LoginResult)o;
			if(loginResult.success) {
				//Switch to main menu screen
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						GameClientV2.getScreenManager().switchToMainMenuScreen(false);
					}
				});
			} else {
				Log.info("[CLIENT]" + loginResult.message);
				Gdx.app.postRunnable(new Runnable() {
					public void run() {
						if(GameClientV2.getScreenManager().getCurrentScreen() instanceof LoginScreen) {
							LoginScreen loginScreen = (LoginScreen) GameClientV2.getScreenManager().getCurrentScreen();
							LoginFailureDialog dialog = new LoginFailureDialog("Login failed", "Login data was incorrect. Please try again", loginScreen.getSkin());
							dialog.show(loginScreen.getStage());
						}
					}
				});
			}
		} else if(o instanceof Matchmaking) {
			Matchmaking m = (Matchmaking) o;
			if(m.entering) {
				Log.info("Matchmaking entered.");
			} else {
				Log.info("Matchmaking left.");
			}
		} else if(o instanceof AcceptedToLobby) {
			final AcceptedToLobby lobbyInfo = (AcceptedToLobby) o;
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					GameClientV2.getScreenManager().switchToLobbyScreen(lobbyInfo);
				}
			});
		} else if(o instanceof PlayerLeftMatchmaking) {
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					GameClientV2.getScreenManager().switchToMainMenuScreen(true);
				}
			});
		} else if(o instanceof PlayerLeftGame) {
			
		} else if(o instanceof AllLockedIn) {
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					GameClientV2.getScreenManager().getLobbyScreen().setChampionSelectionTimer(5f);
				}
			});
		} else if(o instanceof MatchReady) {
			final int matchId = ((MatchReady) o).matchId;
			Gdx.app.postRunnable(new Runnable() {
				public void run() {
					GameClientV2.getScreenManager().switchToGameScreen(matchId);
				}
			});
		} else if(o instanceof MatchInfo) {
			final MatchInfo matchInfo = (MatchInfo) o;
			Gdx.app.postRunnable(new Runnable() {
				public void run() {
					GameClientV2.getScreenManager().getGameScreen().initializeMatch(matchInfo);
				}
			});
		} else if(o instanceof ChampionSelectResponse) {
			final ChampionSelectResponse response = (ChampionSelectResponse) o;
			Gdx.app.postRunnable(new Runnable() {
				public void run() {
					GameClientV2.getScreenManager().getLobbyScreen().selectChampion(response);
				}
			});
		}
		
		else if(o instanceof MoveChampion) {
			final MoveChampion moveChampion = (MoveChampion) o;
			Gdx.app.postRunnable(new Runnable() {
				public void run() {
//					GameClientV2.getScreenManager().getGameScreen().movePlaya(moveChampion.x, moveChampion.y);
				}
			});
		}
	}
	
}
