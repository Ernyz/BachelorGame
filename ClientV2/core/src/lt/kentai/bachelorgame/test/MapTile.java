package lt.kentai.bachelorgame.test;

import com.badlogic.gdx.graphics.Texture;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MapTile {
    public Texture texture;
    public float x,y;

    public MapTile(Texture texture, float x, float y) {
        this.texture = texture;
        this.x = x;
        this.y = y;
    }

}
