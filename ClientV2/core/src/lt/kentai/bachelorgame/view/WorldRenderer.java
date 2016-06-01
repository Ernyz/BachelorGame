package lt.kentai.bachelorgame.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import lt.kentai.bachelorgame.Match;
import lt.kentai.bachelorgame.model.Entity;
import map.utils.Constants;

public class WorldRenderer {

    private SpriteBatch batch;
    private Match match;
    private OrthographicCamera camera;
    private HeadsUpDisplay headsUpDisplay;
    
    //TODO: These should be moved out to some kind of asset class
    private Texture mainRoadTexture = new Texture("tiles/road.png");
    private Texture wallTexture = new Texture("tiles/wall.png");
    private Texture towerTexture = new Texture("tiles/wall.png");
    private Texture grassTexture = new Texture("tiles/grass.png");

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
        camera.position.x = match.getPlayer().getX() + match.getPlayer().getTexture().getWidth() / 2;
        camera.position.y = match.getPlayer().getY() + match.getPlayer().getTexture().getHeight() / 2;
        camera.update();

        batch.begin();
        
        int range = 45;
        int px = (int)Math.ceil(match.getPlayer().getX()/10);
        int py = (int)Math.ceil(match.getPlayer().getY()/10);
        int startX = px-range < 0 ? 0 : px-range;
        int startY = py-range < 0 ? 0 : py-range;
        int endX = px+range > match.getMap()[0].length ? match.getMap()[0].length : px+range;
        int endY = py+range > match.getMap().length ? match.getMap().length : py+range;
        for(int y = startY; y <= endY; y++) {
        	for(int x = startX; x <= endX; x++) {
        		if(match.getMap()[y][x] == Constants.MAIN_ROAD) {
        			batch.draw(mainRoadTexture, x*10, y*10);
	      		}
        		else if (match.getMap()[y][x] == Constants.WALL) {
	      			batch.draw(wallTexture, x*10, y*10);
	      		}
        		else if (match.getMap()[y][x] == Constants.TOWER) {
	      			batch.draw(towerTexture, x*10, y*10);
	      		}
        		else if (match.getMap()[y][x] == Constants.DIRT) {
	      			batch.draw(grassTexture, x*10, y*10);
	      		}
        		else {
	      			batch.draw(grassTexture, x*10, y*10);
	      		}
        	}
        }
        
        for (Entity e : match.getPlayerEntities()) {
            batch.draw(e.getTexture(), e.getX(), e.getY());
        }
        batch.end();

        //headsUpDisplay.updateAndRender(delta);
    }
}
