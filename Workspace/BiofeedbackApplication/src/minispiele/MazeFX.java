package minispiele;

import java.util.Timer;
import java.util.TimerTask;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MazeFX {

	//matrix for easy mode of the maze
	int[][] mazeArray = { { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 2, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1 },
			{ 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1 },
			{ 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1 },
			{ 1, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 1, 0, 1 },
			{ 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1 },
			{ 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1 },
			{ 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1 },
			{ 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1 },
			{ 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1, 1, 1 },
			{ 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 1 },
			{ 1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 1 },
			{ 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 1 },
			{ 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 3 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };

	//matrix for hard mode of the maze
	int[][] mazeArraySchwer = {
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1 },
			{ 2, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0,
					0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0,
					1, 1, 1, 0, 1 },
			{ 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0,
					1, 1, 1, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,
					1, 0, 0, 0, 1 },
			{ 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					1, 0, 1, 1, 1 },
			{ 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,
					1, 0, 1, 1, 1 },
			{ 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0,
					1, 0, 0, 0, 1 },
			{ 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0,
					1, 1, 1, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1, 1,
					1, 1, 1, 0, 1 },
			{ 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0,
					0, 0, 0, 0, 1 },
			{ 1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1,
					1, 1, 1, 0, 1 },
			{ 1, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0,
					0, 0, 1, 0, 1 },
			{ 1, 0, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1,
					1, 0, 1, 0, 1 },
			{ 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0,
					1, 0, 1, 0, 1 },
			{ 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0,
					1, 0, 1, 0, 1 },
			{ 1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0,
					1, 0, 1, 0, 1 },
			{ 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0,
					1, 0, 1, 0, 1 },
			{ 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1,
					1, 0, 1, 0, 1 },
			{ 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 1, 0, 1 },
			{ 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 0, 1, 0, 1 },
			{ 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0,
					1, 0, 1, 0, 1 },
			{ 1, 1, 0, 1, 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1,
					1, 0, 1, 0, 1 },
			{ 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1,
					1, 0, 1, 0, 1 },
			{ 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0,
					1, 0, 1, 0, 1 },
			{ 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0,
					1, 0, 1, 0, 1 },
			{ 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0,
					1, 0, 1, 0, 1 },
			{ 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0,
					1, 0, 1, 0, 1 },
			{ 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0,
					1, 0, 1, 1, 1 },
			{ 1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 1, 0, 3 },
			{ 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1,
					1, 0, 1, 0, 1 },
			{ 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0,
					1, 0, 1, 0, 1 },
			{ 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0,
					1, 0, 1, 0, 1 },
			{ 1, 1, 0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 1, 0,
					1, 0, 1, 0, 1 },
			{ 1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0,
					0, 0, 1, 0, 1 },
			{ 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1,
					1, 1, 1, 0, 1 },
			{ 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0,
					1, 1, 1, 0, 1 },
			{ 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0,
					1, 1, 1, 0, 1 },
			{ 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0,
					0, 0, 0, 0, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
					1, 1, 1, 1, 1 } };

	//variables
	public boolean leicht = true;
	public int lenght;
	public int startX = 0;
	public int startY = 0;
	public int endX = 0;
	public int endY = 0;
	public int locX = startX;
	public int locY = startY;
	public double time;
	public int points = -1000;
	Scene scene, scene2;
	Stage s;
	GridPane g;
	Circle cp = new Circle(4.0, Color.RED);
	Timer timer;
	TimerTask task;
	private AnchorPane minispielPane;
	
	//constructor for mazes
	public MazeFX(AnchorPane mP) {
		this.minispielPane = mP;
	}

	//start method for mazes
	public void start() {
		startTimer();
		g = new GridPane();
		//easy mode: creation of maze
		if (leicht == true) {
			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < 20; j++) {
					if (mazeArray[i][j] == 1) {
						g.add(createWall(), j, i);
					}
					if (mazeArray[i][j] == 0) {
						g.add(createPath(), j, i);
					}
					if (mazeArray[i][j] == 2) {
						g.add(createStart(), j, i);
						startX = j;
						startY = i;
						System.out.println("" + startX + "," + startY);
					}
					if (mazeArray[i][j] == 3) {
						g.add(createFinish(), j, i);
						endX = j;
						endX = i;
					}
				}
			}
		}
		//hard mode: creation of maze
		if (leicht == false) {
			for (int i = 0; i < 40; i++) {
				for (int j = 0; j < 40; j++) {
					if (mazeArraySchwer[i][j] == 1) {
						g.add(createWall(), j, i);
					}
					if (mazeArraySchwer[i][j] == 0) {
						g.add(createPath(), j, i);
					}
					if (mazeArraySchwer[i][j] == 2) {
						g.add(createStart(), j, i);
						startX = j;
						startY = i;
						System.out.println("" + startX + "," + startY);
					}
					if (mazeArraySchwer[i][j] == 3) {
						g.add(createFinish(), j, i);
						endX = j;
						endX = i;
					}
				}
			}
		}

		locX = startX;
		locY = startY;
		addPlayer();
		g.setGridLinesVisible(false);
		g.getChildren().addAll();
		scene = new Scene(g, 400, 400);
		minispielPane.getChildren().add(g);

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (leicht == true) {
					if (event.getCode() == KeyCode.LEFT) {
						if (mazeArray[locY][locX - 1] == 0) {
							g.getChildren().remove(cp);
							locX = locX - 1;
							paintNewScene();
						}
					}
					if (event.getCode() == KeyCode.DOWN) {
						if (mazeArray[locY + 1][locX] == 0) {
							g.getChildren().remove(cp);
							locY = locY + 1;
							paintNewScene();
						}
					}
					if (event.getCode() == KeyCode.RIGHT) {
						if (mazeArray[locY][locX + 1] == 0 || mazeArray[locY][locX + 1] == 3) {
							g.getChildren().remove(cp);
							locX = locX + 1;
							paintNewScene();
						}
					}
					if (event.getCode() == KeyCode.UP) {
						if (mazeArray[locY - 1][locX] == 0) {
							g.getChildren().remove(cp);
							locY = locY - 1;
							paintNewScene();
						}
					}
				} else if (leicht == false) {
					if (event.getCode() == KeyCode.LEFT) {
						if (mazeArraySchwer[locY][locX - 1] == 0) {
							g.getChildren().remove(cp);
							locX = locX - 1;
							paintNewScene();
						}
					}
					if (event.getCode() == KeyCode.DOWN) {
						if (mazeArraySchwer[locY + 1][locX] == 0) {
							g.getChildren().remove(cp);
							locY = locY + 1;
							paintNewScene();
						}
					}
					if (event.getCode() == KeyCode.RIGHT) {
						if (mazeArraySchwer[locY][locX + 1] == 0 || mazeArraySchwer[locY][locX + 1] == 3) {
							g.getChildren().remove(cp);
							locX = locX + 1;
							paintNewScene();
						}
					}
					if (event.getCode() == KeyCode.UP) {
						if (mazeArraySchwer[locY - 1][locX] == 0) {
							g.getChildren().remove(cp);
							locY = locY - 1;
							paintNewScene();
						}
					}
				}

			}

		});
	}

	//stops the maze if necessary
	public void stop() {
		minispielPane.getChildren().remove(0);
	}

	//function to move the player to the left
	public void moveLeft() {
		if (leicht == true) {
			if (mazeArray[locY][locX - 1] == 0) {
				g.getChildren().remove(cp);
				locX = locX - 1;
				paintNewScene();
			}
		}
		if (leicht == false) {
			if (mazeArraySchwer[locY][locX - 1] == 0) {
				g.getChildren().remove(cp);
				locX = locX - 1;
				paintNewScene();
			}
		}
	}

	//function to move the player to the right
	public void moveRight() {
		if (leicht == true) {
			if (mazeArray[locY][locX + 1] == 0 || mazeArray[locY][locX + 1] == 3) {
				g.getChildren().remove(cp);
				locX = locX + 1;
				paintNewScene();
			}
		}
		if (leicht == false) {
			if (mazeArraySchwer[locY][locX + 1] == 0 || mazeArraySchwer[locY][locX + 1] == 3) {
				g.getChildren().remove(cp);
				locX = locX + 1;
				paintNewScene();
			}
		}

	}

	//function to move the player to the up
	public void moveUp() {
		if (leicht == true) {
			if (mazeArray[locY - 1][locX] == 0) {
				g.getChildren().remove(cp);
				locY = locY - 1;
				paintNewScene();
			}
		}
		if (leicht == false) {
			if (mazeArraySchwer[locY - 1][locX] == 0) {
				g.getChildren().remove(cp);
				locY = locY - 1;
				paintNewScene();
			}
		}
	}

	//function to move the player to the down
	public void moveDown() {
		if (leicht == true) {
			if (mazeArray[locY + 1][locX] == 0) {
				g.getChildren().remove(cp);
				locY = locY + 1;
				paintNewScene();
			}
		} else if (leicht == false) {
			if (mazeArraySchwer[locY + 1][locX] == 0) {
				g.getChildren().remove(cp);
				locY = locY + 1;
				paintNewScene();
			}
		}
	}

	//starts the timer to calculate the points 
	public void startTimer() {
		timer = new Timer();
		time = 0.0000;
		task = new TimerTask() {
			@Override
			public void run() {
				time = time + 0.1000;
			}
		};
		timer.scheduleAtFixedRate(task, 100, 100);
	}

	//paints the new scene after player was moved
	public void paintNewScene() {
		addPlayer();
		g.setGridLinesVisible(true);
		g.getChildren().addAll();
		scene = new Scene(g, 400, 400);
		s.setScene(scene);
		minispielPane.getChildren().add(g);
		if (leicht == true) {
			if (mazeArray[locY][locX] == 3) {
				timer.cancel();
				task.cancel();
				System.out.println(time);
				s.close();
			}
		} else if (leicht == false) {
			if (mazeArraySchwer[locY][locX] == 3) {
				timer.cancel();
				task.cancel();
				System.out.println(time);
				if (time <= 50) {
					points = 600;
				} else if (time > 50) {
					points = 100;
				}
				s.close();
			}
		}
	}

	//returns the given points for the maze solve
	public int getPoints() {
		return points;
	}

	//adds the player to the gridpane
	public void addPlayer() {
		g.add(cp, locX, locY);
	}

	//creates a wall for the maze
	public Rectangle createWall() {
		Rectangle rw = new Rectangle();
		rw.setFill(Color.BLACK);
		if (leicht == true) {
			rw.setHeight(20);
			rw.setWidth(20);
		} else if (leicht == false) {
			rw.setHeight(10);
			rw.setWidth(10);
		}
		return rw;
	}

	//creates the starting point in the maze
	public Rectangle createStart() {
		Rectangle rsf = new Rectangle();
		rsf.setFill(Color.GREEN);
		if (leicht == true) {
			rsf.setHeight(20);
			rsf.setWidth(20);
		} else if (leicht == false) {
			rsf.setHeight(10);
			rsf.setWidth(10);
		}
		return rsf;
	}

	//creates the finish in the maze
	public Rectangle createFinish() {
		Rectangle rf = new Rectangle();
		rf.setFill(Color.BLUE);
		if (leicht == true) {
			rf.setHeight(20);
			rf.setWidth(20);
		} else if (leicht == false) {
			rf.setHeight(10);
			rf.setWidth(10);
		}
		return rf;
	}

	//creates a path block in the maze
	public Rectangle createPath() {
		Rectangle rp = new Rectangle();
		rp.setFill(Color.WHITE);
		if (leicht == true) {
			rp.setHeight(20);
			rp.setWidth(20);
		} else if (leicht == false) {
			rp.setHeight(10);
			rp.setWidth(10);
		}
		return rp;
	}

	//test function for the testing area
	public void test() {
		startTimer();
		g = new GridPane();
		leicht = true;
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				if (mazeArray[i][j] == 1) {
					g.add(createWall(), j, i);
				}
				if (mazeArray[i][j] == 0) {
					g.add(createPath(), j, i);
				}
				if (mazeArray[i][j] == 2) {
					g.add(createStart(), j, i);
					startX = j;
					startY = i;
					System.out.println("" + startX + "," + startY);
				}
				if (mazeArray[i][j] == 3) {
					g.add(createFinish(), j, i);
					endX = j;
					endX = i;
				}
			}
		}

		locX = startX;
		locY = startY;
		addPlayer();
		g.setGridLinesVisible(false);
		g.getChildren().addAll();
		scene = new Scene(g, 400, 400);
		minispielPane.getChildren().add(g);
	}

	//moves player to the left in the test area
	public void moveLeftTest(Stage window) {
		if (mazeArray[locY][locX - 1] == 0) {
			g.getChildren().remove(cp);
			locX = locX - 1;
			paintNewTestScene(window);
		}
	}

	//moves player to the right in the test area
	public void moveRightTest(Stage window) {
		if (mazeArray[locY][locX + 1] == 0 || mazeArray[locY][locX + 1] == 3) {
			g.getChildren().remove(cp);
			locX = locX + 1;
			paintNewTestScene(window);
		}
	}

	//moves player up in the test area
	public void moveUpTest(Stage window) {
		if (mazeArray[locY - 1][locX] == 0) {
			g.getChildren().remove(cp);
			locY = locY - 1;
			paintNewTestScene(window);
		}
	}

	//moves player down in the test area
	public void moveDownTest(Stage window) {
		if (mazeArray[locY + 1][locX] == 0) {
			g.getChildren().remove(cp);
			locY = locY + 1;
			paintNewTestScene(window);
		}
	}

	//paints new scene in test area after player was moved
	public void paintNewTestScene(Stage window) {
		addPlayer();
		g.setGridLinesVisible(true);
		g.getChildren().addAll();
		minispielPane.getChildren().add(g);
		scene = new Scene(g, 400, 400);
		window.setScene(scene);
		if (mazeArray[locY][locX] == 3) {
			timer.cancel();
			task.cancel();
			System.out.println(time);
		}
	}
}
