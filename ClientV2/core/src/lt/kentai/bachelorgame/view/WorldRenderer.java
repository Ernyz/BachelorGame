package lt.kentai.bachelorgame.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
        camera.position.x = match.getPlayer().getX() + match.getPlayer().getTexture().getWidth() / 2;
        camera.position.y = match.getPlayer().getY() + match.getPlayer().getTexture().getHeight() / 2;
        camera.update();

        batch.begin();
        /*int w = Gdx.graphics.getWidth()/2;

        int startX = (int) (match.getPlayer().getX()/10 - 20 < 0 ? 0 : match.getPlayer().getX()/10 - 20);
        int endX = (int) (match.getPlayer().getX()/10 + 20 > match.getMap().length ? match.getMap().length : match.getPlayer().getY()/10 + 20);//TODO

        int startY = (int) (match.getPlayer().getY()/10 - 20 < 0 ? 0 : match.getPlayer().getY()/10 - 20);
        int endY = (int) (match.getPlayer().getY()/10 + 20 > match.getMap()[0].length ? match.getMap().length : match.getPlayer().getY()/10 + 20);//TODO
        try {
            for (int y = startY; y < endY; y++) {
                for (int x = startX; x < endX; x++) {
                    batch.draw(match.getMapEntities()[y][x].getTexture(), match.getMapEntities()[y][x].getX(), match.getMapEntities()[y][x].getY());
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }*/
        
//        int range = 8;
//        
//        int px = (int)Math.ceil(match.getPlayer().getX()/10);
//        int py = (int)Math.ceil(match.getPlayer().getY()/10);
//        
//        int startX = px-range < 0 ? 0 : px-range;
//        int startY = py-range < 0 ? 0 : py-range;
//        
//        int endX = px+range > match.getMapEntities()[0].length ? match.getMapEntities()[0].length : px+range;
//        int endY = py+range > match.getMapEntities().length ? match.getMapEntities().length : py+range;
//        
//        for(int y = startY; y <= endY; y++) {
//        	for(int x = startX; x <= endX; x++) {
//        		batch.draw(match.getMapEntities()[y][x].getTexture(), match.getMapEntities()[y][x].getX(), match.getMapEntities()[y][x].getY());
//        	}
//        }
        
        /*for(int y = 0; y < match.getMapEntities().length; y++) {
        	for(int x = 0; x < match.getMapEntities()[0].length; x++) {
        		float dx = (float)Math.abs(x - Math.ceil(match.getPlayer().getX()/10));
        		float dy = (float)Math.abs(y - Math.ceil(match.getPlayer().getY()/10));
            	if(Math.sqrt(dx*dx + dy*dy) <= 8) {
            		batch.draw(match.getMapEntities()[y][x].getTexture(), match.getMapEntities()[y][x].getX(), match.getMapEntities()[y][x].getY());
            	}
            }
        }*/

        for (Entity e : match.getPlayerEntities()) {
            batch.draw(e.getTexture(), e.getX(), e.getY());
        }
        batch.end();

        //headsUpDisplay.updateAndRender(delta);
    }
}
