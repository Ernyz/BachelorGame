package lt.kentai.bachelorgame.controller;

import com.badlogic.gdx.utils.Array;

import lt.kentai.bachelorgame.model.Entity;

public class EntityUpdater {

	private Array<Entity> players;
	
	public EntityUpdater(Array<Entity> players) {
		this.players = players;
	}
	
	public void update(float delta) {
		//Move entities
		for(int i = 0; i < players.size; i++) {
			players.get(i).setX(players.get(i).getX() + delta * players.get(i).getSpeed() * players.get(i).getVelocity().x);
			players.get(i).setY(players.get(i).getY() + delta * players.get(i).getSpeed() * players.get(i).getVelocity().y);
		}
	}
	
}
