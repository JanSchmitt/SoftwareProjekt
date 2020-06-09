package model;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

public class InfoLabel extends Label{

	public static final int IMG_WIDTH = 160;
	public static final int IMG_HEIGHT = 60;
	
	public InfoLabel(String text) {
		setPrefSize(160, 60);
		BackgroundImage info = new BackgroundImage(
				new Image("/view/resources/grey_score_label.png", IMG_WIDTH, IMG_HEIGHT, false, true), //URL, width,height,preserveRatio(boolean), smooth(boolean)
				BackgroundRepeat.NO_REPEAT,	BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null); //repeatx, repeaty,position, size
		setBackground(new Background(info));
		setAlignment(Pos.CENTER);
		setPadding(new Insets(10,10,10,10)); //top, right, bottom,left
		setText(text);
		setFont(Font.font("Verdana",18));
	}
	
}
