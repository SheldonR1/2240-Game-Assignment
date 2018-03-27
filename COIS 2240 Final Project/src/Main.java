import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
		primaryStage.setTitle("Toast");
		primaryStage.setResizable(false);
		StackPane root = new StackPane();
		root.setPrefSize(1000, 1000);
		root.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		//highScoreTest(gameState);
		GridPane highScoreEntry = HighScores.enterScoreName();
		//GridPane highScoresList = HighScores.displayHighScores(GameState.getTimestamp());
		root.getChildren().add(highScoreEntry);
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
		HighScores.checkDatabase();
		//highScoreTest();
		launch(args);
	}
	
	
	public static void transitionToScores() {
		
	}

	// used to test score class
	public static void highScoreTest(GameState gameState) {
		gameState.setName("toast17");
		for (int i = 1; i < 7; i++)
			gameState.asteroidDestroyed();
		if (!(HighScores.enoughHighScores())){
			HighScores.addHighScore(gameState.getName(), gameState.getScore(), gameState.getTimestamp());
			System.out.println("New High Score");
		} else if (HighScores.higherScore(gameState.getScore())) {
			HighScores.addHighScore(gameState.getName(), gameState.getScore(), gameState.getTimestamp());
			HighScores.removeHighScore();
			System.out.println("New High Score");
		} else {
			System.out.println("No New High Score");
		}

	}
}