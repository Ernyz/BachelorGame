package lt.kentai.bachelorgame;

import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import lt.kentai.bachelorgame.Properties.Team;
import lt.kentai.bachelorgame.model.server_data.ChampionData;

public class Network {
	public static final int tcpPort = 54555;
	public static final int udpPort = 54777;
	
	public static void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(LoginRequest.class);
		kryo.register(LoginResult.class);
		kryo.register(Matchmaking.class);
		kryo.register(AcceptedToLobby.class);
		kryo.register(Properties.Team.class);
		kryo.register(RequestForMatchInfo.class);
		kryo.register(MatchInfo.class);
		kryo.register(Array.class);
		kryo.register(Object[].class);
		kryo.register(String[].class);
		kryo.register(ChampionData.class);
		kryo.register(LockIn.class);
	}
	
	public static class LoginRequest {
		public String username;
	}
	
	public static class LoginResult {
		public boolean success;
		public String message;
	}
	
	public static class Matchmaking {
		/**
		 * True if entering matchmaking, false if leaving. */
		public boolean entering;
	}
	
	public static class AcceptedToLobby {
		//public final int matchId;
		public int matchId;
		public Team team;
		public String[] championNames;
		public AcceptedToLobby() {}
		public AcceptedToLobby(final int matchId) {
			this.matchId = matchId;
		}
	}
	
	public static class RequestForMatchInfo {
		//public final int matchId;
		public int matchId;
		public RequestForMatchInfo() {}
		public RequestForMatchInfo(final int matchId) {
			this.matchId = matchId;
		}
	}
	
	public static class MatchInfo {
		public Array<ChampionData> champions;
	}
	
	public static class LockIn {
		public int matchId;
		public String championName;
	}
}
