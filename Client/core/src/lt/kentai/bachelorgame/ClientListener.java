package lt.kentai.bachelorgame;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import lt.kentai.bachelorgame.Network.AcceptedToLobby;
import lt.kentai.bachelorgame.Network.LoginResult;
import lt.kentai.bachelorgame.Network.MatchInfo;
import lt.kentai.bachelorgame.Network.MatchReady;
import lt.kentai.bachelorgame.Network.Matchmaking;
import lt.kentai.bachelorgame.screens.GameScreen;
import lt.kentai.bachelorgame.screens.LoginScreen;
import lt.kentai.bachelorgame.screens.MainMenuScreen;
import lt.kentai.bachelorgame.screens.ScreenManager;

public class ClientListener extends Listener {
	
	public ClientListener() {
	}
	
	public void received(Connection c, Object o) {
		if(o instanceof LoginResult) {
			LoginResult loginResult = (LoginResult)o;
			if(loginResult.success) {
				if(ScreenManager.getCurrentScreen() instanceof LoginScreen) {
					((LoginScreen) ScreenManager.getCurrentScreen()).switchToMainMenuScreen(); 
				} else {
					//TODO: Some errror or something. Warn about it
				}
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
			if(ScreenManager.getCurrentScreen() instanceof MainMenuScreen) {
				final int matchId = ((AcceptedToLobby) o).matchId;
				final String[] championNames = ((AcceptedToLobby) o).championNames;
				((MainMenuScreen) ScreenManager.getCurrentScreen()).switchToLobbyScreen(matchId, championNames); 
			} else {
				//TODO: Some error or something. Warn about it
			}
		} else if(o instanceof MatchInfo) {
			MatchInfo matchInfo = (MatchInfo) o;
			if(ScreenManager.getCurrentScreen() instanceof GameScreen) {
				((GameScreen)ScreenManager.getCurrentScreen()).setInitializeMatch(matchInfo);
			}
		} else if(o instanceof MatchReady) {
			if(ScreenManager.getCurrentScreen() instanceof GameScreen) {
				((GameScreen)ScreenManager.getCurrentScreen()).setMatchReady();
			}
		}
	}
}
