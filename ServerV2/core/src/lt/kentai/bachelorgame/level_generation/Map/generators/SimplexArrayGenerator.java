package lt.kentai.bachelorgame.level_generation.Map.generators;


import lt.kentai.bachelorgame.level_generation.Map.simplex.SimplexNoise;

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
     * @param seed           sekla :D
     * @return
     */
    public SimplexArrayGenerator(int h, int w, int largestFeature, int seed, double persistence) {
        this.h = h;
        this.w = w;
        this.largestFeature = largestFeature;
        this.seed = seed;
        this.persistence = persistence;
    }

    public double[][] getSimplexMap2() {
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

    public double getxStart() {
        return xStart;
    }

    public void setxStart(double xStart) {
        this.xStart = xStart;
    }

    public double getXEnd() {
        return XEnd;
    }

    public void setXEnd(double XEnd) {
        this.XEnd = XEnd;
    }

    public double getyStart() {
        return yStart;
    }

    public void setyStart(double yStart) {
        this.yStart = yStart;
    }

    public double getyEnd() {
        return yEnd;
    }

    public void setyEnd(double yEnd) {
        this.yEnd = yEnd;
    }
}
