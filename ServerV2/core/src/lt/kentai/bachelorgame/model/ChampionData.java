package lt.kentai.bachelorgame.model;

import lt.kentai.bachelorgame.Properties.Team;

/**
 * Used to transfer champion related data between server and client and vice versa.
 * 
 * @author Ernyz
 */
public class ChampionData {
	
	private String accountName;
	private String championName;
	/**
	 * Name of the connection to which this champion belongs to. */
	private int connectionId;
	private Team team;
	private float x;
	private float y;
	private float speed;
	
	public ChampionData() {
		
	}
	
	//XXX: Does x,y really have to be set in constructor?..
	public ChampionData(String accountName, int connectionId, Team team, float x, float y) {
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

	public int getConnectionId() {
		return connectionId;
	}

	public String getChampionName() {
		return championName;
	}

	public void setChampionName(String championName) {
		this.championName = championName;
	}
	
}
