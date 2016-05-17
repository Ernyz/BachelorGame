package map.generators;


import map.simplex.SimplexNoise;

public class SimplexArrayGenerator {

    private double xStart = 0;
    private double XEnd = 1000;
    private double yStart = 0;
    private double yEnd = 1000;

    private int h, w, largestFeature, seed;
    double persistence;

    /**
     * @param h              height
     * @param w              width
     * @param largestFeature Largest future
     * @param persistence    persistance
     * @param seed           sekla
     * @return
     */
    public SimplexArrayGenerator(int h, int w, int largestFeature, int seed, double persistence) {
        this.h = h;
        this.w = w;
        this.largestFeature = largestFeature;
        this.seed = seed;
        this.persistence = persistence;
    }

    public double[][] getSimplexMap() {
        SimplexNoise simplexNoise = new SimplexNoise(largestFeature, persistence, seed);
        double[][] map = new double[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int x = (int) (xStart + i * ((XEnd - xStart) / h));
                int y = (int) (yStart + j * ((yEnd - yStart) / w));
                map[i][j] = 0.5 * (1 + simplexNoise.getNoise(x, y));
            }
        }
        return (map);
    }
}
