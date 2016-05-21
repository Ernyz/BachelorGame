package lt.kentai.bachelorgame.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import lt.kentai.bachelorgame.Match;
import lt.kentai.bachelorgame.model.Entity;
import lt.kentai.bachelorgame.screens.GameScreen;
import lt.kentai.bachelorgame.test.MapTile;

public class WorldRenderer {
<<<<<<< HEAD

    private SpriteBatch batch;
    private Match match;
    private OrthographicCamera camera;

    public WorldRenderer(SpriteBatch batch, Match match) {
        this.batch = batch;
        this.match = match;

        camera = new OrthographicCamera(Gdx.graphics.getWidth()*0.7f, Gdx.graphics.getHeight()*0.7f);
        camera.setToOrtho(false);

        camera.update();
    }

    public Array<MapTile> visibleTiles = new Array<MapTile>();

    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        recrVisibleTiles();


        batch.setProjectionMatrix(camera.combined);
        batch.begin();

=======
	
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
		
>>>>>>> 87de7c271e6710d3479106be9760c6d02e8b0dc0
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
<<<<<<< HEAD
        for (int i = 0; i < visibleTiles.size; i++) {
            batch.draw(visibleTiles.get(i).texture, visibleTiles.get(i).x, visibleTiles.get(i).y);
        }


        for (Entity e : match.getPlayerEntities()) {
            batch.draw(e.getTexture(), e.getX(), e.getY());
        }
        batch.end();


        if (Gdx.input.isKeyPressed(Keys.W)) {
            camera.translate(0f, 5f);
        }
        if (Gdx.input.isKeyPressed(Keys.A)) {
            camera.translate(-5f, 0f);
        }
        if (Gdx.input.isKeyPressed(Keys.S)) {
            camera.translate(0f, -5f);
        }
        if (Gdx.input.isKeyPressed(Keys.D)) {
            camera.translate(5f, 0f);
        }
        camera.update();
    }

    private void recrVisibleTiles() {
        visibleTiles.clear();
        for (int i = 0; i < GameScreen.mapTiles.size; i++) {
            float x,y;
            x = camera.position.x;
            y = camera.position.y;
            if(GameScreen.mapTiles.get(i).x>x-200&&GameScreen.mapTiles.get(i).x<x+200){
                if(GameScreen.mapTiles.get(i).y>y-200&&GameScreen.mapTiles.get(i).y<y+200){
                    visibleTiles.add(GameScreen.mapTiles.get(i));

                }
            }
        }
    }
=======
		
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
>>>>>>> 87de7c271e6710d3479106be9760c6d02e8b0dc0
}
