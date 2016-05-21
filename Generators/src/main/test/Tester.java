import map.StandardMapGenerator;

import java.util.Random;

public class Tester {


    public void testMapCreation(){
    }

    public static void main(String[] args) {
        int seed = new Random().nextInt();
        System.out.println("seed");
        System.out.println(seed);
        StandardMapGenerator standardMapGenerator = new StandardMapGenerator(300,200,
                seed,0.4,280);
        standardMapGenerator.generateMap();

    }
}
