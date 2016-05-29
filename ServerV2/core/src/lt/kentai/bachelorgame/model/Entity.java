package lt.kentai.bachelorgame.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import lt.kentai.bachelorgame.Properties.Team;

//TODO: Reorganize entity class hierarchy
public class Entity {

	public int lastProcessedPacket = 0;
	
	private String accountName;
	private String championName;
	/**
	 * Name of the connection to which this champion belongs to. */
	private int connectionId;
	private Team team;
	private float x;
	private float y;
	private float speed;
	private Vector2 velocity = new Vector2();
	
	public Entity(String accountName, int connectionId, Team team, float x, float y) {
		this.accountName = accountName;
		this.connectionId = connectionId;
		this.team = team;
		this.x = x;
		this.y = y;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getChampionName() {
		return championName;
	}

	public void setChampionName(String championName) {
		this.championName = championName;
	}

	public int getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(int connectionId) {
		this.connectionId = connectionId;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
	
}
