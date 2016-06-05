package map;


import map.dto.MapAndArray;
import map.generators.GroundGenerator;
import map.generators.JungleCampsGenerator;
import map.generators.TowersGenerator;
import map.generators.WayGenerator;
import map.model.Vector;
import map.utils.MapPrinter;
import map.utils.MapUtils;

import java.util.List;

public class StandardMapGenerator {

    final int MAP_WIDTH;
    final int MAP_HEIGHT;
    final int SEED;
    final double PERSISTANCE;
    final int LARGEST_FUTURE;

    public StandardMapGenerator(int MAP_WIDTH, int MAP_HEIGHT, int SEED, double PERSISTANCE, int LARGEST_FEATURE) {
        this.MAP_WIDTH = MAP_WIDTH;
        this.MAP_HEIGHT = MAP_HEIGHT;
        this.SEED = SEED;
        this.PERSISTANCE = PERSISTANCE;
        this.LARGEST_FUTURE = LARGEST_FEATURE;
    }

    public char[][] generateMap() {
        MapAndArray mapAndArray = new GroundGenerator(MAP_WIDTH, MAP_HEIGHT, LARGEST_FUTURE, PERSISTANCE, SEED).getMapAndArray();

        char[][] finalMap = mapAndArray.getChar2dmap();
        List<Vector> campPotentialSpots = mapAndArray.getCoords();

        WayGenerator wayGenerator = new WayGenerator(MAP_WIDTH, MAP_HEIGHT, SEED);

        int[] wayMap = wayGenerator.getWayMap(3, 0.9, 0.3);
        int[][] way2dMap = wayGenerator.get2dMapFromPoints(wayMap);

        TowersGenerator towersGenerator = new TowersGenerator(wayMap, MAP_WIDTH);
        towersGenerator.getTowers();
        //paint towers
        finalMap = MapUtils.paintRoad(MAP_WIDTH, MAP_HEIGHT, finalMap, way2dMap);

        JungleCampsGenerator jungleCampsGenerator = new JungleCampsGenerator(SEED,finalMap, way2dMap, campPotentialSpots);
        finalMap = jungleCampsGenerator.addJungleCamps();

        MapUtils.addBases(finalMap);


        MapUtils.surroundWithWalls(finalMap);

        return finalMap;
    }
}