package lt.kentai.bachelorgame.networking;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import lt.kentai.bachelorgame.GameClientV2;
import lt.kentai.bachelorgame.networking.Network.AcceptedToLobby;
import lt.kentai.bachelorgame.networking.Network.AllLockedIn;
import lt.kentai.bachelorgame.networking.Network.ChampionSelectResponse;
import lt.kentai.bachelorgame.networking.Network.LoginResult;
import lt.kentai.bachelorgame.networking.Network.MatchInfo;
import lt.kentai.bachelorgame.networking.Network.MatchReady;
import lt.kentai.bachelorgame.networking.Network.Matchmaking;
import lt.kentai.bachelorgame.networking.Network.MoveChampion;
import lt.kentai.bachelorgame.networking.Network.PacketHeader;
import lt.kentai.bachelorgame.networking.Network.PlayerLeftGame;
import lt.kentai.bachelorgame.networking.Network.PlayerLeftMatchmaking;
import lt.kentai.bachelorgame.screens.LoginScreen;
import lt.kentai.bachelorgame.ui.LoginFailureDialog;

public class ClientListener extends Listener {

	public ClientListener() {
		
	}
	
	public void received(Connection c, Object o) {
		
		if(o instanceof PacketHeader) {
			final PacketHeader packet = (PacketHeader) o;
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					ClientWrapper clientWrapper = GameClientV2.getNetworkingManager().getClientWrapper();
					//Update remote sequence number
					if(packet.sequenceNumber > clientWrapper.remoteSequenceNumber.getValue()) {
						clientWrapper.remoteSequenceNumber.setValue(packet.sequenceNumber);
					}
					//Mark appropriate ackBitfield bit
					int index = clientWrapper.remoteSequenceNumber.getValue() - packet.sequenceNumber -1;
					if(-1 < index && index < clientWrapper.numberOfAcks) {
						clientWrapper.ackBitfield[index] = true;
					}
					//TODO: Read received packet ackBitfield and decide what to do with lost packets
					for(int i = 0; i < clientWrapper.numberOfAcks; i++) {
						//packet.ackBitfield[i]
					}
					//Add this packet to jitter buffer
					GameClientV2.getNetworkingManager().getClientWrapper().jitterBuffer.addPacket(packet);
				}
			});
		}
		
		if(o instanceof LoginResult) {
			LoginResult loginResult = (LoginResult)o;
			if(loginResult.success) {
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
