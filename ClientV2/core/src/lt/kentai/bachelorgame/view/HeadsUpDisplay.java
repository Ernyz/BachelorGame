package lt.kentai.bachelorgame.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
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
//		timerLabel.setText(String.valueOf(MathUtils.round(match.getMatchTimer())));

        stage.act(delta);
        stage.draw();
    }

    private void setupHUD() {
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
//		table = new Table();

//		table.setDebug(true);  //Careful with this one, might cause memory leaks
//
//		table.setFillParent(true);
//		stage.addActor(table);
//
//		table.row().width(Gdx.graphics.getWidth()).height(Gdx.graphics.getHeight() * 0.3f);
//		timerLabel = new Label("0", skin);
//		table.add(timerLabel);
//
//		table.row().width(Gdx.graphics.getWidth()).height(Gdx.graphics.getHeight() * 0.7f);
//		playerStatsTable = new Table(skin);
//		playerStatsTable.background("default-rect");
//		playerStatsTable.setWidth(200);
//		playerStatsTable.setHeight(200);
//		playerStatsTable.row();
//		stat = new Label("STAT", skin);
//		playerStatsTable.add(stat);
//		table.add(playerStatsTable);
        Window window = new Window("Stats", skin);
		window.setHeight(100);
		window.setWidth(100);
        window.setPosition(5, 5);
        window.add(getStatsTable());
        stage.addActor(window);

        Window miniMap = new Window("Minimap", skin);
        miniMap.setWidth(150);
        miniMap.setHeight(100);
        miniMap.setPosition(485,375);
        miniMap.add(getMinimapTable());
        stage.addActor(miniMap);
        Window top = new Window("Info", skin);
        top.setWidth(200);
        top.setHeight(50);
        top.setPosition(220,425);
        top.add(getInfo());
        stage.addActor(top);
        Window skills = new Window("Info", skin);
        skills.setWidth(250);
        skills.setHeight(70);
        skills.setPosition(110,5);
        skills.add(getSkills());
        stage.addActor(skills);
    }

    private Table getMinimapTable(){
        Table table = new Table(skin);
//        table.add(new TextField("minimap",skin));
        return table;
    }
    private Table getInfo(){
        Table table = new Table(skin);
        table.add(new TextField("match info",skin));
        return table;
    }
    private Table getStatsTable() {
        Table table = new Table(skin);
        table.row();
        table.addActor(new Label("Hp", skin));
        table.addActor(new TextField("100", skin));
        return table;
    }
    private Table getSkills() {
        Table table = new Table(skin);
        table.row();
        table.addActor(new Label("1",skin));
        table.addActor(new Label("2",skin));
        table.addActor(new Label("3",skin));
        return table;
    }
}
