package lt.kentai.bachelorgame.controller;

import com.badlogic.gdx.utils.Array;

import lt.kentai.bachelorgame.Properties;
import lt.kentai.bachelorgame.model.Entity;

public class EntityUpdater {

	private Array<Entity> players;
	
	public EntityUpdater(Array<Entity> players) {
		this.players = players;
	}
	
	public void update() {
		//Move entities
		for(int i = 0; i < players.size; i++) {
			players.get(i).setX(players.get(i).getX() + Properties.FRAME_TIME * players.get(i).getSpeed() * players.get(i).getVelocity().x);
			players.get(i).setY(players.get(i).getY() + Properties.FRAME_TIME * players.get(i).getSpeed() * players.get(i).getVelocity().y);
		}
	}
	
}
