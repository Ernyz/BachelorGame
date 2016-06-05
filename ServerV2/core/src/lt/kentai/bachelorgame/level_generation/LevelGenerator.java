package lt.kentai.bachelorgame.level_generation;

import java.util.Date;
import java.util.Random;

import lt.kentai.bachelorgame.Properties;
import map.StandardMapGenerator;
import map.components.MapComponents;

/**
 * Contains all logic for generating level.
 * 
 * @author Ernyz
 */
public class LevelGenerator {

	private StandardMapGenerator standardMapGenerator;
	private char[][] map = new char[Properties.MapWidth][Properties.MapHeight];
	
	//Example:
	//private CaveMapGenerator caveMapGenerator;
	//private ForestMapGenerator forestMapGenerator;
	
	public LevelGenerator() {
		standardMapGenerator = new StandardMapGenerator(300, 200, 0.35, 300);
	}

	public void setSEED(int seed){
		standardMapGenerator.setSEED(seed);
	}


	public char[][] generateLevel() {
		map = MapComponents.base;
		//Example:
		map = standardMapGenerator.generateMap();
		
		return map;
	}
	
}
