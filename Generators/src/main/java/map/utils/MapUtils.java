package map.utils;
import map.components.MapComponents;
import map.dto.CampSpwnPlaces;
import map.model.Vector;

import java.util.List;

public class MapUtils {

    public static double[][] mergeMaps(int w, int h, double[][]... maps) {
        double[][] resultMap = maps[0];
        for (double[][] map : maps) {
            resultMap = mergeTwoMaps(w, h, resultMap, map);
        }
        return resultMap;
    }

    private static double[][] mergeTwoMaps(int w, int h, double[][] map1, double[][] map2) {
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                map1[i][j] = (map1[i][j] + map2[i][j]) / 2;
            }
        }
        return map1;
    }

    public static char[][] paintRoad(int w, int h, char[][] childMap, int[][] roadMap) {
        Log.logMapSize("paint road", w, h);
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                try {
                    if (roadMap[j][i] == 1) {
                        childMap[j][i] = Constants.ROAD;
                    }
                } catch (Exception e) {
                    System.out.println("fail at " + i + "\t" + j);
                }
            }
        }
        return childMap;
    }

    public static char[][] addBases(char[][] map) {
        char[][] base2 = MapUtils.reverseMap(MapComponents.base);
        MapPrinter.printMapToConsole(base2);
        addBase(map, MapComponents.base, map.length / 2 - MapComponents.base.length / 2, 0);
        addBase(map, base2, ((map.length) / 2 - (base2.length) / 2), map[0].length - MapComponents.base[0].length);
        return map;
    }

    private static void addBase(char[][] map, char[][] base, int x, int y) {
        for (int i = 0; i < base.length; i++) {
            for (int j = 0; j < base[0].length; j++) {
//                System.out.println("x+i :" + (x + i) + "j+y : " + (j + y));
                map[x + i][j + y] = base[i][j];
            }
        }
    }

    public static char[][] reverseMap(char[][] map) {
        char[][] reverse = new char[map.length][];

        for (int i = 0; i < map.length; i++) {
            reverse[i] = map[i].clone();
        }
        for (int j = 0; j < reverse.length; j++) {
            for (int i = 0; i < reverse[j].length / 2; i++) {
                char temp = reverse[j][i];
                if(temp == MapComponents.b)
                    temp = MapComponents.r;
                reverse[j][i] = reverse[j][reverse[j].length - i - 1];
                reverse[j][reverse[j].length - i - 1] = temp;
            }
        }
        return reverse;
    }

    public static CampSpwnPlaces getCampSpwnPlaces(char[][] map,int nrOfMembers){
        CampSpwnPlaces campSpwnPlaces = new CampSpwnPlaces(nrOfMembers);
//        for (int i= 0; i<map.length;i++){
//            for (int j = 0; j < map[1].length; j++) {
//                if (campSpwnPlaces.full())
//                    return campSpwnPlaces;
//                if(map[i][j]==MapComponents.b){
//                    campSpwnPlaces.addBlueBaseVector(new Vector(j,i));
//                    map[i][j]= MapComponents.f;
//                }else if (map[i][j]==MapComponents.r){
//                    System.out.println(j+":"+i);
//                    campSpwnPlaces.addRedBaseVector(new Vector(j,i));
//                    map[i][j]= MapComponents.f;
//                }
//            }
//        }
        return campSpwnPlaces;
    }

    public static boolean inRange(int x1, int y1, int x2, int y2, double distance) {
        if (Math.sqrt(Math.abs((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1))) < distance) {
            return true;
        } else
            return false;
    }

    public static boolean inRange(Vector v1, Vector v2, double distance) {
        return inRange(v1.x, v1.y, v2.x, v2.y, distance);
    }

    public static void surroundWithWalls(char[][] map) {
        for (int i = 0; i < map[0].length; i++) {
            map[0][i] = Constants.WALL;
            map[map.length - 1][i] = Constants.WALL;
        }
        for (int i = 0; i < map.length; i++) {
            map[i][0] = Constants.WALL;
            map[i][map[0].length - 1] = Constants.WALL;
        }
    }

    public static char[][] printBossIntoMap(char[][] map, Vector bossCoord) {
        for (int y = bossCoord.y - 5; y < bossCoord.y + 5; y++) {
            for (int x = bossCoord.x - 5; x < bossCoord.x + 5; x++) {
                map[y][x] = Constants.BOSS;
            }
        }
        return map;
    }

    public static char[][] printCampsIntoMap(char[][] groundMap, List<Vector> campsCoords) {

        for (Vector campVector : campsCoords) {
            for (int y = campVector.y - 5; y < campVector.y + 5; y++) {
                for (int x = campVector.x - 5; x < campVector.x + 5; x++) {
                    if (MapUtils.inRange(x, y, campVector.x, campVector.y, 5))
                        groundMap[y][x] = Constants.JUNGLE_CAMP;
                }
            }
        }
        return groundMap;


    }
}
