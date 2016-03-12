package lt.kentai.bachelorgame.networking;

import java.util.HashMap;

import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import lt.kentai.bachelorgame.Properties;
import lt.kentai.bachelorgame.Properties.Team;
import lt.kentai.bachelorgame.model.ChampionData;

public class Network {
	public static final String serverIP = "127.0.0.1";
	public static final int tcpPort = 54555;
	public static final int udpPort = 54777;
	
	public static void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(PacketHeader.class);  //XXX: Check if this is really needed
		kryo.register(LoginRequest.class);
		kryo.register(LoginResult.class);
		kryo.register(Matchmaking.class);
		kryo.register(HashMap.class);
		kryo.register(AcceptedToLobby.class);
		kryo.register(PlayerLeftMatchmaking.class);
		kryo.register(PlayerLeftGame.class);
		kryo.register(AllLockedIn.class);
		kryo.register(Properties.Team.class);
		kryo.register(RequestForMatchInfo.class);
		kryo.register(MatchInfo.class);
		kryo.register(Array.class);
		kryo.register(Object[].class);
		kryo.register(String[].class);
		kryo.register(ChampionData.class);
		kryo.register(LockIn.class);
		kryo.register(MatchReady.class);
		kryo.register(ChampionSelect.class);
		kryo.register(ChampionSelectResponse.class);
		kryo.register(MoveChampion.class);
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
		public int matchId;
		public Team team;
		public HashMap<Integer, Team> connectionIds;
		public String[] championNames;
		public AcceptedToLobby() {}
		public AcceptedToLobby(final int matchId) {
			this.matchId = matchId;
		}
	}
	
	public static class PlayerLeftMatchmaking {
		public int playerId;
	}
	
	public static class PlayerLeftGame {
		public int playerId;
	}
	
	public static class AllLockedIn {
	}
	
	public static class RequestForMatchInfo {
		public int matchId;
		public RequestForMatchInfo() {}
		public RequestForMatchInfo(final int matchId) {
			this.matchId = matchId;
		}
	}
	
	public static class MatchInfo {
		public Array<ChampionData> champions;
		public int seed;
	}
	
	public static class LockIn {
		public int matchId;
		public String championName;
	}
	
	public static class MatchReady {
		public int matchId;
		public MatchReady() {}
		public MatchReady(int matchId) {
			this.matchId = matchId;
		};
	}
	
	public static class ChampionSelect {
		public int matchId;
		public String name;
		public ChampionSelect() {}
		public ChampionSelect(String name, int matchId) {
			this.name = name;
			this.matchId = matchId;
		}
	}
	
	public static class ChampionSelectResponse {
		public int connectionId;
		public String championName;
		public boolean success;
		public ChampionSelectResponse() {}
		public ChampionSelectResponse(final int connectionId, String championName, boolean success) {
			this.connectionId = connectionId;
			this.championName = championName;
			this.success = success;
		}
	}
	
	public static abstract class PacketHeader {
		public int sequenceNumber;
		public int ack;
		public boolean ackBitfield[];
		public PacketHeader() {
		}
		public PacketHeader(int sequenceNumber, int ack, boolean[] ackBitfield) {
			this.sequenceNumber = sequenceNumber;
			this.ack = ack;
			this.ackBitfield = ackBitfield;
		}
	}
	
	public static class MoveChampion extends PacketHeader {
		public float x = 0f;
		public float y = 0f;
		public int matchId;
		public MoveChampion() {}
		public MoveChampion(int matchId, float x, float y) {
			this.matchId = matchId;
			this.x = x;
			this.y = y;
		}
		public MoveChampion(int sequenceNumber, int ack, boolean[] ackBitfield,
				int matchId, float x, float y) {
			super(sequenceNumber, ack, ackBitfield);
			this.matchId = matchId;
			this.x = x;
			this.y = y;
		}
	}
}
