/**
 * 
 */
package boats;

/**
 * @author toni
 *
 */
public class Battleship {

	int id;
	String name;
	int length;
	int success_points;
	int dead_points;
	public String state = "healthy";

	public Battleship() {
		super();
		this.id = 2;
		this.name = "Battleship";
		this.length = 4;
		this.success_points = 250;
		this.dead_points = 500;
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
	public void setlength(int length) {
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
