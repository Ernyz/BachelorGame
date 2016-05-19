package map.generators;


import map.utils.MapUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WayGenerator {

    private int width;
    private int height;
    private Random random;


    public WayGenerator(int width, int height, long seed) {
        this.width = width;
        this.height = height;
        random = new Random(seed);
    }

    public int[] getWayMap(int times, double maxAmplitudeCoef, double minAmplitudeCoef) {
        return (createArrayOfRandomlyPlacedPoints(times, height, maxAmplitudeCoef, minAmplitudeCoef));
    }

    public int[][] get2dMapFromPoints(int[] wayPoints) {
        return fillMapWithPoints(wayPoints);
    }

    public int[] createArrayOfRandomlyPlacedPoints(int iterationNr, int mapHeight, double maxAmplitude, double minAmplitude) {


        int mapMidY = mapHeight / 2;

        int maxA = (int) ((double) mapMidY * maxAmplitude);
        int minA = (int) ((double) mapMidY * minAmplitude);
        List<Integer> listOfPoints = new ArrayList<Integer>();
        listOfPoints.add(0, mapMidY);
        listOfPoints.add(1, mapMidY);
        printListOfIntegers(listOfPoints);
        int midP = 0;
        for (int i = 0; i < iterationNr; i++) {
            int currentListSize = listOfPoints.size() - 1;
            int newPoint;

            for (int j = 0; j < currentListSize; j++) {

                if (i == 0) {
                    newPoint = mapMidY + getSign() * random.nextInt(maxA);
                    System.out.println(newPoint);
                    midP = newPoint;
                    listOfPoints.add(1, newPoint);
                } else if (i == 1) {
                    if (listOfPoints.get(1) > mapMidY - minA && listOfPoints.get(1) < mapMidY + minA) {

                        newPoint = random.nextInt(mapMidY + maxA);
                        newPoint *= getSign();
                        newPoint += mapMidY;
                        System.out.println(newPoint);
                        listOfPoints.add(j * 2 + 1, newPoint);
                    } else {
                        newPoint = random.nextInt(maxA);
                        newPoint *= midP > mapMidY ? -1 : 1;
                        newPoint += mapMidY;
                        listOfPoints.add(j * 2 + 1, newPoint);
                    }
                } else {
                    int midPointY = (listOfPoints.get(j * 2 + 1) + listOfPoints.get(j * 2)) / 2;
                    midPointY += getDisplaicement(i, maxA);
                    listOfPoints.add(j * 2 + 1, midPointY);
                }
            }

        }
        System.out.println("JOJOJOJOJ");
        printListOfIntegers(listOfPoints);
        smoothenListOfWayPoints(listOfPoints, 3);

        printListOfIntegers(listOfPoints);
        int[] points = new int[listOfPoints.size()];
        for (int i = 0; i < listOfPoints.size(); i++) {
            points[i] = listOfPoints.get(i);
        }

        return points;

    }

    private int getDisplaicement(int i, int maxA) {
        return maxA / (i * i);
    }

    private void smoothenListOfWayPoints(List<Integer> list, int times) {
        for (int q = 0; q < times; q++) {
            int size = list.size();
            for (int i = 1; i < size - 1; i += 2) {
                int pB = list.get(i - 1);
                int pN = list.get(i);
                int pF = list.get(i + 1);
                int m1 = Math.min(pB, pN) + (Math.max(pB, pN) - Math.min(pB, pN)) / 2;
                int m2 = Math.min(pF, pN) + (Math.max(pF, pN) - Math.min(pF, pN)) / 2;
                list.set(i, Math.abs(m1));
                list.add(i + 1, Math.abs(m2));
                size += 1;
            }
            System.out.println(q);
            printListOfIntegers(list);

        }

    }

    private int getSign() {
        return random.nextInt(2) == 1 ? -1 : +1;
    }

    private void printListOfIntegers(List<Integer> list) {
        for (int i : list) {
            System.out.print(i + "\t");
        }
        System.out.println();
    }

    private int[][] fillMapWithPoints(int[] wayPointsArray) {
        int[][] map = new int[height][width];
        double xInterval = ((double) width - 20) / (double) (wayPointsArray.length - 1);
        for (int i = 0; i < wayPointsArray.length - 1; i++) {
            int startY = wayPointsArray[i];
            int endY = wayPointsArray[i + 1];
            float oneStepDisp = (float) (endY - startY) / (float) xInterval;
            for (double j = 0; j <= xInterval; j++) {
                int tempY = (int) (startY + oneStepDisp * j);
                int tempX = (int) ((double) i * xInterval + j + 10);
                widthenRoad(map, tempY, tempX);
//                map[tempY][tempX] = 1;
            }
        }
        return map;
    }

    private void widthenRoad(int[][] map, int y, int x) {
        int roadWidth = (int) (map.length * 0.10);

        int delta = roadWidth / 2;
        y = setupY(y, roadWidth);
        int minX = x - delta < 0 ? 0 : x - delta;
        int maxX = x + delta > width ? width : x + delta;
        for (int j = 0; j < roadWidth; j++) {
            for (int p = minX; p < maxX; p++) {
                if (MapUtils.inRange(x, y, p, y - delta + j, delta)) {
                    map[y - delta + j][p] = 1;
                }
            }
        }
    }


    public int setupY(int y, int roadW) {
        if (y + roadW > height) {
            int t = y + roadW - height;
            return y - t;
        } else if (y - roadW < 0) {
            return y - (y - roadW);
        }
        return y;
    }
}
