package map.dto;

import map.model.Vector;

import java.util.ArrayList;
import java.util.List;


public class CampSpwnPlaces {
    public List<Vector> redBaseVectors;
    public List<Vector> blueBaseVectors;

    public CampSpwnPlaces(int nrOfPlayersInTeam) {
        redBaseVectors = new ArrayList<>();
        redBaseVectors.add(new Vector(298,98));
        redBaseVectors.add(new Vector(298,100));
        redBaseVectors.add(new Vector(298,102));
        blueBaseVectors = new ArrayList<>();
        blueBaseVectors.add(new Vector(2,98));
        blueBaseVectors.add(new Vector(2,100));
        blueBaseVectors.add(new Vector(2,102));
    }

//    public void addRedBaseVector(Vector v) {
//        if (nrOfPlayersInteam == redBaseVectors.size())
//            return;
//        System.out.println("REDd");
//        System.out.println(v.y+":"+v.x);
//
//        redBaseVectors.add(v);
//    }
//
//    public void addBlueBaseVector(Vector v) {
//        if (nrOfPlayersInteam == blueBaseVectors.size())
//            return;
//        System.out.println("Blue");
//
//        System.out.println(v.y+":"+v.x);
//
//        blueBaseVectors.add(v);
//    }
//
//    public boolean full() {
//        if (nrOfPlayersInteam == blueBaseVectors.size() && nrOfPlayersInteam == redBaseVectors.size()) {
//            return true;
//        }
//
//        return false;
//    }
}
