package lt.kentai.bachelorgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.esotericsoftware.kryonet.Client;
import lt.kentai.bachelorgame.GameClientV2;

public class RegistrationScreen implements Screen {

    private Stage stage;
    private Table table;
    private Skin skin;
    private Client client;


    public RegistrationScreen() {
        this.client = GameClientV2.getNetworkingManager().getClient();
    }

    @Override
    public void show() {

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        setupUI();
    }

    private void setupUI() {
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        table.setDebug(false);  //Careful with this one, might cause memory leaks
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        table.row();
        final Label enterUsernameLabel = new Label("Username:", skin);
        table.add(enterUsernameLabel);
        final TextField usernameTextField = new TextField("", skin);
        table.add(usernameTextField);

        table.row();
        final Label pswLbl = new Label("Password:", skin);
        table.add(pswLbl);
        final TextField pswTF = new TextField("", skin);
        table.add(pswTF);

        table.row();
        final Label emailLbl = new Label("Email:", skin);
        table.add(emailLbl);
        final TextField emailTF = new TextField("", skin);
        table.add(emailTF);
        table.row();
        TextButton register = new TextButton("Register", skin);
        register.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                //TODO register
            }
        });
        table.add(register);
        TextButton back = new TextButton("Back", skin);
        back.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                GameClientV2.getScreenManager().switchToLoginScreen();

            }
        });
        table.add(back);

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
}
