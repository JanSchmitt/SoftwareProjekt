package maze;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MazeFX extends Application {

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

	boolean leicht = false;
	int lenght;
	int startX = 0;
	int startY = 0;
	int endX = 0;
	int endY = 0;
	int locX = startX;
	int locY = startY;
	Scene scene, scene2;
	Stage s;
	GridPane g;
	Circle cp = new Circle(4.0, Color.RED);
	Timer timer;
	TimerTask task;
	double time;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryScene) throws Exception {
		startTimer();
		s = primaryScene;
		g = new GridPane();

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
		s.setTitle("The Maze");
		s.setScene(scene);
		s.show();

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

	public void paintNewScene() {
		addPlayer();
		g.setGridLinesVisible(true);
		g.getChildren().addAll();
		// scene = new Scene(g, 400, 400);
		// s.setTitle("The Maze");
		s.setScene(scene);
		s.show();
		if (leicht == true) {
			if (mazeArray[locY][locX] == 3) {
				timer.cancel();
				task.cancel();
				System.out.println(time);
				s.close();
			}
		} else if(leicht == false) {
			if (mazeArraySchwer[locY][locX] == 3) {
				timer.cancel();
				task.cancel();
				System.out.println(time);
				s.close();
			}
		}
	}

	public void addPlayer() {
		g.add(cp, locX, locY);
	}

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

}
