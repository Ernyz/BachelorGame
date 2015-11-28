package lt.kentai.bachelorgame.screens;

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

import lt.kentai.bachelorgame.GameClientV2;
import lt.kentai.bachelorgame.Network.LoginRequest;

public class LoginScreen implements Screen {
	
	private Stage stage;
	private Table table;
	private Skin skin;
	// Client stuff
	private Client client;

	public LoginScreen() {
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
		
		table.row().colspan(2);
		final TextButton connectBtn = new TextButton("Connect!", skin);
		connectBtn.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				//Try to connect
				LoginRequest login = new LoginRequest();
				login.username = usernameTextField.getText();
				client.sendTCP(login);
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

}