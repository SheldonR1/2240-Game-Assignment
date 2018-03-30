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
		highScoreTest();
		GameStage.getGameStage().addNode(GameStage.startScreen());
		
		
		Scene theScene = new Scene(GameStage.getGameStage().getRoot());
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
	

	// used to test score class
	public static void highScoreTest() {
		for (int i = 1; i < 10; i++)
			GameState.getGameState().asteroidDestroyed();
	}
}