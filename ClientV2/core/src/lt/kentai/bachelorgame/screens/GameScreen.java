package lt.kentai.bachelorgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;
import lt.kentai.bachelorgame.GameClientV2;
import lt.kentai.bachelorgame.Match;
import lt.kentai.bachelorgame.Network.MatchInfo;
import lt.kentai.bachelorgame.Network.PlayerStateUpdate;
import lt.kentai.bachelorgame.Network.RequestForMatchInfo;
import lt.kentai.bachelorgame.Network.UserInput;
import lt.kentai.bachelorgame.Properties;
import lt.kentai.bachelorgame.model.Entity;
import lt.kentai.bachelorgame.model.server_data.ChampionData;
import lt.kentai.bachelorgame.view.InputView;
import lt.kentai.bachelorgame.view.WorldRenderer;
import map.StandardMapGenerator;

import java.util.Iterator;

/**
 * The actual game is rendered in this screen.
 *
 * @author Ernyz
 */
public class GameScreen implements Screen {


    private final int matchId;

    private SpriteBatch batch;
    private Client client;

    private Array<UserInput> sentPackets = new Array<UserInput>();
    private Array<PlayerStateUpdate> receivedPlayerStateUpdate = new Array<PlayerStateUpdate>();

    private float accumulator = 0f;
    private int frameNumber = 0;  //TODO: make this uint

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

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (!matchInitialized) return;
//        Log.set(Log.LEVEL_NONE);
        accumulator += delta;
        while (accumulator >= Properties.FRAME_TIME) {
            //Sample and execute input
            boolean[] input = inputView.getInput();
            UserInput userInput = new UserInput(matchId, frameNumber, input);
            match.executeInput(userInput);
            match.update();
            //Send input to server
            client.sendUDP(userInput);
            sentPackets.add(userInput);
            //Read received packets and update game state
            applyServerState();

            accumulator -= Properties.FRAME_TIME;

            //XXX: Less jerky here
            worldRenderer.render(Properties.FRAME_TIME);
        }

        //XXX: Very jerky here
//		worldRenderer.render(delta);

        frameNumber++;//TODO: check if this is enough to handle wrap around
        if (frameNumber < 0) {
            frameNumber = 0;
        }
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

    private void applyServerState() {
        //Update all the players so they match server state
        if (receivedPlayerStateUpdate.size > 0) {
            Array<Entity> entities = match.getPlayerEntities();
            PlayerStateUpdate stateUpdate = receivedPlayerStateUpdate.removeIndex(0);
            for (int i = 0; i < entities.size; i++) {
                for (int j = 0; j < stateUpdate.playerStates.size; j++) {
                    if (entities.get(i).connectionId == stateUpdate.playerStates.get(j).connectionId) {
                        if (entities.get(i).connectionId != match.getPlayer().connectionId) {
                            entities.get(i).recalculateTargetPositions(stateUpdate.playerStates.get(j).x, stateUpdate.playerStates.get(j).y);
                        } else {
//							System.out.println("Before snapping: " + match.getPlayer().getX() + " "+ match.getPlayer().getY());
                            match.getPlayer().setX(stateUpdate.playerStates.get(j).x);
                            match.getPlayer().setY(stateUpdate.playerStates.get(j).y);
//							System.out.println("After snapping: " + match.getPlayer().getX() + " "+ match.getPlayer().getY());
                            //Re-apply player input which is not yet confirmed by the server
                            Iterator<UserInput> iterator = sentPackets.iterator();
                            while (iterator.hasNext()) {
                                UserInput sentPacket = iterator.next();
                                if (sentPacket.sequenceNumber <= stateUpdate.playerStates.get(j).lastProcessedPacket) {
                                    iterator.remove();
                                } else {
                                    //match.executeInput(sentPacket);
                                    match.updatePlayer(sentPacket);
                                }
                            }
//							System.out.println("After prediction: " + match.getPlayer().getX() + " "+ match.getPlayer().getY());
							System.out.println();
                        }
                    }
                }
            }
        }
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
        StandardMapGenerator mapGenerator = new StandardMapGenerator(300, 200, 0.35, 300);
        mapGenerator.setSEED(matchInfo.seed);
        match.setMap(mapGenerator.generateMap());

//		for (int i = 0; i <match.getMap().length ; i++) {
//			for (int j = 0; j < match.getMap()[0].length; j++) {
//				System.out.print(match.getMap()[i][j]);
//			}
//			System.out.println();
//		}
        matchInitialized = true;

        worldRenderer = new WorldRenderer(batch, match);
        inputView = new InputView(client, match);
        Gdx.input.setInputProcessor(inputView);
    }

    public Match getMatch() {
        return match;
    }

    public Array<PlayerStateUpdate> getReceivedPlayerStateUpdate() {
        return receivedPlayerStateUpdate;
    }

}
