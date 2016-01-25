package lt.kentai.bachelorgame;

import com.badlogic.gdx.utils.Array;

import lt.kentai.bachelorgame.model.Entity;

/**
 * All game info is stored in here: map, minions, teams and etc.
 * 
 * @author Ernyz
 */
public class Match {
	
	public final int matchId;
	
	private Entity player;
	private Array<Entity> playerEntities = new Array<Entity>();
	private char[][] map;
	
	public Match(final int matchId) {
		this.matchId = matchId;
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

	public Entity getPlayer() {
		return player;
	}

	public void setPlayer(Entity player) {
		this.player = player;
	}
	
}
