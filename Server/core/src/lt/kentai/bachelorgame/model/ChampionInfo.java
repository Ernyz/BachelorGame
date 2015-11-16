package lt.kentai.bachelorgame.model;

/**
 * Holds information about every champion (stats, name, etc.).
 * 
 * @author Ernyz
 */
public class ChampionInfo {

	//TODO: Move info to external JSON files
	
	public static String[] championNames = {
			Champion1.championName,
			Champion2.championName
	};
	
	//Blue one
	public static class Champion1 {
		public static final String championName = "Champion1";
		public static final float speed = 50f;
	}
	
	//Red one
	public static class Champion2 {
		public static final String championName = "Champion2";
		public static final float speed = 50f;
	}
	
}
