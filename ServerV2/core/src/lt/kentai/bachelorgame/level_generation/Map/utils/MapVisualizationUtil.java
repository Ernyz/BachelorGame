package lt.kentai.bachelorgame.level_generation.Map.utils;

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

    public static char[][] makeGroundCharMap(int h, int w, double[][] map) {
        char[][] realMap = new char[h][w];
        for (int x = 0; x < h; x++) {
            for (int y = 0; y < w; y++) {
                realMap[x][y] = getChar(map[x][y]);
            }
        }
        return realMap;
    }

    private static char getChar(double d){
        if(d<0.2){
            return Constants.GRASS;
        } else if(d<0.25){
            return Constants.BUSH;
        } else if(d<0.45){
            return Constants.GRASS;
        } else if(d<0.65){
            return Constants.DIRT;
        } else if(d<0.85){
            return Constants.WATER;
        } else if(d==1) {
            return Constants.MAIN_ROAD;
        }else
            return Constants.ICE;
        }

}
