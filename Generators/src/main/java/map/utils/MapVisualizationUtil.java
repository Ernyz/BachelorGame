package map.utils;

import map.dto.MapAndArray;
import map.model.Vector;

import java.util.ArrayList;
import java.util.List;

public class MapVisualizationUtil {

    public static MapAndArray getGroundMapAndCampsSpots(double[][] map) {
        char[][] charMap = new char[map.length][map[0].length];
        List<Vector> vectorList = new ArrayList<>();
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                char c = getChar(map[x][y]);
                charMap[x][y] = c;
                if (c == Constants.POT_CAMPS) {
                    vectorList.add(new Vector(y, x));
                }
            }
        }
        return new MapAndArray(vectorList, charMap);
    }

    private static char getChar(double d) {
        if (d < 0.2) {
            return Constants.WALL;
        } else if (d < 0.205) {
            return Constants.POT_CAMPS;
        } else if (d < 0.25) {
            return Constants.DIRT;
        } else if (d < 0.45) {
            return Constants.WALL;
        } else if (d < 0.454) {
            return Constants.POT_CAMPS;
        } else if (d < 0.65) {
            return Constants.DIRT;
        } else if (d < 0.85) {
            return Constants.WALL;
        } else
            return Constants.DIRT;
    }
}
