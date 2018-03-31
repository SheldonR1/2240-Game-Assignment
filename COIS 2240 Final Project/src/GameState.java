import java.util.ArrayList;
import java.util.Iterator;

import javafx.beans.property.SimpleBooleanProperty;

// Lazy singleton class containing various fields and methods relation to the gamestate 
public final class GameState {
	private static GameState gameState = null;
	
	private final long timestamp;				// unix timestamp used to determine order of equal high scores
	private int score;							// current score
	private int gameStage;						// score/difficulty multiplier
	private int lives;							// remaining lives
	private int combo;							// score multiplier
	private int numDest;						// counter for destroyed asteroids
	private int cooldown;						// cooldown for shooting
	private Boolean movingLeft;
	private Boolean movingRight;
	private Boolean firing;	
	private String name;						// user's name
	ArrayList<Missile> missiles = new ArrayList<Missile>(); // Arraylist containing all missile objects in the game at any point in time
	private SimpleBooleanProperty gameStarted;	// flag remove start screen/load game screen
	private SimpleBooleanProperty gameEnded;	// flag to remove game screen and load name entry or high scores screen
	private SimpleBooleanProperty nameEntered;	// flag to remove name entry screen and load high scores screen
	// private constructor to initialize singleton and set base values
	private GameState() {
		timestamp = System.currentTimeMillis();
		score = 0;
		gameStage = 1;
		lives = 10;
		combo = 1;
		numDest = 0;
		cooldown = 0;
		name = "";
		movingLeft = false;
		movingRight = false;
		firing = false;	
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
	public Boolean getMovingLeft() {
		return movingLeft;
	}
	public void setMovingLeft(Boolean movingLeft) {
		this.movingLeft = movingLeft;
	}
	public Boolean getMovingRight() {
		return movingRight;
	}
	public void setMovingRight(Boolean movingRight) {
		this.movingRight = movingRight;
	}
	public Boolean getFiring() {
		return firing;
	}
	public void setFiring(Boolean shooting) {
		this.firing = shooting;
	}
	public int getCooldown() {
		return cooldown;
	}
	public void resetCooldown() {
		cooldown = 50;
	}
	public void decCooldown() {
		if (cooldown > 0)
			cooldown--;
	}
	public Iterator<Missile> getProjectilesIter() {
		return missiles.iterator();
	}
	public void addProjectile(Missile projectile) {
		missiles.add(projectile);
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
