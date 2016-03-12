package lt.kentai.bachelorgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import lt.kentai.bachelorgame.GameClientV2;
import lt.kentai.bachelorgame.networking.ClientWrapper;
import lt.kentai.bachelorgame.networking.Network.Matchmaking;

public class MainMenuScreen implements Screen {

	private ClientWrapper client;
	
	private boolean inMatchmaking = false;
	
	//GUI stuff
	private Stage stage;
	private Skin skin;
	private Table table;
	private TextButton playStopBtn;
	
	public MainMenuScreen() {
		this.client = GameClientV2.getNetworkingManager().getClientWrapper();
	}
	
	@Override
	public void show() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		inMatchmaking = false;
		
		setupUI();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//Manage GUI
		if(inMatchmaking) {
			playStopBtn.setText("Exit matchmaking");
		} else {
			playStopBtn.setText("Play!");
		}
		
		stage.act(delta);
		stage.draw();
	}
	
	/**
	 * Should be called whenever player should be put into matchmaking after
	 * returning to MainMenu screen. */
	public void reenterMatchmaking() {
		inMatchmaking = true;
		Matchmaking m = new Matchmaking();
		m.entering = true;
		client.sendTCP(m);
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
		table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		table.setDebug(false);
		skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
		
		table.row();
		playStopBtn = new TextButton("Play!", skin);
		playStopBtn.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				inMatchmaking = !inMatchmaking;
				Matchmaking m = new Matchmaking();
				if(inMatchmaking) {
					m.entering = true;
				} else {
					m.entering = false;
				}
				client.sendTCP(m);
			}
		});
		table.add(playStopBtn);
		
		table.row();
		final TextButton exitBtn = new TextButton("Exit", skin);
		table.add(exitBtn);
		exitBtn.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.exit();
			}
		});
	}

}
