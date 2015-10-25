package lt.kentai.bachelorgame.screens;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import lt.kentai.bachelorgame.Network;
import lt.kentai.bachelorgame.ServerListener;

public class ServerScreen implements Screen {
	
	private SpriteBatch batch;
	private Stage stage;
	private Table table;
	private Skin skin;
	private Label outputLabel;
	private ScrollPane scrollPane;
	private TextField inputTextField;
	// Server stuff
	private Server server;
	
	public ServerScreen(SpriteBatch batch) {
		this.batch = batch;
	}

	@Override
	public void show() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		setupUI();
		
		setupNetworking();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
		
		if(Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			manageConsoleInput();
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
		batch.dispose();
		stage.dispose();
		skin.dispose();
	}

	private void setupUI() {
		table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		table.setDebug(true);  //Careful with this one, might cause memory leaks
		skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
		
		table.row().width(Gdx.graphics.getWidth()).height(Gdx.graphics.getHeight() * 0.88f);
		outputLabel = new Label("Ouput window initialized.", skin);
		outputLabel.setWrap(true);
		scrollPane = new ScrollPane(outputLabel, skin);
		scrollPane.setFadeScrollBars(false);
		scrollPane.setScrollbarsOnTop(true);
		table.add(scrollPane);
		
		table.row().width(Gdx.graphics.getWidth()).height(Gdx.graphics.getHeight() * 0.06f);
		inputTextField = new TextField("", skin);
		table.add(inputTextField);
		
		table.row().height(Gdx.graphics.getHeight() * 0.06f);
		final TextButton exitBtn = new TextButton("Exit", skin);
		table.add(exitBtn);
		exitBtn.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				Log.info("[SERVER] Server closed.");
				Gdx.app.exit();
			}
		});
		
		for(int i=0; i< 50; i++) {
			addMessage("test"+i);
			
		}
	}
	
	public void addMessage(String message) {
		outputLabel.setText(outputLabel.getText()+"\n"+message);
		table.layout();
	}
	
	private void manageConsoleInput() {
		String inputText = inputTextField.getText();
		if(inputText != null && inputText.length() > 0 && inputText != "") {
			addMessage(inputText);
			inputTextField.setText("");
		}
	}
	
	private void setupNetworking() {
		// TODO move networking stuff away
		Log.set(Log.LEVEL_NONE);
		server = new Server() {
			protected Connection newConnection() {
				return new GameConnection();
			}
		};
		Network.register(server);
		
		server.addListener(new ServerListener());
		try {
			server.bind(Network.tcpPort, Network.udpPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
		server.start();
	}
	
	public static class GameConnection extends Connection {
		public String name;
	}
	
}
