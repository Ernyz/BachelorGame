package lt.kentai.bachelorgame.networking;

import com.badlogic.gdx.utils.Array;

import lt.kentai.bachelorgame.networking.Network.PacketHeader;

public class PacketJitterBuffer {

	private int frameInterval = 4;
	private int frameCounter = 0;
	private Array<PacketHeader> packets = new Array<PacketHeader>();
	
	private Array<PacketHeader> dejitteredPackets = new Array<PacketHeader>();
	
	public PacketJitterBuffer() {
		
	}
	
	public Array<PacketHeader> tick() {
		frameCounter++;
		if(frameCounter >= frameInterval) {
			frameCounter = 0;
			if(packets.size >= 1) {
				dejitteredPackets.insert(0, packets.pop());
			}
		}
		return dejitteredPackets;
	}
	
	public void addPacket(PacketHeader packet) {
		if(packets.size >= 1) {
			for(int i = 0; i < packets.size; i++) {
				if(isMoreRecent(packet.sequenceNumber, packets.get(i).sequenceNumber)) {
					packets.insert(i, packet);
					return;
				}
			}
		}
		packets.add(packet);
	}
	
	/**
	 * Returns true if first packet seq. number is more recent than the second */
	private boolean isMoreRecent(int s1, int s2) {
		return ((s1 > s2) && (s1 - s2 <= Integer.MAX_VALUE/2))
				|| ((s2 > s1) && (s2 - s1 > Integer.MAX_VALUE/2));
	}
	
}
