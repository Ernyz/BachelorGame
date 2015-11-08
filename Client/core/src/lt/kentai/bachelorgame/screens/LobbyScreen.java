package lt.kentai.bachelorgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.esotericsoftware.kryonet.Client;

import lt.kentai.bachelorgame.GameClient;

public class LobbyScreen implements Screen {

	private final int matchId;
	
	private SpriteBatch batch;
	private GameClient mainClass;
	private Client client;
	
	private Table table;
	private Stage stage;
	private Skin skin;
	private Label timerLabel;
	private TextButton lockInBtn;

	private float timer = 5f;

	public LobbyScreen(final int matchId, SpriteBatch batch, GameClient mainClass, Client client) {
		this.matchId = matchId;
		this.batch = batch;
		this.mainClass = mainClass;
		this.client = client;
	}

	@Override
	public void show() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		setupUI();

		timerLabel.setText(MathUtils.floor(timer) + "");
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();

		timer -= delta;
		timerLabel.setText(MathUtils.floor(timer) + "");
		if (timer <= 0) {
			//Start the game
			switchToGameScreen();
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
		table.row();
		skin = new Skin(Gdx.files.internal("ui/uiskin.json"));// temp TODO
		timerLabel = new Label(timer + "", skin);
		table.add(timerLabel);
		table.row();
		final Label textLabel = new Label("Some text", skin);
		table.add(textLabel);
		table.row();
		lockInBtn = new TextButton("Lock in", skin);
		table.add(lockInBtn);
		table.row();

	}

	private void lockIn() {  //TODO: Call this function when lockIn button is pressed
		switchToGameScreen();
	}

	public void switchToGameScreen() {
		ScreenManager.switchToGameScreen(matchId, batch, mainClass, client);
	}
	
}
