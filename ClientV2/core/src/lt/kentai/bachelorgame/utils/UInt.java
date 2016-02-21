package lt.kentai.bachelorgame.utils;

/**
 * Class representing positive integer type.
 * 
 * @author ernyz
 */
public class UInt {

	private int value = 0;
	
	public UInt() {
	}
	
	public UInt(int value) {
		this.value = value;
	}
	
	public int increment() {
		value++;
		if(value < 0) {
			value = 0;
		}
		return value;
	}
	
	public int decrement() {
		value--;
		if(value < 0) {
			value = Integer.MAX_VALUE;
		}
		return value;
	}
	
	/*TODO: finish implementation
	public int add(int x) {
		return value;
	}
	
	public int substract(int x) {
		return value;
	}*/

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		try {
			if(value < 0) {
				throw new Exception("Can not set negative value for UInt. Tried to set to: " + value);
			} else {
				this.value = value;
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
}
