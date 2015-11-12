package lt.kentai.bachelorgame.level_generator;

import lt.kentai.bachelorgame.Properties;

/**
 * Contains all logic for generating level.
 * 
 * @author Ernyz
 */
public class LevelGenerator {
	
	private char[][] map = new char[Properties.MapWidth][Properties.MapHeight];
	
	//Example:
	//private CaveMapGenerator caveMapGenerator;
	//private ForestMapGenerator forestMapGenerator;
	
	public LevelGenerator() {
		
	}

	public char[][] generateLevel() {
		//Example:
		//map = forestMapGenerator.generateMap();
		
		return map;
	}
	
}
