package lt.kentai.bachelorgame.screens;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;

import lt.kentai.bachelorgame.ClientListener;
import lt.kentai.bachelorgame.Network;

public class MainMenuScreen implements Screen {
	
	private SpriteBatch batch;
	private Stage stage;
	private Table table;
	private Skin skin;
	// Client stuff
	private Client client;

	public MainMenuScreen(SpriteBatch batch) {
		this.batch = batch;
	}
	
	@Override
	public void show() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		setupUI();
		
		//setupNetworking();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
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
		table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		table.setDebug(true);  //Careful with this one, might cause memory leaks
		skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
		
		table.row();
		final Label enterIpLabel = new Label("Enter server IP:", skin);
		table.add(enterIpLabel);
		final TextField ipTextField = new TextField("127.0.0.1", skin);
		table.add(ipTextField);
		
		table.row().colspan(2);
		final TextButton connectBtn = new TextButton("Connect!", skin);
		connectBtn.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				setupNetworking(ipTextField.getText());
			}
		});
		table.add(connectBtn);
		
		table.row().colspan(2);
		final TextButton exitBtn = new TextButton("Exit", skin);
		exitBtn.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				Log.debug("[CLIENT] Client closed.");
				Gdx.app.exit();
			}
		});
		table.add(exitBtn);
	}
	
	private void setupNetworking(String serverIP) {
		//TODO move networking stuff away
		Log.set(Log.LEVEL_DEBUG);
		client = new Client();
		client.start();
		Network.register(client);
		client.addListener(new ClientListener());
		
		try {
			client.connect(10000, serverIP, Network.tcpPort, Network.udpPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
