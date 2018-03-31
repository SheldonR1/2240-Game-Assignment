import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.animation.*;
import javafx.geometry.*;

public class Main extends Application {
	
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		primaryStage.setTitle("Toast");
		
		HighScores.checkDatabase();
		primaryStage.setTitle("Toast");
		primaryStage.setResizable(false);
		StackPane root = new StackPane();
		root.setPrefSize(1000, 1000);
		root.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		Scene theScene = new Scene(root);
		primaryStage.setScene(theScene);
		GUI.loadGame(theScene, root);
		//GUI.loadStart(root);
		highScoreTest();

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

