package map.utils;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

public class MapPrinter {

    public static String printMapToFile(final String filename, final char[][] map, boolean createDateStamp) {
        PrintWriter file = null;
        Date date = Calendar.getInstance().getTime();
        String fileName = filename + d2(date) + (createDateStamp ? Calendar.getInstance().getTimeInMillis() : "") + ".txt";
        try {
            file = new PrintWriter("C:\\maps\\"+fileName, "UTF-8");
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[0].length; j++) {
                    file.print(map[i][j]);
                }
                file.println();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            file.close();
        }
        return fileName;

    }

    public static void printMapToConsole(final char[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }

    }

    private static String d2(Date d) {
        return d.getYear() + "-" + d.getMonth() + 1 + "-" + d.getDay() + "-" + d.getMinutes();
    }


    public static void printMapToFile(final double[][] map, final int mapW, final int mapH, final String filename) {
        PrintWriter file = null;

        try {
            Date date = Calendar.getInstance().getTime();
            file = new PrintWriter(filename + d2(date) + ".txt", "UTF-8");

            for (int i = 0; i < mapH; i++) {
                for (int j = 0; j < mapW; j++) {
                    file.print(map[i][j]);
                }
                file.println();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            file.close();
        }

    }

    public static void printMapToFile(final int[][] map, final int mapW, final int mapH, final String filename) {
        PrintWriter file = null;

        try {
            Date date = Calendar.getInstance().getTime();
            file = new PrintWriter("C:\\maps\\"+filename + d2(date) + ".txt", "UTF-8");

            for (int i = 0; i < mapH; i++) {
                for (int j = 0; j < mapW; j++) {
                    file.print(getSimbol(map[i][j]));
                }
                file.println();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            file.close();
        }

    }

    private static char getSimbol(int i){
        if (i==0){
            return '.';
        }else return '#';
    }

    public static void printDebugMapToFile(final int[][] map, final int mapW, final int mapH, final String filename) {
        PrintWriter file = null;

        try {
            Date date = Calendar.getInstance().getTime();
            file = new PrintWriter(filename + d2(date) + "&debug.txt", "UTF-8");

            for (int i = 0; i < mapH; i++) {
                for (int j = 0; j < mapW; j++) {
                    file.print("\t[" + i + ":" + j + "]" + map[i][j]);
                }
                file.println();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            file.close();
        }

    }

}
