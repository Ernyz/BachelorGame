package lt.kentai.bachelorgame.networking;

import com.esotericsoftware.kryonet.Client;

import lt.kentai.bachelorgame.networking.Network.PacketHeader;
import lt.kentai.bachelorgame.utils.UInt;

public class ClientWrapper extends Client {
	
	private UInt localSequenceNumber = new UInt(0);
	private UInt remoteSequenceNumber = new UInt(0);
	private int numberOfAcks = 32;
	private boolean[] ackBitfield = new boolean[numberOfAcks];
	
	public int sendTCP(PacketHeader packetHeader) {
		packetHeader.sequenceNumber = localSequenceNumber;
		packetHeader.ack = remoteSequenceNumber;
		localSequenceNumber.increment();
		return super.sendTCP(packetHeader);
	}
}
