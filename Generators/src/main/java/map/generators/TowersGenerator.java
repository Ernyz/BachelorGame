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
        return getBlueTeamTowers();
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
                    blueTeamTowers.add(new Tower((int)disntanceBtwTowes*towerNr, 0, Team.BLUE));
                } else {
                    blueTeamTowers.add(new Tower((int)disntanceBtwTowes*towerNr, 0, Team.RED));
                }
                if(towerNr == 4) break;
                towerNr++;

            }

        }

        System.out.println("total way lenght : " + totalWayDist);
        System.out.println(blueTeamTowers);
        return blueTeamTowers;
    }


}
