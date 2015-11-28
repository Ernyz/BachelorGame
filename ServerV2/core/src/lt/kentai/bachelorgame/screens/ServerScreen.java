package lt.kentai.bachelorgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import lt.kentai.bachelorgame.AccountConnection;
import lt.kentai.bachelorgame.Match;
import lt.kentai.bachelorgame.Matchmaker;

public class ServerScreen implements Screen {
	
	//GUI stuff
	private Stage stage;
	private Table table;
	private Skin skin;
	private Label outputLabel;
	private ScrollPane scrollPane;
	private TextField inputTextField;
	
	//Server stuff
	private Server server;
	private Matchmaker matchmaker;
	private Array<Match> matchArray = new Array<Match>();
	
	public ServerScreen() {
		matchmaker = new Matchmaker(matchArray);
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
		stage.dispose();
		skin.dispose();
	}
	
	private void setupUI() {
		table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		table.setDebug(false);  //Careful with this one, might cause memory leaks
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
	}
	
	public void addMessage(String message) {
		outputLabel.setText(outputLabel.getText()+"\n"+message);
		scrollPane.layout();
		scrollPane.setScrollPercentY(100);
	}
	
	private void manageConsoleInput() {
		String inputText = inputTextField.getText();
		if(inputText == null || inputText.length() <= 0 || inputText == "") {
			return;
		}
		addMessage(inputText);
		inputTextField.setText("");
		
		//TODO: Make class which deals with command execution.
		if(inputText.equals("exit")) {
			server.stop();
			Gdx.app.exit();
		} else if(inputText.equals("lc")) {
			if(server.getConnections().length > 0) {
				for(Connection c : server.getConnections()) {
					addMessage(c.toString() + "; Player name: " + ((AccountConnection)c).connectionName);
				}
			} else {
				addMessage("There are no connections at the moment.");
			}
		} else {
			addMessage("Command not found: " + inputText + ".");
		}
	}

	public Matchmaker getMatchmaker() {
		return matchmaker;
	}
	
}
