package map.generators;


import map.utils.MapVisualizationUtil;

public class GroundGenerator {

    public char[][] groundMap;

    public GroundGenerator(int w, int h, int largestFuture, double persistance, int seed) {
        SimplexArrayGenerator simplexArrayGenerator = new SimplexArrayGenerator(h, w, largestFuture, seed, persistance);
        double[][] map = simplexArrayGenerator.getSimplexMap();
        groundMap = MapVisualizationUtil.makeGroundCharMap(map);
    }

    public char[][] getGroundMap() {
        return groundMap;
    }
}
