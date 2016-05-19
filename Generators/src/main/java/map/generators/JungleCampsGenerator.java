package map.generators;


import map.model.Coords;
import map.utils.MapUtils;

import java.util.List;

public class JungleCampsGenerator {

    private int nrOfRedTeamCamps = 2;
    private int nrOfBlueTeamCamps = 2;

    private int mapWidth, mapHight;


    char[][] groundMap;
    int[][] wayMap;

    public JungleCampsGenerator(char[][] groundMap, int[][] wayMap) {
        this.groundMap = groundMap;
        this.wayMap = wayMap;
        mapHight = groundMap.length;
        mapWidth = groundMap[0].length;
        System.out.println("width : " + mapWidth);
    }

    public char[][] addJungleCamps() {

        Coords bossCoord = getBoosCoord(wayMap);

        groundMap = MapUtils.printBossIntoMap(groundMap, bossCoord);

        List<Coords> campsCoords = getCampsCoords(wayMap);


        return groundMap;

    }

    private List<Coords> getCampsCoords(int[][] wayMap) {
        System.out.println("wayMap size : " + wayMap[0].length);

        return null;
    }

    private Coords findSpotForCamp(int x, int y) {


        return new Coords(x, y);

    }


    private Coords getBoosCoord(int[][] wayMap) {
        int wayY = 0;
        for (int y = 0; y < wayMap.length; y++) {
            if (wayMap[y][wayMap[0].length / 2] == 1) {
                wayY = y;
                break;
            }
        }
        int q = wayMap.length / 6;
        int bossY = wayY + 3 * q;
        bossY = bossY > wayMap.length ? bossY - wayMap.length : bossY;

        bossY = bossY < 10 ? bossY + 10 : bossY;
        bossY = bossY > wayMap.length - 10 ? bossY - 10 : bossY;

        return new Coords(wayMap[0].length / 2, bossY);
    }


}
