package lt.kentai.bachelorgame.generators.Map.generators;


import lt.kentai.bachelorgame.generators.Map.utils.MapVisualizationUtil;

public class GroundGenerator {

    public char[][] groundMap;
    SimplexArrayGenerator simplexArrayGenerator;

    public GroundGenerator(int w, int h, int largestFuture, double persistance, int seed) {
        simplexArrayGenerator = new SimplexArrayGenerator(h, w, largestFuture, seed, persistance);
        double[][] map = simplexArrayGenerator.getSimplexMap2();
        groundMap = MapVisualizationUtil.makeGroundCharMap(h, w, map);
    }

    public char[][] getGroundMap() {
        return groundMap;
    }
}
