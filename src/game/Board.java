/**
 * 
 */
package game;

import java.util.ArrayList;
import java.util.List;

import boats.*;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

/**
 * @author toni
 *
 */
public class Board extends Parent {

	private VBox rows = new VBox();
	int[][] board = new int[10][10];
	private int player_id;
	private int success_points, dead_points;
	private ArrayList<Object> ListOfShips;
	public int active_ships = 5;
	public double num_of_shoots;
	public double success_shoots;

	List<Pair<Integer, Integer>> axis_list = new ArrayList<Pair<Integer, Integer>>();
	List<Pair<String, String>> status_list = new ArrayList<Pair<String, String>>();

	/**
	 * @param board
	 */
	/* constructor of class Board */
	public Board(int id) {
		super();
		// this.board = board;
		success_points = 0;
		player_id = id;
		ListOfShips = new ArrayList<Object>();
		for (int i = 0; i < board.length; i++) {
			HBox row = new HBox();
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = 0;
				Cell c = new Cell(i, j, this);
				row.getChildren().add(c);
			}
			rows.getChildren().add(row);
		}
		getChildren().add(rows);
	}

	/**
	 * @return the axis_list
	 */
	public List<Pair<Integer, Integer>> getAxis_list() {
		return axis_list;
	}

	/**
	 * @param axis_list the axis_list to set
	 */
	public void setAxis_list(List<Pair<Integer, Integer>> axis_list) {
		this.axis_list = axis_list;
	}

	/**
	 * @return the status_list
	 */
	public List<Pair<String, String>> getStatus_list() {
		return status_list;
	}

	/**
	 * @param status_list the status_list to set
	 */
	public void setStatus_list(List<Pair<String, String>> status_list) {
		this.status_list = status_list;
	}

	/**
	 * @return the board
	 */
	public int[][] getBoard() {
		return board;
	}

	/**
	 * @param board the board to set
	 */
	public void setBoard(int[][] board) {
		this.board = board;
	}

	public void PlaceBoard(int i, int j, int price) {
		this.board[i][j] = price;
	}

	public int GetCell(int i, int j) {

		return this.board[i][j];
	}

	/*------------------------------------------------------------------------------------------*/
	/*
	 * this method initializes all important variables and displays Gui as first,
	 * when the game begun
	 */
	public Board RestartGui(Board whoseboard, int id) {
		if (id == 1 || id == 0) {
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++) {
					Cell cell = GCell(i, j);
					cell.setFill(Color.LIGHTGRAY);
					cell.setStroke(Color.BLACK);

				}
			}
		}

		if (id == 0) {

			whoseboard = Typose(whoseboard);
		}

		success_points = 0;
		dead_points = 0;
		active_ships = 5;
		num_of_shoots = 0;
		success_shoots = 0;

		ResetShips();
		return whoseboard;
	}

	/*------------------------------------------------------------------------------------------*/
	/* this method reset all ships on the initial situation, before they were hit */
	public void ResetShips() {
		Carrier carrier = new Carrier();
		carrier = (Carrier) getListOfShips().get(0);

		Battleship battleship = new Battleship();
		battleship = (Battleship) getListOfShips().get(1);

		Cruiser cruiser = new Cruiser();
		cruiser = (Cruiser) getListOfShips().get(2);

		Submarine submarine = new Submarine();
		submarine = (Submarine) getListOfShips().get(3);

		Destroyer destroyer = new Destroyer();
		destroyer = (Destroyer) getListOfShips().get(4);

		carrier.setState("healthy");
		carrier.setlength(5);

		battleship.setState("healthy");
		battleship.setlength(4);

		cruiser.setState("healthy");
		cruiser.setLength(3);

		submarine.setState("healthy");
		submarine.setLength(3);

		destroyer.setState("healthy");
		destroyer.setLength(2);
		return;
	}

	/*------------------------------------------------------------------------------------------*/
	/*
	 * this method is called when a player wants to hit enemy on a specific position
	 * (x,y)
	 */
	public int shoot(int x, int y) {
		Cell cell = GCell(x, y);
		num_of_shoots = num_of_shoots + 1;

		if (board[x][y] != 0) {
			success_shoots = success_shoots + 1;
			cell.setFill(Color.RED);
			cell.setStroke(Color.GREEN);

			// if (x,y) is a carrier's position
			if (board[x][y] == 1) {
				Carrier carrier = new Carrier();
				carrier = (Carrier) getListOfShips().get(board[x][y] - 1);
				carrier.hit();
				carrier.setState("hit");
				if (carrier.getlength() == 0) {
					dead_points = carrier.getDead_points();
					System.out.println("Carrier is shunk !!");
					carrier.setState("sunk");
					active_ships = active_ships - 1;
					System.out.println("active ships is" + active_ships);
				} else {
					dead_points = 0;
				}
				success_points = dead_points + carrier.getSuccess_points();

				Pair<String, String> s = new Pair<String, String>(carrier.getState(), carrier.getName());
				status_list.add(s);
			}

			if (board[x][y] == 2) {
				Battleship battleship = new Battleship();
				battleship = (Battleship) getListOfShips().get(board[x][y] - 1);
				battleship.hit();
				battleship.setState("hit");
				if (battleship.getlength() == 0) {
					dead_points = battleship.getDead_points();
					System.out.println("Battleship is shunk !!");
					battleship.setState("sunk");
					active_ships = active_ships - 1;
				} else {
					dead_points = 0;
				}

				success_points = dead_points + battleship.getSuccess_points();
				Pair<String, String> s = new Pair<String, String>(battleship.getState(), battleship.getName());
				status_list.add(s);
			}

			if (board[x][y] == 3) {
				Cruiser cruiser = new Cruiser();
				cruiser = (Cruiser) getListOfShips().get(board[x][y] - 1);
				cruiser.hit();
				cruiser.setState("hit");
				if (cruiser.getlength() == 0) {
					dead_points = cruiser.getDead_points();
					System.out.println("Cruiser is shunk !!");
					cruiser.setState("sunk");
					active_ships = active_ships - 1;
				} else {
					dead_points = 0;
				}

				success_points = dead_points + cruiser.getSuccess_points();
				Pair<String, String> s = new Pair<String, String>(cruiser.getState(), cruiser.getName());
				status_list.add(s);
			}

			if (board[x][y] == 4) {
				Submarine submarine = new Submarine();
				submarine = (Submarine) getListOfShips().get(board[x][y] - 1);
				submarine.hit();
				submarine.setState("hit");
				if (submarine.getlength() == 0) {
					dead_points = submarine.getDead_points();
					System.out.println("Submarine is shunk !!");
					submarine.setState("sunk");
					active_ships = active_ships - 1;
				} else {
					dead_points = 0;
				}
				success_points = dead_points + submarine.getSuccess_points();
				Pair<String, String> s = new Pair<String, String>(submarine.getState(), submarine.getName());
				status_list.add(s);
			}

			if (board[x][y] == 5) {
				Destroyer destroyer = new Destroyer();
				destroyer = (Destroyer) getListOfShips().get(board[x][y] - 1);
				destroyer.hit();
				destroyer.setState("hit");
				if (destroyer.getlength() == 0) {
					dead_points = destroyer.getDead_points();
					System.out.println("Destroyer is shunk !!");
					destroyer.setState("sunk");
					active_ships = active_ships - 1;
				} else {
					dead_points = 0;
				}
				success_points = dead_points + destroyer.getSuccess_points();
				Pair<String, String> s = new Pair<String, String>(destroyer.getState(), destroyer.getName());
				status_list.add(s);
			}

		} else {
			success_points = 0;
			cell.setFill(Color.AQUA);
			cell.setStroke(Color.GREEN);
			Pair<String, String> s = new Pair<String, String>("missed shot", "");
			status_list.add(s);
		}

		Pair<Integer, Integer> p = new Pair<Integer, Integer>(x, y);
		axis_list.add(p);

		return success_points;
	}

	/*------------------------------------------------------------------------------------------*/
	/* this method displays the ships of player on board pane */
	public Board Typose(Board player_board) {

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				Cell cell = GCell(i, j);
				if (board[i][j] != 0) {
					cell.setFill(Color.PURPLE);
					cell.setStroke(Color.GREEN);
				}
			}
		}

		return player_board;
	}

	public Cell GCell(int x, int y) {
		return (Cell) ((HBox) rows.getChildren().get(x)).getChildren().get(y);
	}

	public class Cell extends Rectangle {
		public int x, y;

		public Board board;

		public Cell(int x, int y, Board board) {
			super(30, 30);
			this.x = x;
			this.y = y;
			this.board = board;
			setFill(Color.LIGHTGRAY);
			setStroke(Color.BLACK);
		}

	}

	/**
	 * @return the player_id
	 */
	public int getPlayer_id() {
		return player_id;
	}

	/**
	 * @param player_id the player_id to set
	 */
	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
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

	public ArrayList<Object> getListOfShips() {
		return ListOfShips;
	}

	public void setListOfShips(ArrayList<Object> listOfShips) {
		ListOfShips = listOfShips;
	}

}