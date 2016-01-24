package lt.kentai.bachelorgame.generators.Map.utils;

public class Log {
    public static void log(String s) {
        System.out.println(s);
    }

    public static void logMapSize(String s, int w, int h) {
        System.out.println(s + " w : " + w + "\th : " + h);
    }

    public static void logE(String s) {
        System.out.println("error : " + s);
    }
}
