package lt.kentai.bachelorgame.level_generation;

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
//		standardMapGenerator = new StandardMapGenerator(20, 20, 10, 0.35, 300);
//		standardMapGenerator = new StandardMapGenerator(20, 20, 10, 0.35, 300);
	}

	public char[][] generateLevel() {
		map = MapComponents.base;
		//Example:
		//map = forestMapGenerator.generateMap();
		
		return map;
	}
	
}
