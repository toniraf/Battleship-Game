/**
 * 
 */
package boats;

/**
 * @author toni
 *
 */
public class Submarine {

	int id;
	String name;
	int length;
	int success_points;
	int dead_points;
	public String state = "healthy";

	public Submarine() {
		super();
		this.id = 4;
		this.name = "Submarine";
		this.length = 3;
		this.success_points = 100;
		this.dead_points = 0;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	public void hit() {
		this.length = this.length - 1;

	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the length
	 */
	public int getlength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * @return the success_points
	 */
	public int getSuccess_points() {
		return success_points;
	}

	/**
	 * @param success_points the success_points to set
	 */
	public void setSuccess_points(int success_points) {
		this.success_points = success_points;
	}

	/**
	 * @return the dead_points
	 */
	public int getDead_points() {
		return dead_points;
	}

	/**
	 * @param dead_points the dead_points to set
	 */
	public void setDead_points(int dead_points) {
		this.dead_points = dead_points;
	}

}
