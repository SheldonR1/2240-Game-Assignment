import java.util.ArrayList;
import java.util.Collections;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;


// contains methods to create and load start screen, game screen, name entry screen, and high scores screen. Also contains listeners used for transitions between sceens
public final class GUI {
	//private construction to prevent instantiation
	private GUI() {
	}
	// creates start scene
	public static void loadStart(StackPane root) {
		root.setBackground(new Background(new BackgroundImage(new Image ("file:resources/background.jpg"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, null)));	// loads/sets background image
		GridPane grid = new GridPane();																		// Creates and formats GridPane to hold scene elements
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(5, 5, 5, 5));
		grid.getColumnConstraints().addAll(new ColumnConstraints (100), new ColumnConstraints(175), new ColumnConstraints(100), new ColumnConstraints(500));
		grid.getRowConstraints().addAll(new RowConstraints(300),  new RowConstraints(75), new RowConstraints(200));
		Label lblTitle = new Label("Game Title");															// Creates and formats labels to display title/instructions
		lblTitle.setFont(new Font(100));
		lblTitle.setTextFill(Color.DARKRED);
		Label lblInstruct = new Label("How to Play: \n"
				+ "The object of this game is to defend the planet by shooting meteors before they make contact\n"
				+ "Use the mouse to aim and left click to fire\n"
				+ "Use the Left and Right arrow keys to move\n"
				+ "If the planet gets hit to many times the game will end");
		lblInstruct.setTextAlignment(TextAlignment.CENTER);
		lblInstruct.setFont(new Font(20));
		lblInstruct.setTextFill(Color.AQUAMARINE);
		Button btStart = new Button("Start");																// Creates/formats button and defines event for button click
		btStart.setPrefSize(200, 100);
		btStart.setOnAction((event) -> {
			root.getChildren().clear();																		// clears previous scene and adds listener for game finish flag
			addGameListener(root);
			GameState.getGameState().setGameEnded(true);
		});
		grid.add(lblTitle, 2, 1, 3, 1);																		// Loads elements into GridPane and adds GridPane to root to display
		grid.add(lblInstruct, 1, 2, 5, 1);
		grid.add(btStart, 3, 3, 3, 3);
		root.getChildren().add(grid);
	}

