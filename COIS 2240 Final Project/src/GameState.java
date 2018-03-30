import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.GridPane;

// Lazy singleton class containing various fields and methods relation to the gamestate 
public final class GameState {
	private static GameState gameState = null;
	
	private final long startTime;				//Start time of program
	private final long timestamp;				// unix timestamp used to determine order of equal high scores
	private int score;							// current score
	private int gameStage;						// score/difficulty multiplier
	private int lives;							// remaining lives
	private int combo;							// score multiplier
	private int numDest;						// counter for destroyed asteroids
	private String name;						// user's name
	private SimpleBooleanProperty gameStarted;	// flag remove start screen/load game screen
	private SimpleBooleanProperty gameEnded;	// flag to remove game screen and load name entry or high scores screen
	private SimpleBooleanProperty nameEntered;	// flag to remove name entry screen and load high scores screen
	private GameState() {
		startTime = System.nanoTime();
		timestamp = System.currentTimeMillis();
		score = 0;
		gameStage = 1;
		lives = 10;
		combo = 1;
		numDest = 0;
		name = "";
		gameStarted = new SimpleBooleanProperty(false);
		gameEnded = new SimpleBooleanProperty(false);
		nameEntered = new SimpleBooleanProperty(false);
	}
	public static GameState getGameState() {
		if (gameState == null) {
			gameState = new GameState();
		}
		return gameState;
	}
	public int getScore() {
		return score;
	}
	public int getGameStage() {
		return gameStage;
	}
	public int getCombo() {
		return combo;
	}
	public int getNumDest() {
		return numDest;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLives() {
		return lives;
	}
	public long getTimestamp( ) {
		return timestamp;
	}
	public long getElapsedTime( ) {
		return startTime - System.nanoTime();
	}
	public void setGameStarted(boolean gameStarted) {
		this.gameStarted.set(gameStarted);
	}
	public SimpleBooleanProperty gameStartedProperty() {
		return gameStarted;
	}
	public void setGameEnded(boolean gameEnded) {
		this.gameEnded.set(gameEnded);
	}
	public SimpleBooleanProperty gameEndedProperty() {
		return gameEnded;
	}
	public void setNameEntered(boolean nameEntered) {
		this.nameEntered.set(nameEntered);
	}
	public SimpleBooleanProperty nameEnteredProperty() {
		return nameEntered;
	}
	public void asteroidDestroyed() {
		score += 100 * gameStage * combo;
		combo += 1;
		numDest += 1;
		if (numDest >= (gameStage * 10))
			upStage();
	}
	public void asteroidHit() {
		combo = 1;
		lives--;
		if (lives == 0)
			setGameEnded(true);
	}
	public void upStage() {
		score += gameStage * 1000;
		gameStage += 1;
		lives++;
		numDest = 0;
	}
	
	public static void addGameListener() {
		getGameState().gameEndedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> o, Boolean oldText, Boolean newText) {
				GameStage.getGameStage().clearNodes();
				if (!(HighScores.enoughHighScores())){
					GridPane highScoreEntry = HighScores.highScoreEntry();
					GameStage.getGameStage().addNode(highScoreEntry);
				} else if (HighScores.higherScore(GameState.getGameState().getScore())) {
					GridPane highScoreEntry = HighScores.highScoreEntry();
					GameStage.getGameStage().addNode(highScoreEntry);
					HighScores.removeHighScore();
				} else {
					GridPane highScoresList = HighScores.displayHighScores(GameState.getGameState().getTimestamp());
					GameStage.getGameStage().addNode(highScoresList);
				}
			}
		});
	}
	
	public static void addNameListener() {
		getGameState().nameEnteredProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> o, Boolean oldText, Boolean newText) {
				GameStage.getGameStage().clearNodes();
				HighScores.addHighScore(GameState.getGameState().getName(), GameState.getGameState().getScore(), GameState.getGameState().getTimestamp());
				GridPane highScoresList = HighScores.displayHighScores(GameState.getGameState().getTimestamp());
				GameStage.getGameStage().addNode(highScoresList);
				GameState.getGameState().nameEnteredProperty().removeListener(this);
			}
		});
	}
}
