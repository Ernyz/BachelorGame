package lt.kentai.bachelorgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.esotericsoftware.kryonet.Client;

import lt.kentai.bachelorgame.GameClientV2;
import lt.kentai.bachelorgame.Network.LockIn;

public class LobbyScreen implements Screen {

	private final int matchId;
	private final String[] championNames;
	
	private String selectedChampion = "";
	
	private Client client;
	
	private Table table;
	private Stage stage;
	private Skin skin;
	private Label timerLabel;
	private TextButton lockInBtn;

	private float championSelectionTimer = 8f;

	public LobbyScreen(final int matchId, String[] championNames) {
		this.matchId = matchId;
		this.championNames = championNames;
		this.client = GameClientV2.getNetworkingManager().getClient();
	}

	@Override
	public void show() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		setupUI();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		championSelectionTimer -= delta;
		timerLabel.setText(MathUtils.floor(championSelectionTimer) + "");
		if (championSelectionTimer <= 0) {
			//TODO: check if all the player have selected a champion (on the server side of course).
			
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
		table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		
		table.setDebug(false);
		
		table.row();
		timerLabel = new Label(championSelectionTimer + "", skin);
		table.add(timerLabel);
		
		table.row();
		//List all the champions
		for(String name : championNames) {
			final TextButton championBtn = new TextButton(name, skin);
			championBtn.addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					return true;
				}
				public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
					selectedChampion = championBtn.getText().toString();
				}
			});
			table.add(championBtn);
		}
		
		table.row();
		lockInBtn = new TextButton("Lock in", skin);
		lockInBtn.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				lockIn();
			}
		});
		table.add(lockInBtn);
		table.row();

		timerLabel.setText(MathUtils.floor(championSelectionTimer) + "");
	}

	private void lockIn() {
		LockIn lockInPacket = new LockIn();
		lockInPacket.championName = selectedChampion;
		lockInPacket.matchId = matchId;
		client.sendTCP(lockInPacket);
	}

}
