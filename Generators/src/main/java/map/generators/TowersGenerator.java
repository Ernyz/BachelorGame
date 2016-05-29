package map.generators;

import map.model.Team;
import map.model.Tower;

import java.util.ArrayList;
import java.util.List;

public class TowersGenerator {

    int[] wayMap;
    int xWayLenght;
    int mapWidth;

    public TowersGenerator(int[] wayMap, int mapWidth) {
        this.wayMap = wayMap;
        xWayLenght = wayMap.length;
        this.mapWidth = mapWidth;
    }

    public List<Tower> getTowers() {
        List<Tower> towers = new ArrayList<>();
        double[][] array = new double[xWayLenght - 1][3];
        int dx = 0;
        int dy = wayMap[0];
        double totalWayDist = 0;
        for (int i = 1; i < xWayLenght; i++) {
            int x = i * mapWidth / xWayLenght;
            int y = wayMap[i];
            double pt = Math.pow(x - dx, 2) + Math.pow(y - dy, 2);
            dx = x;
            dy = y;
            double z = Math.sqrt(pt);
            totalWayDist += z;
            array[i - 1][0] = x;
            array[i - 1][1] = y;
            array[i - 1][2] = totalWayDist;
        }

        System.out.println(totalWayDist / 5);
        for (int i = 1; i < 5; i++) {
            for (int a = 0; a < xWayLenght - 1; a++) {
                if (closeEnough(array[a][2], i * totalWayDist / 5)) {
                    towers.add(new Tower((int) array[a][0], (int) array[a][1], Team.BLUE));
                    break;
                }
            }
        }


        System.out.println(array);

        return towers;


    }

    private boolean closeEnough(double a, double b) {
        return Math.abs(a - b) < 10;
    }

    private List<Tower> getBlueTeamTowers() {
        List<Tower> blueTeamTowers = new ArrayList<>();
        double totalWayDist = 0;

        for (int i = 0; i < xWayLenght - 1; i++) {
            totalWayDist += Math.sqrt(Math.pow(mapWidth / xWayLenght, 2) + Math.pow((wayMap[i + 1] - wayMap[i]), 2));
        }

        double disntanceBtwTowes = totalWayDist / 7;


        double totalWayMom = 0;
        int towerNr = 1;

        for (int i = 0; i < xWayLenght - 1; i++) {
            totalWayMom += Math.sqrt(Math.pow(mapWidth / xWayLenght, 2) + Math.pow((wayMap[i + 1] - wayMap[i]), 2));

            if (towerNr * disntanceBtwTowes < totalWayMom) {
                if (towerNr < 3) {
                    blueTeamTowers.add(new Tower((int) disntanceBtwTowes * towerNr, 0, Team.BLUE));
                } else {
                    blueTeamTowers.add(new Tower((int) disntanceBtwTowes * towerNr, 0, Team.RED));
                }
                if (towerNr == 4) break;
                towerNr++;
            }
        }
        System.out.println("total way lenght : " + totalWayDist);
        System.out.println(blueTeamTowers);
        return blueTeamTowers;
    }
}