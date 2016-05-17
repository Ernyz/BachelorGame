package lt.kentai.bachelorgame.networking;

import java.util.HashMap;

import com.badlogic.gdx.utils.Array;

import lt.kentai.bachelorgame.networking.Network.UserInput;

public class InputPacketManager {
	
	private HashMap<Integer, Array<UserInput>> packets = new HashMap<Integer, Array<UserInput>>();
	
	public InputPacketManager() {
		
	}
	
//	public HashMap<Integer, Array<UserInput>> tick() {
//		frameCounter++;
//		if(frameCounter >= frameInterval) {
//			frameCounter = 0;
//			
//		}
//		return dejitteredPackets;
//	}
	
	public void addPacket(int connectionId, UserInput userInput) {
		if(packets.get(connectionId).size >= 1) {
			for(int i = 0; i < packets.get(connectionId).size; i++) {
				if(isMoreRecent(userInput.sequenceNumber, packets.get(connectionId).get(i).sequenceNumber)) {
					packets.get(connectionId).insert(i, userInput);
					return;
				}
			}
		}
		packets.get(connectionId).add(userInput);
	}
	
	public HashMap<Integer, Array<UserInput>> getDejitteredPackets() {
		//TODO: return packets 0-x
	}
	
	/**
	 * Returns true if first packet seq. number is more recent than the second */
	private boolean isMoreRecent(int s1, int s2) {
		return ((s1 > s2) && (s1 - s2 <= Integer.MAX_VALUE/2))
				|| ((s2 > s1) && (s2 - s1 > Integer.MAX_VALUE/2));
	}
	
}
