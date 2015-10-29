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
import com.esotericsoftware.kryonet.Listener.ThreadedListener;
import com.esotericsoftware.minlog.Log;

import lt.kentai.bachelorgame.ClientListener;
import lt.kentai.bachelorgame.GameClient;
import lt.kentai.bachelorgame.Network;
import lt.kentai.bachelorgame.Network.LoginRequest;

public class LoginScreen implements Screen {
	
	private SpriteBatch batch;
	private GameClient mainClass;
	private Stage stage;
	private Table table;
	private Skin skin;
	private boolean switchToMainMenu = false;
	// Client stuff
	private Client client;

	public LoginScreen(SpriteBatch batch, GameClient mainClass) {
		this.batch = batch;
		this.mainClass = mainClass;
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
		
		stage.act(delta);
		stage.draw();
		
		if(switchToMainMenu) {
			switchToMainMenu = false;
			mainClass.setScreen(new MainMenuScreen(batch, mainClass, client));
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
		dispose();
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
		table.setDebug(false);  //Careful with this one, might cause memory leaks
		skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
		
		table.row();
		final Label enterUsernameLaber = new Label("Enter username:", skin);
		table.add(enterUsernameLaber);
		final TextField usernameTextField = new TextField("Player", skin);
		table.add(usernameTextField);
		
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
				if(setupNetworking(ipTextField.getText())) {
					//Try to connect
					LoginRequest login = new LoginRequest();
					login.username = usernameTextField.getText();
					client.sendTCP(login);
				} else {
					//Handle connection error
				}
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
				client.close();
				Gdx.app.exit();
			}
		});
		table.add(exitBtn);
	}
	
	/**
	 * @return true if connection was successful, false otherwise. */
	private boolean setupNetworking(String serverIP) {
		Log.set(Log.LEVEL_DEBUG);
		client = new Client();
		client.start();
		Network.register(client);
		client.addListener(new ThreadedListener(new ClientListener(this)));
		
		try {
			client.connect(10000, serverIP, Network.tcpPort, Network.udpPort);
			return true;
		} catch (IOException e) {
			//e.printStackTrace();
			Log.error(e.getMessage());
			return false;
		}
	}
	
	public void switchToMainMenuScreen() {
		switchToMainMenu = true;
	}

}
