package lt.kentai.bachelorgame.model;

import lt.kentai.bachelorgame.Properties.Team;

public class ChampionData {
	
	private String accountName;
	private Team team;
	private float x;
	private float y;
	
	//TODO: See if there is a way to make only one constructor
	public ChampionData() {
		
	}
	
	public ChampionData(String accountName, Team team, float x, float y) {
		this.accountName = accountName;
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
	
}
