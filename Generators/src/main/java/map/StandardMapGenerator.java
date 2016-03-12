package map;


import map.generators.GroundGenerator;
import map.generators.WayGenerator;
import map.utils.MapUtils;

public class StandardMapGenerator {

    final int MAP_WIDTH;
    final int MAP_HEIGHT;
    final int SEED;
    final double PERSISTANCE;
    final int LARGEST_FUTURE;

    public StandardMapGenerator(int MAP_WIDTH, int MAP_HEIGHT, int SEED, double PERSISTANCE, int LARGEST_FUTURE) {
        this.MAP_WIDTH = MAP_WIDTH;
        this.MAP_HEIGHT = MAP_HEIGHT;
        this.SEED = SEED;
        this.PERSISTANCE = PERSISTANCE;
        this.LARGEST_FUTURE = LARGEST_FUTURE;
    }

    public char[][] generateMap() {
        char[][] finalMap = new GroundGenerator(MAP_WIDTH, MAP_HEIGHT, LARGEST_FUTURE, PERSISTANCE, SEED).getGroundMap();
        finalMap = MapUtils.paintRoad(MAP_WIDTH, MAP_HEIGHT, finalMap, new WayGenerator(MAP_WIDTH, MAP_HEIGHT, SEED).getWayMap(3, 0.9, 0.3));
        MapUtils.addBases(finalMap);
        MapUtils.surroundWithWalls(finalMap);
//        try {
////            Desktop.getDesktop().open(new File("C:\\Users\\Laurynas\\Documents\\TestingPorp\\" + MapPrinter.printMapToFile("generated map-", finalMap, true)));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return finalMap;
    }
}