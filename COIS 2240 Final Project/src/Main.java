import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception{
		HighScores.checkDatabase();
		primaryStage.setTitle("Toast");
		primaryStage.setResizable(false);
		StackPane root = new StackPane();
		root.setPrefSize(1000, 1000);
		root.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		highScoreTest();
		if (!(HighScores.enoughHighScores())){
			GridPane highScoreEntry = HighScores.highScoreEntry();
			root.getChildren().add(highScoreEntry);
		} else if (HighScores.higherScore(GameState.getScore())) {
			GridPane highScoreEntry = HighScores.highScoreEntry();
			root.getChildren().add(highScoreEntry);
			HighScores.removeHighScore();
		} else {
			GridPane highScoresList = HighScores.displayHighScores(GameState.getTimestamp());
			root.getChildren().add(highScoresList);
		}
		GameState.nameProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> o, String oldText, String newText) {
				root.getChildren().clear();
				HighScores.addHighScore(GameState.getName(), GameState.getScore(), GameState.getTimestamp());
				GridPane highScoresList = HighScores.displayHighScores(GameState.getTimestamp());
				root.getChildren().add(highScoresList);
			}
		});
		Scene theScene = new Scene(root);
		primaryStage.setScene(theScene);

		/*Canvas canvas = new Canvas(800, 800);
				root.getChildren().add(canvas);
				GraphicsContext gc = canvas.getGraphicsContext2D();

				Image circleImage = new Image("file:resources/Circle.png");
				ImageView circleImageView = new ImageView(circleImage);
				Image semicircleImage = new Image("file:resources/semicircle.png");
				ImageView semicircleImageVeiw = new ImageView(semicircleImage);
				semicircle.setRotate(90);
				gc.drawImage(circleImageView, 350, 350);
				gc.drawImage(semicircle, 390, 340);*/

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public static GridPane startScreen() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(5, 5, 5, 5));
		Label lblHighScoreMsg = new Label("Game Title");
		lblHighScoreMsg.setFont(new Font(100));
		Label lblNamePrompt = new Label("How to Play: \n"
				+ "The object of this game is to defend the planet by shooting meteors before they make contact"
				+ "Use the mouse to aim and click to fire"
				+ "If the planet gets hit to many times the game will end");
		Button btStart = new Button("Start");
		btStart.setOnAction((event) -> {
			
		});
		
		return grid;
	}
	

	// used to test score class
	public static void highScoreTest() {
		for (int i = 1; i < 1; i++)
			GameState.asteroidDestroyed();
	}
}