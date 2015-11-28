package lt.kentai.bachelorgame.model;

import com.badlogic.gdx.graphics.Texture;

/**
 * Represents an entity in the game world.
 * 
 * @author Ernyz
 */
public class Entity {
	
	private Texture texture;
	private float x;
	private float y;
	
	//XXX: Temp
	public int connectionId;
	public String championName;
	private float speed;
	
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

	//XXX: Test
	public void moveBy(float delta, float x, float y) {
		this.x += x * delta * speed;
		this.y += y * delta * speed;
	}
	
}
