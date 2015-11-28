package lt.kentai.bachelorgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;

import lt.kentai.bachelorgame.GameClient;
import lt.kentai.bachelorgame.Match;
import lt.kentai.bachelorgame.Network.MatchInfo;
import lt.kentai.bachelorgame.Network.MoveChampion;
import lt.kentai.bachelorgame.Network.RequestForMatchInfo;
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
	private boolean matchReady = false;
	private boolean initializeMatch = false;
	private boolean matchInitialized = false;
	
	//XXX: test
	private Entity player;
	
	public GameScreen(final int matchId, SpriteBatch batch, GameClient mainClass, Client client) {
		this.matchId = matchId;
		this.batch = batch;
		this.mainClass = mainClass;
		this.client = client;
		
//		//Send request for server so he returns info about this match
//		RequestForMatchInfo requestInfo = new RequestForMatchInfo(matchId);
//		client.sendTCP(requestInfo);
	}
	
	//XXX: remove this later
	public void movePlaya(float x, float y) {
		//player.moveBy(Gdx.graphics.getDeltaTime(), x, y);
		for(int i = 0; i < match.getPlayerEntities().size; i++) {
			if(!match.getPlayerEntities().get(i).equals(player)) {
				match.getPlayerEntities().get(i).moveBy(Gdx.graphics.getDeltaTime(), x, y);
			}
		}
	}

	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(!matchReady) {
			//Send request for server so he returns info about this match
//			RequestForMatchInfo requestInfo = new RequestForMatchInfo(matchId);
//			client.sendTCP(requestInfo);
			return;
		}
		if(initializeMatch) initializeMatch();
		if(!matchInitialized) return;
		
		//XXX: Test
		if(Gdx.input.isKeyPressed(Keys.W)) {
			player.moveBy(delta, 0f, 1f);
			client.sendUDP(new MoveChampion(0f, 1f));
		}
		if(Gdx.input.isKeyPressed(Keys.A)) {
			player.moveBy(delta, -1f, 0f);
			client.sendUDP(new MoveChampion(-1f, 0f));
		}
		if(Gdx.input.isKeyPressed(Keys.S)) {
			player.moveBy(delta, 0f, -1f);
			client.sendUDP(new MoveChampion(0f, -1f));
		}
		if(Gdx.input.isKeyPressed(Keys.D)) {
			player.moveBy(delta, 1f, 0f);
			client.sendUDP(new MoveChampion(1f, 0f));
		}
		
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
		if(matchInitialized) return;
		
		match = new Match();
		
		Log.info("initialize match called");
		
		for(ChampionData champion : matchInfo.champions) {
			Entity e = new Entity(champion.getX(), champion.getY());
			e.connectionId = champion.getConnectionId();
			e.championName = champion.getChampionName();
			e.setSpeed(champion.getSpeed());//FIXME speed
			System.out.println("Champion name: " + e.championName + " " + e.getSpeed());
			e.setTexture(new Texture(Gdx.files.internal("champions/"+e.championName+"/"+e.championName+".png")));
			match.getPlayerEntities().add(e);
		}
		
		for(Entity e : match.getPlayerEntities()) {
			if(e.connectionId == client.getID()) {
				player = e;
			}
		}
		
		matchInitialized = true;
		initializeMatch = false;
	}
	
	public void setInitializeMatch(MatchInfo matchInfo) {
		this.matchInfo = matchInfo;
		initializeMatch = true;
	}
	
	public void setMatchReady() {
		matchReady = true;
		System.out.println(matchReady);
		//Send request for server so he returns info about this match
		RequestForMatchInfo requestInfo = new RequestForMatchInfo(matchId);
		client.sendTCP(requestInfo);
	}
	
}
