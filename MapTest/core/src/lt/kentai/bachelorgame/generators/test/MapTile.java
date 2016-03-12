package lt.kentai.bachelorgame.generators.test;

        import com.badlogic.gdx.graphics.Texture;

public class MapTile {
    public Texture texture;
    public float x,y;

    public MapTile(Texture texture, float x, float y) {
        this.texture = texture;
        this.x = x;
        this.y = y;
    }

}
