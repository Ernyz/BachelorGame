package map.dto;

import map.model.Vector;

import java.util.ArrayList;
import java.util.List;


public class CampSpwnPlaces {
    public List<Vector> redBaseVectors;
    public List<Vector> blueBaseVectors;
    private int nrOfPlayersInteam;

    public CampSpwnPlaces(int nrOfPlayersInTeam) {
        nrOfPlayersInteam = nrOfPlayersInTeam;
        redBaseVectors = new ArrayList<>();
        blueBaseVectors = new ArrayList<>();
    }

    public void addRedBaseVector(Vector v) {
        if (nrOfPlayersInteam == redBaseVectors.size())
            return;
        redBaseVectors.add(v);
    }

    public void addBlueBaseVector(Vector v) {
        if (nrOfPlayersInteam == blueBaseVectors.size())
            return;
        blueBaseVectors.add(v);
    }

    public boolean full() {
        if (nrOfPlayersInteam == blueBaseVectors.size() && nrOfPlayersInteam == redBaseVectors.size()) {
            return true;
        }
        return false;
    }
}
