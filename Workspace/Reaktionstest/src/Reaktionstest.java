import javafx.application.Application;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javax.sound.sampled.*;
import java.io.File;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class Reaktionstest extends Application implements EventHandler<KeyEvent>{
	Stage win;
	Scene s;
	BorderPane b;
	Label l;
	Circle c;
	Timer  timer1, timer2,t3,sou;
	TimerTask  task, z, play;
	int zeit;
	int time = 0 ;
	boolean ende = false;
	int i= 1;
	int Versuch1,Versuch2,Versuch3;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage reactio){
		File Beep = new File("Beep.wav");
		reactio.setTitle("Reaktionstest");
		b = new BorderPane();
		l = new Label("Klicken Sie 'ENTER' sobald sich die Farbe ändert oder ein Ton zu hören ist");
		c = new Circle(100);
		c.setStroke(Color.BLACK);
		c.setFill(Color.WHITE);
		b.setCenter(c);
		b.setTop(l);
		s = new Scene(b,400,400);
		reactio.setScene(s);
		reactio.show();
		task = new TimerTask() {
			public void run() {
				c.setFill(Color.GREEN);
			}
		};
		play = new TimerTask() {
			public void run() {
				playMusic(Beep);
			}
		};
		z = new TimerTask() {
			public void run() {
				time = time + 1;
			}
		};
		time();
		win = reactio;
		s.setOnKeyPressed(this);
	}
	
	
	public void end() {
		System.out.println(i);
		timer1.cancel();
		timer2.cancel();
		if(i<3) {
			i++;
			win.close();
			start(new Stage());
		}else {
			i++;
			System.out.println(i);
			endscreen();
		}
	}
		
	public void endscreen() {
		BorderPane Bo = new BorderPane();
		Label l1 = new Label();
		Label l2 = new Label();
		Label l3 = new Label();
		if(Versuch1 >= 0) {
			l1.setText("Zeit: " + (1000-Versuch1) +"ms." + Versuch1+ "Punkte extra");
		}else {
			l1.setText("Zu früh gedrückt oder zu lange gebraucht. " + Versuch1 + " Punkte abzug");
		}
		if(Versuch2 >= 0) {
			l2.setText("Zeit: " + (1000-Versuch2) +"ms." + Versuch2+ "Punkte extra");
		}else {
			l2.setText("Zu früh gedrückt oder zu lange gebraucht. " + Versuch1 + " Punkte abzug");
		}
		if(Versuch3 >= 0) {
			l3.setText("Zeit: " + (1000-Versuch3) +"ms." + Versuch3+ "Punkte extra");
		}else {
			l3.setText("Zu früh gedrückt oder zu lange gebraucht. " + Versuch1 + " Punkte abzug");
		}
		Bo.setTop(l1);
		Bo.setCenter(l2);
		Bo.setBottom(l3);
		s = new Scene(Bo,400,400);
		win.setScene(s);
		s.setOnKeyPressed(this);
	}
	
	public void Score(int k) {
		if(k == 1) {
			if(i == 1) {
				Versuch1 = (1000-time);
			}else if (i == 2) {
				Versuch2 = (1000-time);
			}else if ( i == 3) {
				Versuch3 = (1000-time);
			}	
		}else if (k == 0) {
			if(i == 1) {
				Versuch1 = -500;
			}else if (i == 2) {
				Versuch2 = -500;
			}else if ( i == 3) {
				Versuch3 = -500;
			}
		}
		
	}
	
	public void time() {
		timer1 = new Timer();
		timer2 = new Timer();
		Random zahl = new Random();
		sou = new Timer();
		zeit = zahl.nextInt(10);
		zeit = 1000* zeit;
		timer1.schedule(task, zeit);
		sou.schedule(play, zeit);
		timer2.scheduleAtFixedRate(z,zeit,1);
	}

	@Override
	public void handle(KeyEvent event) {
		if((event.getCode() == KeyCode.ENTER)&& (i<= 3)) {
			if(time > 0) {
				Score(1);
				end();
			}else if(time <= 0) {
				Score(0);
				end();
			}
		}else if((event.getCode() == KeyCode.ENTER)&& ( i == 4)) {
			win.close();
		}
	}
	
	
	public static void playMusic(File sound) {
		try {
		    Clip clip = AudioSystem.getClip();
		    clip.open(AudioSystem.getAudioInputStream(sound));
		    clip.start();
		    
		    Thread.sleep(clip.getMicrosecondLength()/1000);
		}catch(Exception e) {
			System.out.println("Fehler");
		}
	}
	
}
