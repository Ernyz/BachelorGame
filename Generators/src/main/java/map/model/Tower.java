package map.model;

public class Tower extends Vector {
    private Team team;

    public Tower(int x, int y, Team team) {
        super(x, y);
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
