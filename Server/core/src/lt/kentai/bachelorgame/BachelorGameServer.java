package lt.kentai.bachelorgame;

import java.io.IOException;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

public class BachelorGameServer extends ApplicationAdapter {
	SpriteBatch batch;
	/* Server stuff */
	private Server server;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		/* Server stuff */
		Log.set(Log.LEVEL_DEBUG);
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

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	private static class GameConnection extends Connection {
		public String name;
	}
}
