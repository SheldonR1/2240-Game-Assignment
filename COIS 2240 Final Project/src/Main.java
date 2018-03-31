import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

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
		GUI.loadStart(theScene, root);
		primaryStage.setScene(theScene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}

