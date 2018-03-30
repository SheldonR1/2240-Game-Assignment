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
	// private constructor to initialize singleton and set base values
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
	
	// Alter values when player destroys an asteroid
	public void asteroidDestroyed() {
		score += 100 * gameStage * combo;	// Increase score based on stage and combo
		combo += 1;							// Increase combo and destroyed counter
		numDest += 1;
		if (numDest >= (gameStage * 10))	// Destroyed counter high enough calls function to increase stage
			upStage();
	}
	
	// Alters values when asteroid hits planet
	public void asteroidHit() {
		combo = 1;							// resets combo and decreases lives
		lives--;
		if (lives == 0)						// if lives hits zero set flag for end of game to true
			setGameEnded(true);
	}
	
	// Alters values when stage increases
	public void upStage() {
		score += gameStage * 1000;			// increase score based on stage
		gameStage += 1;						// increases stage and lives, resets destroyed counter
		lives++;
		numDest = 0;
	}
}
