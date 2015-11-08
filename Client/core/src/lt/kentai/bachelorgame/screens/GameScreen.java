package lt.kentai.bachelorgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;

import lt.kentai.bachelorgame.GameClient;
import lt.kentai.bachelorgame.Match;
import lt.kentai.bachelorgame.Network.MatchInfo;
import lt.kentai.bachelorgame.Network.RequestForMatchInfo;
import lt.kentai.bachelorgame.Properties.Team;
import lt.kentai.bachelorgame.model.Entity;
import lt.kentai.bachelorgame.model.server_data.ChampionData;

/**
 * The actual game is rendered in this screen.
 * 
 * @author ernyz
 */
public class GameScreen implements Screen {
	
	private final int matchId;  //FIXME: Something is not right. Check if default constructor can be called (it should not be available).
	
	private SpriteBatch batch;
	private GameClient mainClass;
	private Client client;
	
	private Match match;
	MatchInfo matchInfo;
	private boolean initializeMatch = false;
	private boolean matchInitialized = false;
	
	public GameScreen(final int matchId, SpriteBatch batch, GameClient mainClass, Client client) {
		this.matchId = matchId;
		this.batch = batch;
		this.mainClass = mainClass;
		this.client = client;
		
		//Send request for server so he returns info about this match
		RequestForMatchInfo requestInfo = new RequestForMatchInfo(matchId);
		client.sendTCP(requestInfo);
	}

	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(initializeMatch) initializeMatch();
		
		if(!matchInitialized) return;
		
		//TODO: move most of the stuff in this class to a proper game world class.
		batch.begin();
		
		for(Entity e : match.getPlayerEntities()) {
			batch.draw(e.getTexture(), e.getX(), e.getY());
		}
		
		batch.end();
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
	
	public void initializeMatch() {
		match = new Match();
		
		for(ChampionData champion : matchInfo.champions) {
			Entity e = new Entity(champion.getX(), champion.getY());
			if(champion.getTeam() == Team.BLUE) {
				e.setTexture(new Texture(Gdx.files.internal("champions/blueChampion.png")));
			} else if(champion.getTeam() == Team.RED) {
				e.setTexture(new Texture(Gdx.files.internal("champions/redChampion.png")));
			}
			match.getPlayerEntities().add(e);
		}
		/*Entity e = new Entity(matchInfo.x, matchInfo.y);
		if(matchInfo.team == Team.BLUE) {
			e.setTexture(new Texture(Gdx.files.internal("champions/blueChampion.png")));
		} else if(matchInfo.team == Team.RED) {
			e.setTexture(new Texture(Gdx.files.internal("champions/redChampion.png")));
		}
		match.getPlayerEntities().add(e);*/
		
		matchInitialized = true;
	}
	
	public void setInitializeMatch(MatchInfo matchInfo) {
		this.matchInfo = matchInfo;
		initializeMatch = true;
	}
	
}
