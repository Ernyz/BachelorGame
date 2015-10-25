package lt.kentai.bachelorgame;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Network {
	public static final int tcpPort = 54555;
	public static final int udpPort = 54777;
	
	public static void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(LoginRequest.class);
		kryo.register(LoginResult.class);
	}
	
	public static class LoginRequest {
		public String username;
	}
	
	public static class LoginResult {
		public boolean success;
		public String message;
	}
}
