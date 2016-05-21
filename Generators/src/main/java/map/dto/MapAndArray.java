package map.dto;

import map.model.Vector;

import java.util.List;

public class MapAndArray {

    private char[][] char2dmap;
    private List<Vector> coords;

    public MapAndArray(List<Vector> coords, char[][] char2dmap) {
        this.coords = coords;
        this.char2dmap = char2dmap;
    }

    public char[][] getChar2dmap() {
        return char2dmap;
    }

    public void setChar2dmap(char[][] char2dmap) {
        this.char2dmap = char2dmap;
    }

    public List<Vector> getCoords() {
        return coords;
    }

    public void setCoords(List<Vector> coords) {
        this.coords = coords;
    }
}
