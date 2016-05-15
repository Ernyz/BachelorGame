package lt.kentai.bachelorgame.controller;

import com.badlogic.gdx.Input.Keys;

import lt.kentai.bachelorgame.Match;
import lt.kentai.bachelorgame.model.Entity;
import lt.kentai.bachelorgame.networking.Network.UserInput;

public class PlayerInputManager {

	public static void applyInput(int connectionId, UserInput userInput, Match match) {
		boolean[] input = userInput.input;
		Entity player = null;
		
//		System.out.println(match);
//		System.out.println(match.getChampions());
		for(int i = 0; i < match.getChampions().size; i++) {
			if(connectionId == match.getChampions().get(i).getConnectionId()) {
				player = match.getChampions().get(i);
			}
		}
//		System.out.println("Player: " + player);
//		System.out.println(connectionId + " " + userInput.input[Keys.W] + " " + match.getMatchId());
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
