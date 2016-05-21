package map.generators;


import map.model.Vector;
import map.utils.MapUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JungleCampsGenerator {

    private int nrOfRedTeamCamps = 2;
    private int nrOfBlueTeamCamps = 2;

    private int mapWidth, mapHight;


    char[][] groundMap;
    int[][] wayMap;
    List<Vector> campPotentialSpots;
    int seed;
    Vector bossCoords;

    public JungleCampsGenerator(int seed, char[][] groundMap, int[][] wayMap, List<Vector> campPotentialSpots) {
        this.groundMap = groundMap;
        this.wayMap = wayMap;
        this.seed = seed;
        this.campPotentialSpots = campPotentialSpots;
        mapHight = groundMap.length;
        mapWidth = groundMap[0].length;
    }

    public char[][] addJungleCamps() {
        bossCoords = getBoosCoord(wayMap);
        groundMap = MapUtils.printBossIntoMap(groundMap, bossCoords);

        List<Vector> jungleCampCoords = getCampsCoords2();
        groundMap = MapUtils.printCampsIntoMap(groundMap, jungleCampCoords);
        return groundMap;
    }

    private List<Vector> getCampsCoords2() {
        List<Vector> coordses = new ArrayList<>();

        for (Vector v : campPotentialSpots) {
            if (checkIfAwayFromMapBoarders(v, 20)) {
                if (checkIfAwayFromOtherCamps(v, coordses, 50)) {
                    if (checkIfAwayFromWay(v, 30)) {
                        if (checkIfAwayFromBossCamp(v, 40))
                            coordses.add(v);
                    }
                }
            }
        }
        return coordses;
    }

    private boolean checkIfAwayFromBossCamp(Vector v, int range) {
        if (MapUtils.inRange(v.x, v.y, bossCoords.x, bossCoords.y, range)) {
            return false;
        }
        return true;
    }


    private boolean checkIfAwayFromMapBoarders(Vector v, int range) {
        if (v.x < range || v.x > mapWidth - range || v.y < range || v.y > mapHight - range) {
            return false;
        } else return farAwayFromMainCamps(v, 50);
    }

    private boolean farAwayFromMainCamps(Vector v, int range) {
        if (MapUtils.inRange(v.x, v.y, 0, mapHight / 2, range) || MapUtils.inRange(v.x, v.y, mapWidth - 1, mapHight / 2, range))
            return false;
        else return true;
    }

    /**
     * check if far away from other camps
     *
     * @param v
     * @param coordses
     * @param range
     * @return
     */
    private boolean checkIfAwayFromOtherCamps(Vector v, List<Vector> coordses, int range) {
        for (Vector campV : coordses) {
            if (MapUtils.inRange(v.x, v.y, campV.x, campV.y, range)) {
                return false;
            }
        }
        return true;
    }

    /**
     * check if far away from way
     *
     * @param vector
     * @return
     */
    private boolean checkIfAwayFromWay(Vector vector, int range) {
        int xFrom = vector.x - 20 < 0 ? 0 : vector.x - 20;
        int xTo = vector.x + 20 > mapWidth ? mapWidth : vector.x + 20;

        for (int x = xFrom; x < xTo; x = x + 5) {
            if (MapUtils.inRange(vector.x, vector.y, x, getWayYAtThisXPoint(x), range)) {
                return false;
            }
        }
        return true;
    }

    private List<Vector> getCampsCoords() {
        List<Vector> coordses = new ArrayList<>();
        coordses.addAll(getCamps(seed, mapWidth / 4));
        coordses.addAll(getCamps(seed, mapWidth / 4 * 3));
        return coordses;
    }

    private List<Vector> getCamps(int seed, int x) {
        Random r = new Random(seed);
        int wayY = 0;
        for (int y = 0; y < wayMap.length; y++) {
            if (wayMap[y][wayMap[0].length / 2] == 1) {
                wayY = y;
                break;
            }
        }
        int camp1Y = getCampY(r, wayY, 2 + r.nextInt(2) - 1);
        int camp2Y = getCampY(r, wayY, 4 + r.nextInt(2) - 1);

        List<Vector> vectorList = new ArrayList<>();
        Vector vector1 = new Vector(x + r.nextInt(30) - 15, camp1Y + r.nextInt(20) - 10);
        vector1 = lastCheck(vector1);
        Vector vector2 = new Vector(x + r.nextInt(30) - 15, camp2Y + r.nextInt(20) - 10);
        vector2 = lastCheck(vector2);
        vectorList.add(vector1);
        vectorList.add(vector2);
        return vectorList;
    }

    private Vector lastCheck(Vector vector1) {
        int distanceToRoad = getWayYAtThisXPoint(vector1.x) - vector1.y;
        if (distanceToRoad > -10 || distanceToRoad < 10) {
            vector1.y = distanceToRoad > 0 ? vector1.y + 10 : vector1.y - 10;
        }
        return vector1;
    }

    private int getCampY(Random r, int wayY, int coef) {

        int q = wayMap.length / 6;

        int res = wayY + coef * q;

        res = res > wayMap.length ? res - wayMap.length : res;

        res = res + r.nextInt(20) - 10;

        res = res < 10 ? res + 10 : res;
        res = res > wayMap.length - 10 ? res - 10 : res;


        return res;

    }


    private int getWayYAtThisXPoint(int x) {
        for (int y = 0; y < wayMap.length; y++) {
            try {
                if (wayMap[y][x] == 1) {
                    return y;
                }
            }catch (Exception e){
                System.out.println(e);
            }
        }
        System.err.print("DEFAULT WAY Y RETURNED");
        return wayMap.length / 2;
    }

    private Vector getBoosCoord(int[][] wayMap) {
        int wayY = getWayYAtThisXPoint(mapWidth / 2);
        int q = wayMap.length / 6;
        int bossY = wayY + 3 * q;
        bossY = bossY > wayMap.length ? bossY - wayMap.length : bossY;

        bossY = bossY < 10 ? bossY + 10 : bossY;
        bossY = bossY > wayMap.length - 10 ? bossY - 10 : bossY;

        return new Vector(wayMap[0].length / 2, bossY);
    }


}
