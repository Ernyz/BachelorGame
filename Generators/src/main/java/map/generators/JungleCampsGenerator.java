package map.generators;


import map.model.Coords;

import java.util.List;

public class JungleCampsGenerator {

    private int nrOfRedTeamCamps = 2;
    private int nrOfBlueTeamCamps = 2;


    public char[][] addJungleCamps(char[][] groundMap, int[][] wayMap) {

        Coords bossCoord = getBoosCoord(wayMap);
        List<Coords> campsCoords = getCampsCoords(wayMap);


        return groundMap;

    }

    private List<Coords> getCampsCoords(int[][] wayMap) {
        System.out.println("wayMap size : " + wayMap[0].length);

        return null;
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
        return new Coords(wayMap[0].length / 2, bossY);
    }



}
