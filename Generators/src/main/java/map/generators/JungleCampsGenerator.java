package map.generators;


import map.model.Coords;
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
    int seed;

    public JungleCampsGenerator(int seed,char[][] groundMap, int[][] wayMap) {
        this.groundMap = groundMap;
        this.wayMap = wayMap;
        this.seed = seed;
        mapHight = groundMap.length;
        mapWidth = groundMap[0].length;
        System.out.println("width : " + mapWidth);
    }

    public char[][] addJungleCamps() {

        Coords bossCoord = getBoosCoord(wayMap);

        groundMap = MapUtils.printBossIntoMap(groundMap, bossCoord);

        List<Coords> campsCoords = getCampsCoords(wayMap);

        groundMap = MapUtils.printCampsIntoMap(groundMap, campsCoords);

        return groundMap;

    }

    private List<Coords> getCampsCoords(int[][] wayMap) {
        System.out.println("wayMap size : " + wayMap[0].length);
        List<Coords> coordses = new ArrayList<>();
        coordses.addAll(getCamps(seed,mapWidth/4));
        coordses.addAll(getCamps(seed,mapWidth/4*3));
        return coordses;
    }


    private List<Coords> getCamps(int seed, int x){
        Random r = new Random(seed);
        int wayY = 0;
        for (int y = 0; y < wayMap.length; y++) {
            if (wayMap[y][wayMap[0].length / 2] == 1) {
                wayY = y;
                break;
            }
        }
        int camp1Y = getCampY(r,wayY, 2+r.nextInt(2)-1);
        int camp2Y = getCampY(r, wayY, 6+r.nextInt(2)-1);

        List<Coords> coordsList = new ArrayList<>();
        Coords coords1 = new Coords(x+r.nextInt(30)-15, camp1Y+r.nextInt(20)-10);
        coords1 = lastCheck(coords1);
        Coords coords2 = new Coords(x+r.nextInt(30)-15, camp2Y+r.nextInt(20)-10);
        coords2 = lastCheck(coords2);
        coordsList.add(coords1);
        coordsList.add(coords2);
        return coordsList;
    }

    private Coords lastCheck(Coords coords1) {
        int distanceToRoad = getWayYAtThisXPoint(coords1.x)-coords1.y;
        if(distanceToRoad>-10||distanceToRoad<10){
            coords1.y = distanceToRoad>0? coords1.y +10:coords1.y -10;
        }
        return coords1;
    }

    private int getCampY(Random r, int wayY, int coef){

        int q = wayMap.length / 6;

        int res = wayY + coef * q;

        res = res > wayMap.length ? res - wayMap.length : res;

        res = res +r.nextInt(20)-10;

        res = res < 10 ? res + 10 : res;
        res = res > wayMap.length - 10 ? res - 10 : res;


        return  res;

    }


    private int getWayYAtThisXPoint(int x){
        for (int y = 0; y < wayMap.length; y++) {
            if (wayMap[y][x] == 1) {
                return  y;
            }
        }
        System.err.print("DEFAULT WAY Y RETURNED");
        return wayMap.length/2;
    }

    private Coords getBoosCoord(int[][] wayMap) {
        int wayY = getWayYAtThisXPoint(mapWidth/2);
        int q = wayMap.length / 6;
        int bossY = wayY + 3 * q;
        bossY = bossY > wayMap.length ? bossY - wayMap.length : bossY;

        bossY = bossY < 10 ? bossY + 10 : bossY;
        bossY = bossY > wayMap.length - 10 ? bossY - 10 : bossY;

        return new Coords(wayMap[0].length / 2, bossY);
    }


}
