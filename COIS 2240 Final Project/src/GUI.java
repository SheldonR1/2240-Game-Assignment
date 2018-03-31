import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
	public static void loadStart(Scene theScene, StackPane root) {
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
			root.getChildren().clear();																		// clears previous scene and loads game scene
			loadGame(theScene, root);
		});
		grid.add(lblTitle, 2, 1, 3, 1);																		// Loads elements into GridPane and adds GridPane to root to display
		grid.add(lblInstruct, 1, 2, 5, 1);
		grid.add(btStart, 3, 3, 3, 3);
		root.getChildren().add(grid);
	}

	private static void loadStatusDisplay(StackPane root) {
		Label lblScore = new Label();
		Label lblCombo = new Label();
		Label lblStage = new Label();
		Label lblLives = new Label();
		lblScore.textProperty().bind(Bindings.concat("Score: ", GameState.getGameState().getScoreProperty().asString()));
		lblCombo.textProperty().bind(Bindings.concat("Combo: ", GameState.getGameState().getComboProperty().asString()));
		lblStage.textProperty().bind(Bindings.concat("Stage: ", GameState.getGameState().getGameStageProperty().asString()));
		lblLives.textProperty().bind(Bindings.concat("Lives: ", GameState.getGameState().getLivesProperty().asString()));
		lblScore.setFont(new Font(25));
		lblCombo.setFont(new Font(25));
		lblStage.setFont(new Font(25));
		lblLives.setFont(new Font(25));
		lblScore.setTextFill(Color.AQUAMARINE);
		lblCombo.setTextFill(Color.AQUAMARINE);
		lblStage.setTextFill(Color.AQUAMARINE);
		lblLives.setTextFill(Color.AQUAMARINE);
		GridPane grid = new GridPane();
		grid.getColumnConstraints().addAll(new ColumnConstraints (300), new ColumnConstraints(300), new ColumnConstraints(300));
		grid.add(lblScore, 0, 0);
		grid.add(lblCombo, 1, 0);
		grid.add(lblStage, 2, 0);
		grid.add(lblLives, 3, 0);
		root.getChildren().add(grid);
	}
	
	

	public static void loadGame(Scene theScene, StackPane root) {
		addGameListener(root);
		Canvas canvas = new Canvas(1000, 1000);
		root.getChildren().add(canvas);
		loadStatusDisplay(root);


		GraphicsContext gc = canvas.getGraphicsContext2D();

		Image background = new Image("file:resources/background.jpg"); //sets background
		Image planet = new Image("file:resources/earth.png", 150,150,true,true);      //sets planet
		Player player = new Player(); 

		theScene.setOnKeyPressed(new EventHandler<KeyEvent>() { //Used to detect button presses and store the value

			public void handle(KeyEvent e)                        
			{
				switch (e.getCode()) {
				case LEFT: GameState.getGameState().setMovingLeft(true); break;
				case RIGHT: GameState.getGameState().setMovingRight(true); break;
				case SPACE: GameState.getGameState().setFiring(true); break;
				case UP: GameState.getGameState().asteroidDestroyed(); break;
				case DOWN: GameState.getGameState().asteroidHit(); break;
				default: break;
				}
			}
		});
		theScene.setOnKeyReleased(new EventHandler<KeyEvent>() { //used to detect when the button has been released

			public void handle(KeyEvent e)                        
			{
				switch (e.getCode()) {
				case LEFT: GameState.getGameState().setMovingLeft(false); break;
				case RIGHT: GameState.getGameState().setMovingRight(false); break;
				case SPACE: GameState.getGameState().setFiring(false); break;
				default: break;
				}
			}
		});

		new AnimationTimer()
		{
			public void handle(long currentNanoTime)
			{
				if (GameState.getGameState().getMovingRight())   //EQUATIONS FOR MOVEMENT REQUIRED
				{
					player.incPosCounter();
					player.update();
				}
				if (GameState.getGameState().getMovingLeft()) //EQUATIONS FOR MOVEMENT REQUIRED
				{
					player.decPosCounter();
					player.update();
				}
				if (GameState.getGameState().getFiring() && GameState.getGameState().getCooldown() <= 0)
				{   
					GameState.getGameState().addProjectile(new Missile(player));
					GameState.getGameState().resetCooldown();
				}

				// background image clears canvas
				gc.drawImage(background, 0, 0);     //draws the canvas
				gc.drawImage(planet, 500-75, 500-75);    //draws the circle 
				player.render(gc);
				Iterator<Missile> missileIter = GameState.getGameState().getProjectilesIter();
				while (missileIter.hasNext()) {
					Missile missile = missileIter.next();
					missile.render(gc);
					if (missile.getPositionX() > 1000 || missile.getPositionY() > 1000 || missile.getPositionX() < 1 || missile.getPositionY() < 1)
						missileIter.remove();
				}
				GameState.getGameState().decCooldown();
			}
		}.start();
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
		lblHighScoreMsg.setTextFill(Color.AQUAMARINE);
		lblNamePrompt.setTextFill(Color.AQUAMARINE);
		lblInvalidName.setTextFill(Color.RED);
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
