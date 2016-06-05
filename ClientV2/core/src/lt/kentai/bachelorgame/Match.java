package lt.kentai.bachelorgame;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.utils.Array;

import lt.kentai.bachelorgame.Network.UserInput;
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
	
	public void update() {
		matchTimer += Properties.FRAME_TIME;
		
		for(Entity e : playerEntities) {
			if(e.connectionId != player.connectionId) {
				e.setX(e.getX() + e.dx);
				e.setY(e.getY() + e.dy);
			} else {
				e.setX(e.getX() + e.getSpeed() * e.getVelocity().x * Properties.FRAME_TIME);
				e.setY(e.getY() + e.getSpeed() * e.getVelocity().y * Properties.FRAME_TIME);
			}
		}
	}
	
	public void updatePlayer(UserInput sentPacket) {
		int velocityX = 0;
		int velocityY = 0;
		if(sentPacket.input[Keys.A]) {
			velocityX = -1;
		} else if(sentPacket.input[Keys.D]) {
			velocityX = 1;
		}
		if(sentPacket.input[Keys.W]) {
			velocityY = -1;
		} else if(sentPacket.input[Keys.S]) {
			velocityY = 1;
		}
		player.setX(player.getX() + player.getSpeed() * velocityX * Properties.FRAME_TIME);
		player.setY(player.getY() + player.getSpeed() * velocityY * Properties.FRAME_TIME);
	}
	
	public void executeInput(UserInput userInput) {
		if(userInput.input[Input.Keys.W]) {
			player.getVelocity().y = 1;
		} else if(userInput.input[Input.Keys.S]) {
			player.getVelocity().y = -1;
		} else {
			player.getVelocity().y = 0;
		}
		if(userInput.input[Input.Keys.A]) {
			player.getVelocity().x = -1;
		} else if(userInput.input[Input.Keys.D]) {
			player.getVelocity().x = 1;
		} else {
			player.getVelocity().x = 0;
		}
	}

	public float getMatchTimer() {
		return matchTimer;
	}
	
}