	// creates scene to get user to enter name
	private static void loadNameEntry(StackPane root) {
		addNameListener(root);																			 	// Creates listener for name entered flag
		GridPane grid = new GridPane();																		// Creates and formats GridPane to hold scene elements
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(5, 5, 5, 5));
		Label lblHighScoreMsg = new Label("New High Score");												// Creates and formats labels for scene
		lblHighScoreMsg.setFont(new Font(40));
		Label lblNamePrompt = new Label("Enter Name: ");
		Label lblInvalidName = new Label("Name must be 2-15 characters");
		lblInvalidName.setVisible(false);
		TextField nameTextField = new TextField();															// Creates text field for name entry and defines event for pressing enter
		nameTextField.setOnAction((event) -> {
			if ((nameTextField.getText().length() > 15) || (nameTextField.getText().length() < 2)) {		// If name too long or short displays message
				lblInvalidName.setVisible(true);
			} else {																						// If name valid saves it and sets nameEntered flag to true for scene transition
				GameState.getGameState().setName(nameTextField.getText());
				GameState.getGameState().setNameEntered(true);
			}
		});
		HBox hBox = new HBox();																				// Adds all elements to GridPane then adds GridPane to root for display
		hBox.getChildren().addAll(lblNamePrompt, nameTextField);
		grid.add(lblHighScoreMsg, 0, 0);
		grid.add(hBox, 0, 3);
		grid.add(lblInvalidName, 0, 5);
		root.getChildren().add(grid);
	}

	// loads high scores and creates scene to display them
	private static void loadHighScores(StackPane root) {
		GridPane grid = new GridPane();																		// Creates and formats GridPane to hold scores
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(5, 5, 5, 5));
		grid.getColumnConstraints().addAll(new ColumnConstraints (400), new ColumnConstraints(30), new ColumnConstraints(250), new ColumnConstraints(100), new ColumnConstraints (400));
		grid.getRowConstraints().addAll(new RowConstraints(300), new RowConstraints(25), new RowConstraints(25), new RowConstraints(25), new RowConstraints(25), new RowConstraints(25), new RowConstraints(25), new RowConstraints(25), new RowConstraints(25), new RowConstraints(25), new RowConstraints(25), new RowConstraints(200));
		ArrayList<ArrayList<Object>> highScoreList = HighScores.getHighScoreList();							// gets scores from database loaded into 2D ArrayList
		ArrayList<Label> scoreLabels;
		for(int i = 0; i < highScoreList.size(); i++) {														// converts score data from ArrayList into labels and adds them to GridPane
			scoreLabels = getScoreLabels(String.valueOf(i+1), String.valueOf(highScoreList.get(i).get(0)), String.valueOf(highScoreList.get(i).get(1)), (highScoreList.get(i).get(2).equals(GameState.getGameState().getTimestamp())));
			grid.add(scoreLabels.get(0), 1, i+1);
			grid.add(scoreLabels.get(1), 2, i+1);
			grid.add(scoreLabels.get(2), 3, i+1);
		}
		Button exitButton = new Button("Exit");																// creates button to exit program
		exitButton.setOnAction((event) -> {
			Platform.exit();
		});
		grid.add(exitButton, 4, 11);
		root.getChildren().add(grid);																		// adds GridPane to root to display
	}
	
	// takes an ArrayList containing score position, name, score and boolean value for whether score was new and returns ArrayList with values in labels formatted appropriately
	private static ArrayList<Label> getScoreLabels(String scorePos, String name, String score, Boolean newScore) {
		Label lblPos = new Label(scorePos);																	// loads score position/name/score into labels and sets font size
		Label lblName = new Label(name);
		Label lblScore = new Label(score);
		lblPos.setFont(new Font(25));
		lblName.setFont(new Font(25));
		lblScore.setFont(new Font(25));
		if (newScore == true) {																				// Sets font colour depending on whether score was new
			lblPos.setTextFill(Color.DARKRED);
			lblName.setTextFill(Color.DARKRED);
			lblScore.setTextFill(Color.DARKRED);
		}
		else {
			lblPos.setTextFill(Color.CHARTREUSE);
			lblName.setTextFill(Color.CHARTREUSE);
			lblScore.setTextFill(Color.CHARTREUSE);
		}
		ArrayList<Label> scoreLabels = new ArrayList<Label>();												// loads labels into new ArrayList and returns them
		Collections.addAll(scoreLabels, lblPos, lblName, lblScore);
		return scoreLabels;
	}

	// creates listener that transitions to next scene after game finishes
	private static void addGameListener(StackPane root) {
		GameState.getGameState().gameEndedProperty().addListener(new ChangeListener<Boolean>() {			// adds change listener to gameEnded property
			@Override
			public void changed(ObservableValue<? extends Boolean> o, Boolean oldText, Boolean newText) {
				root.getChildren().clear();																	// clears previous scene
				if (!(HighScores.enoughHighScores())){														// if too few high scores in database loads name entry scene 
					loadNameEntry(root);
				} else if (HighScores.higherScore(GameState.getGameState().getScore())) {					// if new score is higher than any in database removes lowest score from database then loads name entry scene
					HighScores.removeHighScore();
					loadNameEntry(root);
				} else {																					// otherwise loads high score scene
					loadHighScores(root);
				}
				GameState.getGameState().gameEndedProperty().removeListener(this);							// removes listener after finish
			}
		});
	}

	// creates a listener that transitions to next scene after the user has entered a valid name
	private static void addNameListener(StackPane root) {
		GameState.getGameState().nameEnteredProperty().addListener(new ChangeListener<Boolean>() {			// Adds change listener to nameEntered property 
			@Override
			public void changed(ObservableValue<? extends Boolean> o, Boolean oldText, Boolean newText) {															
				HighScores.addHighScore(GameState.getGameState().getName(), GameState.getGameState().getScore(), GameState.getGameState().getTimestamp());	// adds score to database
				root.getChildren().clear();																	// Clears previous scene and load high score scene
				loadHighScores(root);
				GameState.getGameState().nameEnteredProperty().removeListener(this);						// removes listener after finished
			}
		});
	}
}
