package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

public class MyButton extends Button{
	
	private final String FONT_PATH = "src/model/resources/kenvector_future.ttf";
	private final String BUTTON_FREE_STYLE = "-fx-background-color: transparent; -fx-background-image: url('model/resources/yellow_button00.png');";
	private final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-image: url('model/resources/yellow_button01.png');";
	
	public MyButton(String text) {
		setText(text);
		setButtonFont();
		setPrefWidth(190);
		setPrefHeight(49);
		setStyle(BUTTON_FREE_STYLE);
		initializeButtonListeners();
	}
	
	private void setButtonFont() {
		//setFont(Font.font("Verdana", 18));
		try {
			
			setFont(Font.loadFont(new FileInputStream(FONT_PATH), 19));
		} catch (FileNotFoundException e) {
			setFont(Font.font("Verdana", 23));
		}
		
	}
	private void setButtonPressedStyle() {
		setStyle(BUTTON_PRESSED_STYLE);
		this.setPrefHeight(45);
		this.setLayoutY(getLayoutY() +4);
	}
	
	private void setButtonReleasedStyle() {
		setStyle(BUTTON_FREE_STYLE);
		this.setPrefHeight(49);
		this.setLayoutY(getLayoutY() -4);
	}
	
	private void initializeButtonListeners() {
		setOnMousePressed(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				if(event.getButton().equals(MouseButton.PRIMARY)) {
					setButtonPressedStyle();
				}
			}
		});
		
		setOnMouseReleased(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				if(event.getButton().equals(MouseButton.PRIMARY)) {
					setButtonReleasedStyle();
				}
			}
		});
		
		setOnMouseEntered(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				setEffect(new DropShadow());
			}
		});
		
		setOnMouseExited(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				setEffect(null);
			}
		});
		
	}
}
