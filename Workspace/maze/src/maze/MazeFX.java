package maze;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MazeFX extends Application {

	int[][] mazeArray={ { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
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

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage s) throws Exception {
		     
		GridPane g = new GridPane();
		
		for(int i = 0; i<20; i++) {
			for(int j = 0; j<20; j++) {
				if(mazeArray[i][j] == 1) {
					g.add(createWall(), j, i);
				}
				if(mazeArray[i][j] == 0) {
					g.add(createPath(), j, i);
				}
				if(mazeArray[i][j] == 2) {
					g.add(createStart(), j, i);
					g.add(createPlayer(), j, i);
				}
				if(mazeArray[i][j]==3) {
					g.add(createFinish(), j, i);
				}
			}
		}
		
		Scene scene = new Scene(g, 400, 400);
		g.setGridLinesVisible(true);
		s.setTitle("The Maze");
		s.setScene(scene);
		s.show();	
	}
	
	public Rectangle createWall() {
		Rectangle rw = new Rectangle();
		rw.setFill(Color.BLACK);
		rw.setHeight(20);
		rw.setWidth(20);
		return rw;
	}
	
	public Rectangle createStart() {
		Rectangle rsf = new Rectangle();
		rsf.setFill(Color.GREEN);
		rsf.setHeight(20);
		rsf.setWidth(20);
		return rsf;
	}
	
	public Rectangle createFinish() {
		Rectangle rf = new Rectangle();
		rf.setFill(Color.BLUE);
		rf.setHeight(20);
		rf.setWidth(20);
		return rf;
	}
	
	public Rectangle createPath() {
		Rectangle rp = new Rectangle();
		rp.setFill(Color.WHITE);
		rp.setHeight(20);
		rp.setWidth(20);
		return rp;
	}
	
	public Circle createPlayer() {
		Circle cp = new Circle();
		cp.setFill(Color.RED);
		cp.setRadius(6);
		return cp;
	}
}
