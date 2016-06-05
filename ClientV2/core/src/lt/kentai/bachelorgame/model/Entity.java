package lt.kentai.bachelorgame.model;

import org.omg.Messaging.SyncScopeHelper;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import lt.kentai.bachelorgame.Properties;

/**
 * Represents an entity in the game world.
 * 
 * @author Ernyz
 */
public class Entity {
	
	private Texture texture;
	private float x;
	private float y;
	
	private float targetX = 0;
	private float targetY = 0;
	public float dx = 0;
	public float dy = 0;
	
	public int connectionId;  //XXX: Connection ID? Minions won't need that...
	public String championName;
	private float speed;
	private Vector2 velocity = new Vector2();
	
	public Entity(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void recalculateTargetPositions(float tx, float ty) {
		x = targetX;
		y = targetY;
		targetX = tx;
		targetY = ty;
		dx = (targetX-x)/4;  //60FPS / 10 updates per second
		dy = (targetY-y)/4;
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

	public float getTargetX() {
		return targetX;
	}

	public void setTargetX(float targetX) {
		this.targetX = targetX;
	}

	/*public float getTargetY() {
		return targetY;
	}

	public void setTargetY(float targetY) {
		this.targetY = targetY;
	}*/
	
}
