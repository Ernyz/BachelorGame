package lt.kentai.bachelorgame;

import java.io.IOException;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;

public class GameClient extends ApplicationAdapter {
	private SpriteBatch batch;
	/* Client stuff */
	private Client client;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		//TODO create screen; move networking stuff away
		/* Initialize client stuff */
		Log.set(Log.LEVEL_DEBUG);
		client = new Client();
		client.start();
		Network.register(client);
		client.addListener(new ClientListener());
		
		try {
			client.connect(10000, "127.0.0.1", Network.tcpPort, Network.udpPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
}
