package lt.kentai.bachelorgame.networking;

import com.esotericsoftware.kryonet.Server;

import lt.kentai.bachelorgame.AccountConnection;
import lt.kentai.bachelorgame.networking.Network.PacketHeader;

public class ServerWrapper extends Server {
	
	public ServerWrapper() {
		
	}
	
	public void sendUDP(AccountConnection connection, PacketHeader packetHeader) {
		packetHeader.sequenceNumber = connection.localSequenceNumber.getValue();
		packetHeader.ack = connection.remoteSequenceNumber.getValue();
		packetHeader.ackBitfield = connection.ackBitfield;
		connection.localSequenceNumber.increment();
		
		super.sendToUDP(connection.getID(), packetHeader);
	}
}
