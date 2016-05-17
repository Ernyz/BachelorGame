package map.utils;

public class MapVisualizationUtil {
    public static char[][] doCharMap(int h, int w, int[][] map, double coef) {
        char[][] realMap = new char[h][w];
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                realMap[y][x] = map[y][x] > coef ? '#' : '.';
            }
        }
        return realMap;
    }

    public static char[][] doCharMap(int h, int w, double[][] map, double coef) {
        char[][] realMap = new char[w][h];
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                realMap[x][y] = map[x][y] > coef ? '#' : '.';
            }
        }
        return realMap;
    }

    public static char[][] makeGroundCharMap(double[][] map) {
        char[][] charMap = new char[map.length][map[0].length];
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                charMap[x][y] = getChar(map[x][y]);
            }
        }
        return charMap;
    }

    private static char getChar(double d) {
        if (d < 0.2) {
            return Constants.WALL;
        } else if (d < 0.25) {
            return Constants.DIRT;
        } else if (d < 0.45) {
            return Constants.WALL;
        } else if (d < 0.65) {
            return Constants.DIRT;
        } else if (d < 0.85) {
            return Constants.WALL;
        } else
            return Constants.DIRT;
    }
}
