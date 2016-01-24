package lt.kentai.bachelorgame;

import com.badlogic.gdx.utils.Array;

import lt.kentai.bachelorgame.model.Entity;

/**
 * All game info is stored in here: map, minions, teams and etc.
 * 
 * @author Ernyz
 */
public class Match {
	
	private Array<Entity> playerEntities = new Array<Entity>();
	private char[][] map;
	
	public Match() {
		
	}

	public Array<Entity> getPlayerEntities() {
		return playerEntities;
	}

	public char[][] getMap() {
		return map;
	}

	public void setMap(char[][] map) {
		this.map = map;
	}
}
