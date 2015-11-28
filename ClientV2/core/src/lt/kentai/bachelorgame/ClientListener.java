package lt.kentai.bachelorgame;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import lt.kentai.bachelorgame.Network.AcceptedToLobby;
import lt.kentai.bachelorgame.Network.LoginResult;
import lt.kentai.bachelorgame.Network.MatchInfo;
import lt.kentai.bachelorgame.Network.MatchReady;
import lt.kentai.bachelorgame.Network.Matchmaking;
import lt.kentai.bachelorgame.Network.MoveChampion;

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
						GameClientV2.getScreenManager().switchToMainMenuScreen();
					}
				});
			} else {
				//TODO: write failure msg to current screen
				Log.info("[CLIENT]" + loginResult.message);
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
					GameClientV2.getScreenManager().switchToLobbyScreen(lobbyInfo.matchId, lobbyInfo.championNames);
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
		}
		//XXX: Test part
		else if(o instanceof MoveChampion) {
			final MoveChampion moveChampion = (MoveChampion) o;
			Gdx.app.postRunnable(new Runnable() {
				public void run() {
					GameClientV2.getScreenManager().getGameScreen().movePlaya(moveChampion.x, moveChampion.y);
				}
			});
		}
	}
	
}