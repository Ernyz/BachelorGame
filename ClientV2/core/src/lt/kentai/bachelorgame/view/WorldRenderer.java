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
        int w = Gdx.graphics.getWidth()/2;

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
        }

        for (Entity e : match.getPlayerEntities()) {
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
