package lt.kentai.bachelorgame.networking;

import com.esotericsoftware.kryonet.Client;

import lt.kentai.bachelorgame.networking.Network.PacketHeader;
import lt.kentai.bachelorgame.utils.UInt;

public class ClientWrapper extends Client {
	
	public UInt localSequenceNumber = new UInt(0);
	public UInt remoteSequenceNumber = new UInt(0);
	public int numberOfAcks = 32;
	public boolean[] ackBitfield = new boolean[numberOfAcks];
	
	public int sendUDP(PacketHeader packetHeader) {
		packetHeader.sequenceNumber = localSequenceNumber.getValue();
		packetHeader.ack = remoteSequenceNumber.getValue();
		packetHeader.ackBitfield = ackBitfield;
		localSequenceNumber.increment();
		return super.sendUDP(packetHeader);
	}

}
