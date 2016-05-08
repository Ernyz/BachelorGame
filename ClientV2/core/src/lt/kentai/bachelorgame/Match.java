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
	
	private float matchTimer = 0f;  //XXX: Rethink this
	
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
	
	//XXX: Testing. Rename to "executeInput"?
	public void update(float delta) {
		matchTimer += delta;
		
		for(Entity e : playerEntities) {
			e.setX(e.getX() + e.getSpeed() * e.getVelocity().x * delta);
			e.setY(e.getY() + e.getSpeed() * e.getVelocity().y * delta);
		}
	}

	public float getMatchTimer() {
		return matchTimer;
	}
	
}
