package lt.kentai.bachelorgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryonet.Client;
import lt.kentai.bachelorgame.GameClientV2;
import lt.kentai.bachelorgame.Match;
import lt.kentai.bachelorgame.Network.MatchInfo;
import lt.kentai.bachelorgame.Network.RequestForMatchInfo;
import lt.kentai.bachelorgame.model.Entity;
import lt.kentai.bachelorgame.model.server_data.ChampionData;
import lt.kentai.bachelorgame.test.MapTile;
import lt.kentai.bachelorgame.view.InputView;
import lt.kentai.bachelorgame.view.WorldRenderer;
import map.StandardMapGenerator;

/**
 * The actual game is rendered in this screen.
 *
 * @author Ernyz
 */
public class GameScreen implements Screen {

    private final int matchId;

    private SpriteBatch batch;
    private Client client;

    private InputView inputView;
    private WorldRenderer worldRenderer;

    private Match match;
    private MatchInfo matchInfo;
    private boolean matchInitialized = false;

    public GameScreen(final int matchId, SpriteBatch batch) {
        this.matchId = matchId;
        this.batch = batch;
        this.client = GameClientV2.getNetworkingManager().getClient();
        //Send request for server so he returns info about this match
        RequestForMatchInfo requestInfo = new RequestForMatchInfo(matchId);
        client.sendTCP(requestInfo);
    }

//	public void movePlaya(float x, float y) {
//		for(int i = 0; i < match.getPlayerEntities().size; i++) {
//			if(!match.getPlayerEntities().get(i).equals(match.getPlayer())) {
//				match.getPlayerEntities().get(i).moveBy(Gdx.graphics.getDeltaTime(), x, y);
//			}
//		}
//	}

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (!matchInitialized) return;

        worldRenderer.render();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public void initializeMatch(MatchInfo matchInfo) {
        if (matchInitialized) return;

        this.matchInfo = matchInfo;
        match = new Match(matchId);

        for (ChampionData champion : matchInfo.champions) {
            Entity e = new Entity(champion.getX(), champion.getY());
            e.connectionId = champion.getConnectionId();
            e.championName = champion.getChampionName();
            e.setSpeed(champion.getSpeed());
            e.setTexture(new Texture(Gdx.files.internal("champions/" + e.championName + "/" + e.championName + ".png")));
            match.getPlayerEntities().add(e);
        }

        for (Entity e : match.getPlayerEntities()) {
            if (e.connectionId == client.getID()) {
                match.setPlayer(e);
            }
        }
        StandardMapGenerator mapGenerator = new StandardMapGenerator(300, 100, matchInfo.seed, 0.35, 300);

        match.setMap(mapGenerator.generateMap());
//		for (int i = 0; i <match.getMap().length ; i++) {
//			for (int j = 0; j < match.getMap()[0].length; j++) {
//				System.out.print(match.getMap()[i][j]);
//			}
//			System.out.println();
        mapTiles = new Array<MapTile>();
        for (int i = 0; i < match.getMap().length; i++) {
            for (int j = 0; j < match.getMap()[0].length; j++) {
                if (match.getMap()[i][j] == '#') {
                    mapTiles.add(new MapTile(new Texture("tiles/wall.png"),j*10,i*10));

                } else if (match.getMap()[i][j] == 'R') {
                    mapTiles.add(new MapTile(new Texture("tiles/road.png"),j*10,i*10));

                } else if (match.getMap()[i][j] == ' ') {
                    mapTiles.add(new MapTile(new Texture("tiles/grass.png"),j*10,i*10));

                }
            }
        }


        matchInitialized = true;

        worldRenderer = new WorldRenderer(batch, match);
        inputView = new InputView(client, match);
        Gdx.input.setInputProcessor(inputView);
    }

    public static Array<MapTile> mapTiles;
}
