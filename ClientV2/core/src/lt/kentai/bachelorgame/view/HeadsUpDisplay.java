package lt.kentai.bachelorgame.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import lt.kentai.bachelorgame.Match;

public class HeadsUpDisplay {
	
	private Match match;
	private SpriteBatch batch;  //TODO: Dispose, check if it is even needed
	
	private Stage stage;
	private Table table;
	private Skin skin;
	
	//HUD elements
	private Label timerLabel;  //XXX: rename?..
	private Table playerStatsTable;
	private Label stat;
	
	public HeadsUpDisplay(Match match, SpriteBatch batch) {
		this.match = match;
		this.batch = batch;
		
		setupHUD();
	}
	
	public void updateAndRender(float delta) {
		timerLabel.setText(String.valueOf(MathUtils.round(match.getMatchTimer())));
		
		stage.act(delta);
		stage.draw();
	}
	
	private void setupHUD() {
		stage = new Stage();
		skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
		table = new Table();
		table.setDebug(true);  //Careful with this one, might cause memory leaks
		
		table.setFillParent(true);
		stage.addActor(table);
		
		table.row().width(Gdx.graphics.getWidth()).height(Gdx.graphics.getHeight() * 0.3f);
		timerLabel = new Label("0", skin);
		table.add(timerLabel);
		
		table.row().width(Gdx.graphics.getWidth()).height(Gdx.graphics.getHeight() * 0.7f);
		playerStatsTable = new Table(skin);
		playerStatsTable.background("default-rect");
		playerStatsTable.setWidth(200);
		playerStatsTable.setHeight(200);
		playerStatsTable.row();
		stat = new Label("STAT", skin);
		playerStatsTable.add(stat);
		table.add(playerStatsTable);
	}
}
