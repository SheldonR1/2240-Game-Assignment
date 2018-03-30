import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public final class GameStage {
	private static final GameStage gameStage = new GameStage();
	private StackPane root;
	private GameStage() {
		root = new StackPane();
		root.setPrefSize(1000, 1000);
		root.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
	}
	public static GameStage getGameStage() {
		return gameStage;
	}
	public void addNode(Node node) {
		root.getChildren().add(node);
	}
	public void clearNodes() {
		root.getChildren().clear();
	}
	public StackPane getRoot() {
		return root;
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

			getGameStage().clearNodes();
			GameState.addGameListener();
			GameState.getGameState().setGameEnded(true);
		});
		grid.add(lblHighScoreMsg, 0, 0);
		grid.add(lblNamePrompt, 0, 1);
		grid.add(btStart, 0, 2);
		return grid;
	}
	

	//GameStage.getGameStage().addNode(grid);
	
}
