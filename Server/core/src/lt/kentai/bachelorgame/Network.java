package lt.kentai.bachelorgame;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import lt.kentai.bachelorgame.Network.MatchInfo;
import lt.kentai.bachelorgame.Network.RequestForMatchInfo;
import lt.kentai.bachelorgame.Properties.Team;

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
		public Team team;
	}
	
	public static class RequestForMatchInfo {
	}
	
	public static class MatchInfo {
		public String name;
		public Team team;
		public float x;
		public float y;
	}
}
