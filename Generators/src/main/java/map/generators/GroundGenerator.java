package map.generators;


import map.dto.MapAndArray;
import map.utils.MapVisualizationUtil;

public class GroundGenerator {

    private MapAndArray mapAndArray;

    public GroundGenerator(int w, int h, int largestFuture, double persistance, int seed) {
        SimplexArrayGenerator simplexArrayGenerator = new SimplexArrayGenerator(h, w, largestFuture, seed, persistance);
        double[][] map = simplexArrayGenerator.getSimplexMap();
        mapAndArray = MapVisualizationUtil.getGroundMapAndCampsSpots(map);
    }

    public MapAndArray getMapAndArray() {
        return mapAndArray;
    }
}
