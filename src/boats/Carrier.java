/**
 * 
 */
package boats;

/**
 * @author toni
 *
 */
public class Carrier {

	int id;
	String name;
	int length;
	int success_points;
	int dead_points;
	public String state = "healthy";

	public Carrier() {
		super();
		this.id = 1;
		this.name = "Carrier";
		this.length = 5;
		this.success_points = 350;
		this.dead_points = 1000;
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

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getlength() {
		return length;
	}

	public void setlength(int length) {
		this.length = length;
	}

	public int getSuccess_points() {
		return success_points;
	}

	public void setSuccess_points(int success_points) {
		this.success_points = success_points;
	}

	public int getDead_points() {
		return dead_points;
	}

	public void setDead_points(int dead_points) {
		this.dead_points = dead_points;
	}

}
