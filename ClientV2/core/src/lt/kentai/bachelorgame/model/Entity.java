package lt.kentai.bachelorgame.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Represents an entity in the game world.
 * 
 * @author Ernyz
 */
public class Entity {
	
	private Texture texture;
	private float x;
	private float y;
	
	public int connectionId;  //XXX: Connection ID? Minions won't need that...
	public String championName;
	private float speed;
	private Vector2 velocity = new Vector2();
	
	public Entity(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
	
}
