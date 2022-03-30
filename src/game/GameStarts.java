package game;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;
import boats.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
//import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

public class GameStarts extends Application {

	// variables for check if a ship exists only one time!!
	int unique1 = 0, unique2 = 0, unique3 = 0, unique4 = 0, unique5 = 0;

	public int x = 0, y = 0, xpc = 0, ypc = 0;
	public int playertimes = 0;
	public int turn;
	public int pctimes = 0;

	Board player_board;
	Board enemy_board;

	public int player_success_points = 0;
	public int enemy_success_points = 0;

	public int count = 0;
	boolean random = true;

	List<Pair<Integer, Integer>> list = new ArrayList<Pair<Integer, Integer>>();

	private Parent createContent() {

		BorderPane root = new BorderPane();
		root.setPrefSize(1200, 800);

		Menu option1 = new Menu("Application ");
		Menu option2 = new Menu("Details ");

		MenuItem item1 = new MenuItem("Start");
		MenuItem item2 = new MenuItem("Load");
		MenuItem item3 = new MenuItem("Exit");
		MenuItem item4 = new MenuItem("Enemy Ships");
		MenuItem item5 = new MenuItem("Player Shots");
		MenuItem item6 = new MenuItem("Enemy Shots");

		option1.getItems().addAll(item1, item2, item3);
		option2.getItems().addAll(item4, item5, item6);

		// Add Menu-Bar. Option1 for "Application" Button , Option2 for "Details"
		MenuBar menuBar = new MenuBar(option1, option2);

		item3.setOnAction(e -> {
			System.exit(0);
		});

		item4.setOnAction(e -> {
			Carrier newc = (Carrier) enemy_board.getListOfShips().get(0);
			Battleship newb = (Battleship) enemy_board.getListOfShips().get(1);
			Cruiser newcr = (Cruiser) enemy_board.getListOfShips().get(2);
			Submarine newsub = (Submarine) enemy_board.getListOfShips().get(3);
			Destroyer newd = (Destroyer) enemy_board.getListOfShips().get(4);

			ExceptionBox.BeforeStart("Enemy Ships",
					"Carrier : " + newc.getState() + "\n" + "Battleship : " + newb.getState() + "\n" + "Cruiser : "
							+ newcr.getState() + "\n" + "Submarine : " + newsub.getState() + "\n" + "Destroyer : "
							+ newd.getState());
		});

		// gridpane for player - informations
		GridPane grid1 = new GridPane();
		grid1.setPadding(new Insets(10, 10, 10, 10));
		grid1.setVgap(8);
		grid1.setHgap(15);
		grid1.setStyle("-fx-background-color: #afeced;");

		Label l1 = new Label("Total score of Player =");
		l1.setFont(new Font(15));
		GridPane.setConstraints(l1, 0, 0);

		Label var1player = new Label();
		var1player.setFont(new Font(15));
		GridPane.setConstraints(var1player, 1, 0);

		Label l2 = new Label("Rate of success shots =");
		l2.setFont(new Font(15));
		GridPane.setConstraints(l2, 0, 1);

		Label var2player = new Label();
		var2player.setFont(new Font(15));
		GridPane.setConstraints(var2player, 1, 1);

		Label l3 = new Label("Active Ships of Player =");
		l3.setFont(new Font(15));
		GridPane.setConstraints(l3, 0, 2);

		Label var3player = new Label();
		var3player.setFont(new Font(15));
		GridPane.setConstraints(var3player, 1, 2);

		grid1.getChildren().addAll(l1, var1player, l2, var2player, l3, var3player);

		// gridpane for enemy's information
		GridPane grid2 = new GridPane();
		grid2.setPadding(new Insets(10, 10, 10, 10));
		grid2.setVgap(8);
		grid2.setHgap(15);
		grid2.setStyle("-fx-background-color: #afeced;");

		Label e1 = new Label("Total score of Computer =");
		e1.setFont(new Font(15));
		GridPane.setConstraints(e1, 0, 0);

		Label var1pc = new Label();
		var1pc.setFont(new Font(15));
		GridPane.setConstraints(var1pc, 1, 0);

		Label e2 = new Label("Rate of success shots =");
		e2.setFont(new Font(15));
		GridPane.setConstraints(e2, 0, 1);

		Label var2pc = new Label();
		var2pc.setFont(new Font(15));
		GridPane.setConstraints(var2pc, 1, 1);

		Label e3 = new Label("Active Ships of Computer =");
		e3.setFont(new Font(15));
		GridPane.setConstraints(e3, 0, 2);

		Label var3pc = new Label();
		var3pc.setFont(new Font(15));
		GridPane.setConstraints(var3pc, 1, 2);

		grid2.getChildren().addAll(e1, var1pc, e2, var2pc, e3, var3pc);

		// top box of stage scene
		HBox hbox = new HBox(menuBar, grid1, grid2);
		hbox.setPadding(new Insets(40, 12, 40, 12));
		hbox.setSpacing(100);
		hbox.setStyle("-fx-background-color: #ed9f51;");
		hbox.setAlignment(Pos.BASELINE_LEFT);
		root.setTop(hbox);

		// create an object of class Board for player
		// id of player is zero.
		player_board = new Board(0);
		File file1 = new File("/Users/toni/Desktop/Eclipse-Medialab/MyGame/player_default.txt");

		try {
			player_board = inputSource(player_board, file1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		player_board = player_board.Typose(player_board);

		// create an object of class Board for enemy-pc
		// id of enemy is one.
		enemy_board = new Board(1);
		File file2 = new File("/Users/toni/Desktop/Eclipse-Medialab/MyGame/enemy_default.txt");

		try {
			enemy_board = inputSource(enemy_board, file2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HBox box = new HBox(25, player_board, enemy_board);
		box.setAlignment(Pos.CENTER);
		root.setCenter(box);

		/* whose turn is first to play */
		/*---------------------------------------------------------------------------*/
		turn = new Random().nextInt(2);

		// Display message- whose turn is it .
		if (turn == 0) {
			ExceptionBox.BeforeStart("Game is starting", "Player is your turn");

		} else {
			ExceptionBox.BeforeStart("Game is starting", "Computer is playing now");

		}
		/*---------------------------------------------------------------------------*/

		/*--------------------------------------------------------------------------------------------------------*/
		/* initialize boxes on GUI */
		var1player.setText("" + player_success_points);
		var1player.setText("" + player_success_points);
		var2player.setText("" + Math.round((enemy_board.success_shoots / enemy_board.num_of_shoots) * 100) + "%");
		var1pc.setText("" + enemy_success_points);
		var2pc.setText("" + Math.round((player_board.success_shoots / player_board.num_of_shoots) * 100) + "%");
		var3pc.setText("" + enemy_board.active_ships);
		var3player.setText("" + player_board.active_ships);

		/*--------------------------------------------------------------------------------------------------------*/
		// user input interface for x,y -shooting
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(8);
		grid.setHgap(10);
		grid.setStyle("-fx-background-color: #ed9f51;");

		// X - row label
		Label label1 = new Label("Choose x between 0-9:");
		GridPane.setConstraints(label1, 0, 0);

		// X - row input
		TextField xinput = new TextField();
		xinput.setPromptText("row");
		GridPane.setConstraints(xinput, 1, 0);

		// Y - column label
		Label label2 = new Label("Choose y between 0-9:");
		GridPane.setConstraints(label2, 0, 1);

		// Y - column input
		TextField yinput = new TextField();
		yinput.setPromptText("column");
		GridPane.setConstraints(yinput, 1, 1);

		Button submit = new Button("Go");
		GridPane.setConstraints(submit, 1, 2);

		grid.getChildren().addAll(label1, xinput, label2, yinput, submit);
		grid.setAlignment(Pos.CENTER);
		root.setBottom(grid);
		/*---------------------------------------------------------------------------*/

		/*----------------------------------------------------------------------------------------------------------*/
		/* When button "Enemy Shots " is pressed */
		item6.setOnAction(e -> {

			if (pctimes >= 5) {
				ExceptionBox.BeforeStart("Last five Enemyshots",
						"1st : " + "[" + player_board.getAxis_list().get(pctimes - 5).getKey() + ","
								+ player_board.getAxis_list().get(pctimes - 5).getValue() + "]"
								+ player_board.getStatus_list().get(pctimes - 5).getKey() + ","
								+ player_board.getStatus_list().get(pctimes - 5).getValue() + "\n" + "2nd : " + "["
								+ player_board.getAxis_list().get(pctimes - 4).getKey() + ","
								+ player_board.getAxis_list().get(pctimes - 4).getValue() + "]" + ","
								+ player_board.getStatus_list().get(pctimes - 4).getKey() + ","
								+ player_board.getStatus_list().get(pctimes - 4).getValue() + "\n" + "3rd : " + "["
								+ player_board.getAxis_list().get(pctimes - 3).getKey() + ","
								+ player_board.getAxis_list().get(pctimes - 3).getValue() + "]" + ","
								+ player_board.getStatus_list().get(pctimes - 3).getKey() + ","
								+ player_board.getStatus_list().get(pctimes - 3).getValue() + "\n" + "4rth : " + "["
								+ player_board.getAxis_list().get(pctimes - 2).getKey() + ","
								+ player_board.getAxis_list().get(pctimes - 2).getValue() + "]" + ","
								+ player_board.getStatus_list().get(pctimes - 2).getKey() + ","
								+ player_board.getStatus_list().get(pctimes - 2).getValue() + "\n" + "5th : " + "["
								+ player_board.getAxis_list().get(pctimes - 1).getKey() + ","
								+ player_board.getAxis_list().get(pctimes - 1).getValue() + "]" + ","
								+ player_board.getStatus_list().get(pctimes - 1).getKey() + ","
								+ player_board.getStatus_list().get(pctimes - 1).getValue());
			}

			// if player has played under 5 times ,display last shot

			else {
				ExceptionBox.BeforeStart("Last shot under five is",
						pctimes + " : " + "[" + player_board.getAxis_list().get(pctimes - 1).getKey() + ","
								+ player_board.getAxis_list().get(pctimes - 1).getValue() + "]" + ","
								+ player_board.getStatus_list().get(pctimes - 1).getKey() + ","
								+ player_board.getStatus_list().get(pctimes - 1).getValue());
			}
		});
		/*----------------------------------------------------------------------------------------------------------*/

		/*----------------------------------------------------------------------------------------------------------*/
		/* When button "Player shots " is pressed */
		item5.setOnAction(e -> {

			if (playertimes >= 5) {
				ExceptionBox.BeforeStart("Last five Playershots",
						"1st : " + "[" + enemy_board.getAxis_list().get(playertimes - 5).getKey() + ","
								+ enemy_board.getAxis_list().get(playertimes - 5).getValue() + "]" + ","
								+ enemy_board.getStatus_list().get(playertimes - 5).getKey() + ","
								+ enemy_board.getStatus_list().get(playertimes - 5).getValue() + "\n" + "2nd : " + "["
								+ enemy_board.getAxis_list().get(playertimes - 4).getKey() + ","
								+ enemy_board.getAxis_list().get(playertimes - 4).getValue() + "]" + ","
								+ enemy_board.getStatus_list().get(playertimes - 4).getKey() + ","
								+ enemy_board.getStatus_list().get(playertimes - 4).getValue() + "\n" + "3rd : " + "["
								+ enemy_board.getAxis_list().get(playertimes - 3).getKey() + ","
								+ enemy_board.getAxis_list().get(playertimes - 3).getValue() + "]" + ","
								+ enemy_board.getStatus_list().get(playertimes - 3).getKey() + ","
								+ enemy_board.getStatus_list().get(playertimes - 3).getValue() + "\n" + "4rth : " + "["
								+ enemy_board.getAxis_list().get(playertimes - 2).getKey() + ","
								+ enemy_board.getAxis_list().get(playertimes - 2).getValue() + "]" + ","
								+ enemy_board.getStatus_list().get(playertimes - 2).getKey() + ","
								+ enemy_board.getStatus_list().get(playertimes - 2).getValue() + "\n" + "5th : " + "["
								+ enemy_board.getAxis_list().get(playertimes - 1).getKey() + ","
								+ enemy_board.getAxis_list().get(playertimes - 1).getValue() + "]" + ","
								+ enemy_board.getStatus_list().get(playertimes - 1).getKey() + ","
								+ enemy_board.getStatus_list().get(playertimes - 1).getValue());
			}

			// if player has played under 5 times ,display last shot

			else {
				ExceptionBox.BeforeStart("Last shot under five is",
						playertimes + " : " + "[" + enemy_board.getAxis_list().get(playertimes - 1).getKey() + ","
								+ enemy_board.getAxis_list().get(playertimes - 1).getValue() + "]" + ","
								+ enemy_board.getStatus_list().get(playertimes - 1).getKey() + ","
								+ enemy_board.getStatus_list().get(playertimes - 1).getValue());
			}
		});
		/*----------------------------------------------------------------------------------------------------------*/

		/*----------------------------------------------------------------------------------------------------------*/
		/* "Start" button for restarting game is pressed */
		item1.setOnAction(e -> {
			player_board = player_board.RestartGui(player_board, 0);
			enemy_board = enemy_board.RestartGui(enemy_board, 1);
			player_success_points = 0;
			enemy_success_points = 0;
			playertimes = 0;
			pctimes = 0;

			var1player.setText("" + player_success_points);
			var2player.setText("" + Math.round((enemy_board.success_shoots / enemy_board.num_of_shoots) * 100) + "%");
			var3player.setText("" + player_board.active_ships);

			var1pc.setText("" + enemy_success_points);
			var2pc.setText("" + Math.round((player_board.success_shoots / player_board.num_of_shoots) * 100) + "%");
			var3pc.setText("" + enemy_board.active_ships);
		});
		/*----------------------------------------------------------------------------------------------------------*/

		/*------------------------------------------------------------------------------------------------------------------------------*/
		/*
		 * "Submit" button is pressed by player, when gives (x,y) coordinates for shot
		 */
		submit.setOnAction(e -> {
			// if player cant play again - limit of shots has been passed
			if (playertimes > 40) {
				checkWinner(enemy_success_points, player_success_points);
				System.exit(0);
			} // if times are under limit - then if is turn of player
			else if (turn == 0) {
				isInt(xinput, xinput.getText(), yinput, yinput.getText());
				System.out.println("I am shooting ");
				playertimes++;
				player_success_points = player_success_points + enemy_board.shoot(x, y);

				var1player.setText("" + player_success_points);
				var2player
				.setText("" + Math.round((enemy_board.success_shoots / enemy_board.num_of_shoots) * 100) + "%");
				var3player.setText("" + player_board.active_ships);

				if (player_success_points == 5200) {
					ExceptionBox.display("End Game", "Player is winner, all Pc ships are shunk!!!");
				}
				System.out.println("success_points is " + player_success_points);
				turn = 1;
			}
			// if turn of enemy is - enemy plays
			if (turn == 1 && pctimes < 40) {
				if (random == true) {
					xpc = new Random().nextInt(10);
					ypc = new Random().nextInt(10);
				} else {
					if (!list.isEmpty()) {
						xpc = list.get(0).getKey();
						ypc = list.get(0).getValue();
						list.remove(0);
					} else {
						random = true;
						xpc = new Random().nextInt(10);
						ypc = new Random().nextInt(10);
						if (player_board.board[xpc][ypc] != 0) {
							random = false;
							addtoList(xpc, ypc);
						}
					}
				}

				if (player_board.board[xpc][ypc] != 0) {
					random = false;
					addtoList(xpc, ypc);
				}
				System.out.println("Pc is shooting");
				enemy_success_points = enemy_success_points + player_board.shoot(xpc, ypc);
				var1pc.setText("" + enemy_success_points);
				var2pc.setText("" + Math.round((player_board.success_shoots / player_board.num_of_shoots) * 100) + "%");
				var3pc.setText("" + enemy_board.active_ships);
				if (enemy_success_points == 5200) {
					ExceptionBox.display("End Game", "PC is winner");
				}
				turn = 0;
				pctimes++;
			} // if pc-enemy cant play again, then...
			else if (pctimes >= 40) {
				checkWinner(enemy_success_points, player_success_points);
				System.exit(0);
			}
		});
		/*------------------------------------------------------------------------------------------------------------------------------*/

		/*-------------------------------------------------------------------------------------------------------------*/
		/* First-time Pc plays! */
		if (turn == 1) {
			var1player.setText("" + player_success_points);
			xpc = new Random().nextInt(10);
			ypc = new Random().nextInt(10);
			enemy_success_points = enemy_success_points + player_board.shoot(xpc, ypc);
			var1pc.setText("" + enemy_success_points);
			var2pc.setText("" + Math.round((player_board.success_shoots / player_board.num_of_shoots) * 100) + "%");
			if (player_board.board[xpc][ypc] != 0) {
				random = false;
				addtoList(xpc, ypc);
			}
			System.out.println("Pc is shooting");
			turn = 0;
			pctimes++;
		}
		/*------------------------------------------------------------------------------------------------------------------------------*/
		return root;
	}

	/*------------------------------------------------------------------------------------------------------------------------------*/
	// method which decides for winner of the game
	public void checkWinner(int enemy_points, int player_points) {
		if (enemy_points > player_points) {
			ExceptionBox.BeforeStart("End Game", "PC is winner");
		} else
			ExceptionBox.BeforeStart("End Game", "Player is winner");
	}

	/*------------------------------------------------------------------------------------------------------------------------------*/
	/*
	 * part method of "clever" algorithm shooting of pc , if it hits a ship of
	 * player. If (x,y) shot is successful then put adjacent coordinators on a list
	 * and check them
	 */
	public void addtoList(int xpc, int ypc) {
		Pair<Integer, Integer> p1 = new Pair<Integer, Integer>(xpc - 1, ypc);
		list.add(p1);

		Pair<Integer, Integer> p2 = new Pair<Integer, Integer>(xpc + 1, ypc);
		list.add(p2);

		Pair<Integer, Integer> p3 = new Pair<Integer, Integer>(xpc, ypc - 1);
		list.add(p3);

		Pair<Integer, Integer> p4 = new Pair<Integer, Integer>(xpc, ypc + 1);
		list.add(p4);

	}

	/*---------------------------------------------------------------------------------------------------------*/
	/* method for checking if (x,y) coordinators have valid type (integer type) */
	public boolean isInt(TextField inputx, String msg1, TextField inputy, String msg2) {
		try {
			x = Integer.parseInt(inputx.getText());
			y = Integer.parseInt(inputy.getText());
			// System.out.println("x is "+ x + " and y is " + y);
			return true;
		} catch (NumberFormatException e) {
			System.out.println("Error: " + msg1 + " and " + msg2 + " is not a number");
			return false;
		}

	}

	/*------------------------------------------------------------------------------------------------------------------------------*/
	/*
	 * This method is call time ,one time for each player and is for reading input
	 * from file, creating ships and calling other methods which check if a ship can
	 * be placed on board
	 */
	public Board inputSource(Board gameboard, File file) throws IOException {

		int id = 0, col = 0, row = 0, pos = 0, num_of_cells = 0;

		unique1 = 0;
		unique2 = 0;
		unique3 = 0;
		unique4 = 0;
		unique5 = 0;

		String path = file.getAbsolutePath();
		BufferedReader reader = new BufferedReader(new FileReader(path));

		String line = reader.readLine();

		while (line != null) {

			id = Integer.parseInt(line.substring(0, 1));
			row = Integer.parseInt(line.substring(2, 3));
			col = Integer.parseInt(line.substring(4, 5));
			pos = Integer.parseInt(line.substring(6, 7));

			// if ship is a carrier
			if (id == 1) {
				if (unique1 > 0) {
					ExceptionBox.display("Warning", "InvalidCountException");
				} else {
					Carrier carrier = new Carrier();
					gameboard.getListOfShips().add(carrier);
					num_of_cells = carrier.getlength();
					OversizeException(row, col, pos, num_of_cells);
					OverlapTilesException(gameboard, num_of_cells, row, col, pos);
					unique1++;
				}
			}

			if (id == 2) {
				if (unique2 > 0) {
					ExceptionBox.display("Warning", "InvalidCountException");
				} else {
					Battleship battleship = new Battleship();
					gameboard.getListOfShips().add(battleship);
					num_of_cells = battleship.getlength();
					OversizeException(row, col, pos, num_of_cells);
					OverlapTilesException(gameboard, num_of_cells, row, col, pos);
					unique2++;
				}
			}

			if (id == 3) {
				if (unique3 > 0) {
					ExceptionBox.display("Warning", "InvalidCountException");
				} else {
					Cruiser cruiser = new Cruiser();
					gameboard.getListOfShips().add(cruiser);
					num_of_cells = cruiser.getlength();
					OversizeException(row, col, pos, num_of_cells);
					OverlapTilesException(gameboard, num_of_cells, row, col, pos);
					unique3++;
				}
			}

			if (id == 4) {
				if (unique4 > 0) {
					ExceptionBox.display("Warning", "InvalidCountException");
				} else {
					Submarine submarine = new Submarine();
					gameboard.getListOfShips().add(submarine);
					num_of_cells = submarine.getlength();
					OversizeException(row, col, pos, num_of_cells);
					OverlapTilesException(gameboard, num_of_cells, row, col, pos);
					unique4++;
				}
			}

			if (id == 5) {
				if (unique5 > 0) {
					ExceptionBox.display("Warning", "InvalidCountException");
				} else {
					Destroyer destroyer = new Destroyer();
					gameboard.getListOfShips().add(destroyer);
					num_of_cells = destroyer.getlength();
					OversizeException(row, col, pos, num_of_cells);
					OverlapTilesException(gameboard, num_of_cells, row, col, pos);
					unique5++;
				}
			}
			Fillboard(gameboard, num_of_cells, row, col, pos, id);
			AdjacentTilesException(gameboard, num_of_cells, row, col, pos);
			line = reader.readLine();
		}

		return gameboard;

	}

	/*------------------------------------------------------------------------------------------------------------------------------*/
	/*
	 * method for checking if a ship has at least one cell distance(vertically or
	 * horizontally) from any other ship on board
	 */
	public void AdjacentTilesException(Board player_board, int boatLength, int row, int col, int pos) {

		switch (pos) {
		case 2: {
			if ((row - 1) >= 0) {
				if (player_board.GetCell(row - 1, col) != 0)
					ExceptionBox.display("Warning", "AdjacentTilesException");
			}

			if ((row + boatLength) < 10) {
				if (player_board.GetCell((row + boatLength), col) != 0)
					ExceptionBox.display("Warning", "AdjacentTilesException");
			}

			if ((col - 1) >= 0) {
				for (int i = row; i < (row + boatLength); i++) {
					if (player_board.GetCell(i, col - 1) != 0)
						ExceptionBox.display("Warning", "AdjacentTilesException");
				}
			}

			if ((col + 1) < 10) {
				for (int i = row; i < (row + boatLength); i++) {
					if (player_board.GetCell(i, col + 1) != 0)
						ExceptionBox.display("Warning", "AdjacentTilesException");
				}
			}

			break;
		}

		case 1: {

			if ((col - 1) >= 0) {
				if (player_board.GetCell(row, col - 1) != 0)
					ExceptionBox.display("Warning", "AdjacentTilesException");
			}

			if ((col + boatLength) < 10) {
				if (player_board.GetCell(row, (col + boatLength)) != 0)
					ExceptionBox.display("Warning", "AdjacentTilesException");
			}

			if ((row - 1) >= 0) {
				for (int j = col; j < (col + boatLength); j++) {
					if (player_board.GetCell(row - 1, j) != 0)
						ExceptionBox.display("Warning", "AdjacentTilesException");
				}
			}

			if ((row + 1) < 10) {
				for (int j = col; j < (col + boatLength); j++) {
					if (player_board.GetCell(row + 1, j) != 0)
						ExceptionBox.display("Warning", "AdjacentTilesException");
				}
			}

			break;
		}
		}

		return;

	}

	/*------------------------------------------------------------------------------------------------------------------------------*/
	/* method for checking if all the positions of a ship are inside the board */
	public void OversizeException(int row, int col, int pos, int boatLength) {

		switch (pos) {
		case 2: {
			if ((row + boatLength) > 10) {
				ExceptionBox.display("Warning", "OversizeException");
			}
			break;
		}

		case 1: {
			if ((col + boatLength) > 10) {
				ExceptionBox.display("Warning", "OversizeException");
			}
			break;
		}

		}

		return;
	}

	/*------------------------------------------------------------------------------------------------------------------------------*/
	/*
	 * this method is called when a ship satisfies all restrictions and she places
	 * the ship on the board
	 */
	public void Fillboard(Board player_board, int boatLength, int row, int col, int pos, int id) {

		switch (pos) {
		case 2: {
			for (int i = row; i < (row + boatLength); i++) {
				player_board.PlaceBoard(i, col, id);
			}

			break;
		}

		case 1: {
			for (int j = col; j < (col + boatLength); j++) {
				player_board.PlaceBoard(row, j, id);
			}
			break;
		}
		}

		return;
	}

	/*------------------------------------------------------------------------------------------------------------------------------*/
	/*
	 * this method checks if positions of a ship have been occupied by other ship
	 * before
	 */
	public void OverlapTilesException(Board player_board, int boatLength, int row, int col, int pos) {

		switch (pos) {
		case 2: {
			for (int i = row; i < (row + boatLength); i++) {
				if (player_board.GetCell(i, col) != 0) {
					ExceptionBox.display("Warning", "OverlapTilesException");
				}
			}

			break;
		}

		case 1: {
			for (int j = col; j < (col + boatLength); j++) {
				if (player_board.GetCell(row, j) != 0) {
					ExceptionBox.display("Warning", "OverlapTilesException");
				}
			}
			break;
		}
		}

		return;

	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Scene scene = new Scene(createContent());
		primaryStage.setTitle("Media Battleship");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);

	}
}
