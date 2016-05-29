package lt.kentai.bachelorgame.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import lt.kentai.bachelorgame.Match;
import lt.kentai.bachelorgame.model.Entity;

public class WorldRenderer {

	
	private SpriteBatch batch;
	private Match match;
	private OrthographicCamera camera;
	private HeadsUpDisplay headsUpDisplay;
	
	public WorldRenderer(SpriteBatch batch, Match match) {
		this.batch = batch;
		this.match = match;
		
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.setToOrtho(false);
		camera.update();
		
		headsUpDisplay = new HeadsUpDisplay(match, batch);
	}
	
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		camera.position.x = match.getPlayer().getX() + match.getPlayer().getTexture().getWidth()/2;
		camera.position.y = match.getPlayer().getY() + match.getPlayer().getTexture().getHeight()/2;
		camera.update();
		
		batch.begin();
		
//		for(int x = 0; x < match.getMap().length; x++) {
//			for(int y = 0; y < match.getMap()[0].length; y++) {
//		for(int x = 0; x < 50; x++) {
//			for(int y = 0; y < 50; y++) {
//				if(match.getMap()[x][y] == '#') {
//					batch.draw(new Texture("tiles/wall.png"), x*10, y*10);
//				} else if(match.getMap()[x][y] == ' ') {
//					batch.draw(new Texture("tiles/grass.png"), x*10, y*10);
//				} else if(match.getMap()[x][y] == '.') {
//					batch.draw(new Texture("tiles/road.png"), x*10, y*10);
//				}
//			}
//		}
		
		for(Entity e : match.getPlayerEntities()) {
			batch.draw(e.getTexture(), e.getX(), e.getY());
		}
		batch.end();
		
		//headsUpDisplay.updateAndRender(delta);
		
//		if(Gdx.input.isKeyPressed(Keys.W)) {
//			camera.translate(0f, 5f);
//		}
//		if(Gdx.input.isKeyPressed(Keys.A)) {
//			camera.translate(-5f, 0f);
//		}
//		if(Gdx.input.isKeyPressed(Keys.S)) {
//			camera.translate(0f, -5f);
//		}
//		if(Gdx.input.isKeyPressed(Keys.D)) {
//			camera.translate(5f, 0f);
//		}
	}
}
