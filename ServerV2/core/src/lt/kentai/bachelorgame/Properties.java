package lt.kentai.bachelorgame;

public class Properties {

	public static final int FPS = 60;
	public static final float FRAME_TIME = 1f/FPS;
	
	public static int TeamSize = 1;
	public static enum Team {
		BLUE, RED
	}
	
	public static int MapWidth = 50;
	public static int MapHeight = 50;
	
	private Properties() {
		
	}
	
}
