package com.map.testing.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import javafx.scene.control.Skin;
import javafx.scene.control.TextField;
import lt.kentai.bachelorgame.generators.test.MapTile;
import map.StandardMapGenerator;

public class SettingsScreen implements Screen {
    public StandardMapGenerator mapGen;
    Stage stage;
    Skin skin;
    Table table;
    TextButton generate;
    private TextField seedTF, largestFutureTF, persistanceTF;
    private SpriteBatch batch;
    private OrthographicCamera cam;
    private Array<MapTile> mapTiles;
    private boolean changeMap = false;

    private float rotationSpeed;
    public SettingsScreen() {

        rotationSpeed = 2f;
        batch = new SpriteBatch();
//        cam = new OrthographicCamera(Gdx.graphics.getWidth() * 2f, Gdx.graphics.getHeight() * 2f);
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        cam = new OrthographicCamera(30, 30 * (h / w));
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);



//        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
//        cam.setToOrtho(false);
        cam.update();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport(), batch);
        Gdx.input.setInputProcessor(stage);
        setupUi();
//        generateMap(300, 100, 1, 0.3, 300);
//        DRAW();
    }

    private void setupUi() {
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        table = new Table();
        table.setPosition(Gdx.graphics.getWidth() * 0.9f, Gdx.graphics.getHeight() * 0.7f);
        stage.addActor(table);

        table.row();
        Label seedL = new Label("Seed", skin);
        table.add(seedL);
        table.row();
        seedTF = new TextField("100", skin);
        table.add(seedTF);

        table.row();
        Label lfT = new Label("Largest Future", skin);
        table.add(lfT);
        table.row();
        largestFutureTF = new TextField("300", skin);
        table.add(largestFutureTF);

        table.row();
        Label persistanceL = new Label("Persistance", skin);
        table.add(persistanceL);
        table.row();
        persistanceTF = new TextField("0.3", skin);
        table.add(persistanceTF);


        generate = new TextButton("Generate map ", skin);
        generate.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //generateMap
                generateMap(300, 100, gV(seedTF), gVd(persistanceTF), gV(largestFutureTF));
            }
        });
        table.row();
        table.add(generate);

    }

    private int gV(TextField tf) {
        int res = Integer.parseInt(tf.getText());

        return res;
    }

    private double gVd(TextField tf) {
        return Double.parseDouble(tf.getText());

    }

    @Override
    public void render(float delta) {

        handleInput();
        cam.update();
        batch.setProjectionMatrix(cam.combined);

        DRAW();
        stage.act(delta);
        stage.draw();
    }

    public void DRAW() {

        if (changeMap) {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.setProjectionMatrix(cam.combined);
            batch.begin();

            for (int i = 0; i < mapTiles.size; i++) {
                batch.draw(mapTiles.get(i).texture, mapTiles.get(i).x, mapTiles.get(i).y);
            }
//            changeMap = false;

            batch.end();
            // pause();

        }

    }

    private void generateMap(int width, int height, int seed, double persistance, int largestFuture) {
        mapGen = new StandardMapGenerator(width, height, seed, persistance, largestFuture);
        char[][] map = mapGen.generateMap();
        Array<MapTile> tiles = new Array<MapTile>();

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == '#') {
                    tiles.add(new MapTile(new Texture("tiles/wall.png"), j * 1, i * 1));

                } else if (map[i][j] == 'R') {
                    tiles.add(new MapTile(new Texture("tiles/road.png"), j * 1, i * 1));

                } else if (map[i][j] == ' ') {
                    tiles.add(new MapTile(new Texture("tiles/grass.png"), j * 1, i * 1));

                }
            }
        }

        mapTiles = tiles;
        changeMap = true;
    }


    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            cam.zoom += 0.1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            cam.zoom -= 0.1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            cam.translate(-3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            cam.translate(3, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            cam.translate(0, -3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            cam.translate(0, 3, 0);
        }

        cam.zoom = MathUtils.clamp(cam.zoom, 0.1f, 100 / cam.viewportWidth);

        float effectiveViewportWidth = cam.viewportWidth * cam.zoom;
        float effectiveViewportHeight = cam.viewportHeight * cam.zoom;

        cam.position.x = MathUtils.clamp(cam.position.x, effectiveViewportWidth / 2f, 300 - effectiveViewportWidth / 2f);
        cam.position.y = MathUtils.clamp(cam.position.y, effectiveViewportHeight / 2f, 300 - effectiveViewportHeight / 2f);
        System.out.println("x "+ cam.position.x);
        System.out.println("y "+ cam.position.y);
    }

    @Override
    public void resize(int width, int height) {
        cam.viewportWidth = 30f;
        cam.viewportHeight = 30f * height/width;
        cam.update();
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
}