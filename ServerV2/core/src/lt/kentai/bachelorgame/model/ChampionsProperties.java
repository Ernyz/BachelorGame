package lt.kentai.bachelorgame.model;

/**
 * Holds information about every champion (stats, name, etc.).
 * 
 * @author Ernyz
 */
public class ChampionsProperties {

	//TODO: Move info to external JSON files
	
	public static String[] championNames = {
			Champion1.championName,
			Champion2.championName,
			Champion3.championName,
			Champion4.championName
	};
	
	//Blue one
	public static class Champion1 {
		public static final String championName = "Bob";
		public static final float speed = 50f;
	}
	
	//Red one
	public static class Champion2 {
		public static final String championName = "Gorbag";
		public static final float speed = 60f;
	}
	
	public static class Champion3 {
		public static final String championName = "GorbagRed";
		public static final float speed = 60f;
	}
	
	public static class Champion4 {
		public static final String championName = "GorbagBlue";
		public static final float speed = 60f;
	}
	
}
