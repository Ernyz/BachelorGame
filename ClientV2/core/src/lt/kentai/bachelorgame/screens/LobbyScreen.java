package lt.kentai.bachelorgame.screens;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;

import lt.kentai.bachelorgame.GameClientV2;
import lt.kentai.bachelorgame.Network.AcceptedToLobby;
import lt.kentai.bachelorgame.Network.ChampionSelect;
import lt.kentai.bachelorgame.Network.ChampionSelectResponse;
import lt.kentai.bachelorgame.Network.LockIn;
import lt.kentai.bachelorgame.Properties.Team;

public class LobbyScreen implements Screen {

	private final int matchId;
	private final HashMap<Integer, Team> connectionIds;
	private final String[] championNames;
	
	private String selectedChampion = "";
	private Array<Image> selectedChampionIcons;
	
	private Client client;
	
	private Table table;
	private Stage stage;
	private Skin skin;
	
	private TextureAtlas championIconAtlas;
	private Skin championIconSkin;
	private Table allyChampionsTable;
	private Table selectionTable;
	private float selectionTableWidth = 0.55f;
	private Table enemyChampionsTable;
	
	private Label timerLabel;
	private TextButton lockInBtn;

	private float championSelectionTimer = 20f;  //TODO Later on get this value from server

	public LobbyScreen(AcceptedToLobby lobbyInfo) {
		this.matchId = lobbyInfo.matchId;
		this.connectionIds = lobbyInfo.connectionIds;
		this.championNames = lobbyInfo.championNames;
		this.client = GameClientV2.getNetworkingManager().getClient();
	}

	@Override
	public void show() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		selectedChampionIcons = new Array<Image>();
		
		setupUI();
	}

	@Override
	public void render(float delta) {
		if(connectionIds.get(client.getID()) == Team.BLUE) {
			Gdx.gl.glClearColor(0, 0, 1, 1);
		} else if(connectionIds.get(client.getID()) == Team.RED) {
			Gdx.gl.glClearColor(1, 0, 0, 1);
		}
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		championSelectionTimer -= delta;
		timerLabel.setText(MathUtils.floor(championSelectionTimer) + "");
		if (championSelectionTimer <= 0) {
			//TODO: check if all the players have selected a champion (on the server side of course).
			
			lockIn();
		}
		
		stage.act(delta);
		stage.draw();
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
		stage.dispose();
		skin.dispose();
	}

	private void setupUI() {
		skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
		table = new Table(skin);
		table.setFillParent(true);
		stage.addActor(table);
		
		championIconAtlas = new TextureAtlas("ui/champion_select/ChampionIcons.atlas");
		championIconSkin = new Skin(championIconAtlas);
		
		table.setDebug(true, true);
		
		/* 
		 * Allied champion table
		 */
		allyChampionsTable = new Table(skin);
		table.add(allyChampionsTable).width(Gdx.graphics.getWidth()*(1-selectionTableWidth)/2).height(Gdx.graphics.getHeight());
		for(Map.Entry<Integer, Team> entry : connectionIds.entrySet()) {
			if(entry.getValue() == connectionIds.get(client.getID())) {
				allyChampionsTable.row();
				Image img = new Image(championIconSkin.getDrawable("ChampionIcon_Empty"));
				img.setUserObject(new ChampionIconUserObject(entry.getKey(), entry.getValue()));
				allyChampionsTable.add(img);
				selectedChampionIcons.add(img);
			}
		}
		
		/*
		 * Selection table
		 */
		selectionTable = new Table(skin);
		table.add(selectionTable).width(Gdx.graphics.getWidth()*selectionTableWidth).height(Gdx.graphics.getHeight());
		
		selectionTable.row();
		timerLabel = new Label(championSelectionTimer + "", skin);
		selectionTable.add(timerLabel);
		timerLabel.setText(MathUtils.floor(championSelectionTimer) + "");
		
		selectionTable.row();
		for(final String name : championNames) {
			final ImageButton championBtn = new ImageButton(championIconSkin.getDrawable("ChampionIcon_"+name));
			championBtn.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					return true;
				}
				public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
					if(!selectedChampion.equals(name)) {
						System.out.println("Sending champ select id: " + matchId);
						client.sendTCP(new ChampionSelect(name, matchId));
					}
				}
			});
			selectionTable.add(championBtn);
		}
		
		selectionTable.row();
		lockInBtn = new TextButton("Lock in", skin);
		lockInBtn.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				lockIn();
			}
		});
		selectionTable.add(lockInBtn);
		
		/*
		 * Enemy champions table
		 */
		enemyChampionsTable = new Table(skin);
		table.add(enemyChampionsTable).width(Gdx.graphics.getWidth()*(1-selectionTableWidth)/2).height(Gdx.graphics.getHeight());
		for(Map.Entry<Integer, Team> entry : connectionIds.entrySet()) {
			if(entry.getValue() != connectionIds.get(client.getID())) {
				enemyChampionsTable.row();
				Image img = new Image(championIconSkin.getDrawable("ChampionIcon_Empty"));
				img.setUserObject(new ChampionIconUserObject(entry.getKey(), entry.getValue()));
				enemyChampionsTable.add(img);
			}
		}
	}
	
	public void selectChampion(ChampionSelectResponse response) {
		if(response.success) {
			if(response.connectionId == client.getID()) {
				selectedChampion = response.championName;
			}
			for(Image i : selectedChampionIcons) {
				ChampionIconUserObject userObj = (ChampionIconUserObject) i.getUserObject();
				if(userObj.team == connectionIds.get(client.getID())) {
					if(userObj.connectionId == response.connectionId) {
						i.setDrawable(championIconSkin.getDrawable("ChampionIcon_"+response.championName));
					}
				}
			}
			Log.info(response.championName + " selected by " + response.connectionId);
		}
	}

	private void lockIn() {
		LockIn lockInPacket = new LockIn();
		lockInPacket.championName = selectedChampion;
		lockInPacket.matchId = matchId;
		client.sendTCP(lockInPacket);
	}

	private class ChampionIconUserObject {
		public final int connectionId;
		public final Team team;
		public ChampionIconUserObject(final int connectionId, final Team team) {
			this.connectionId = connectionId;
			this.team = team;
		}
	}
	
}
