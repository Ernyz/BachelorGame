package lt.kentai.bachelorgame.controller;

import com.badlogic.gdx.Input.Keys;

import lt.kentai.bachelorgame.Match;
import lt.kentai.bachelorgame.model.Entity;
import lt.kentai.bachelorgame.networking.Network.UserInput;

public class PlayerInputManager {

	//FIXME: Replace match with a champion onto which input is applied
	public static void applyInput(int connectionId, UserInput userInput, Match match) {
		boolean[] input = userInput.input;
		Entity player = null;
		
		for(int i = 0; i < match.getChampions().size; i++) {
			if(connectionId == match.getChampions().get(i).getConnectionId()) {
				player = match.getChampions().get(i);
				player.lastProcessedPacket = userInput.sequenceNumber;
			}
		}
		
		//Apply movement
		if(input[Keys.W]) {
			player.getVelocity().y = 1;
		} else if(input[Keys.S]) {
			player.getVelocity().y = -1;
		} else {
			player.getVelocity().y = 0;
		}
		if(input[Keys.A]) {
			player.getVelocity().x = -1;
		} else if(input[Keys.D]) {
			player.getVelocity().x = 1;
		} else {
			player.getVelocity().x = 0;
		}
	}
	
}
