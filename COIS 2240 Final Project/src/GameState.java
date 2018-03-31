import java.util.ArrayList;
import java.util.Iterator;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;

// Lazy singleton class containing various fields and methods relation to the gamestate 
public final class GameState {
	private static GameState gameState = null;
	private final long timestamp;				// unix timestamp used to determine order of equal high scores
	private SimpleIntegerProperty score;		// current score
	private SimpleIntegerProperty gameStage;	// score/difficulty multiplier
	private SimpleIntegerProperty lives;		// remaining lives
	private SimpleIntegerProperty combo;		// score multiplier
	private int numDest;						// counter for destroyed asteroids
	private int cooldown;						// cooldown for shooting
	private Boolean movingLeft;					// flag indicating left arrow key held
	private Boolean movingRight;				// flag indicating right arrow held
	private Boolean firing;						// flag indicating space bar held
	private String name;						// user's name
	private final Image background; 			// background image
	private final Image planet;      			// planet image
	private final Player player; 				// player character
	private final ArrayList<Missile> missiles; 		// Arraylist containing all missile objects in the game at any point in time
	private SimpleBooleanProperty gameStarted;	// flag to remove start screen/load game screen
	private SimpleBooleanProperty gameEnded;	// flag to remove game screen and load name entry or high scores screen
	private SimpleBooleanProperty nameEntered;	// flag to remove name entry screen and load high scores screen
	
	// private constructor to initialize singleton and set base values
	private GameState() {
		timestamp = System.currentTimeMillis();
		score = new SimpleIntegerProperty(0);
		gameStage = new SimpleIntegerProperty(1);
		lives = new SimpleIntegerProperty(10);
		combo = new SimpleIntegerProperty(1);
		numDest = 0;
		cooldown = 0;
		name = "";
		missiles = new ArrayList<Missile>();
		movingLeft = false;
		movingRight = false;
		firing = false;	
		background = new Image("file:resources/background.jpg");
		planet = new Image("file:resources/earth.png", 150,150,true,true);
		player = new Player();
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
		return score.get();
	}
	public SimpleIntegerProperty getScoreProperty() {
		return score;
	}
	public int getGameStage() {
		return gameStage.get();
	}
	public SimpleIntegerProperty getGameStageProperty() {
		return gameStage;
	}
	public int getCombo() {
		return combo.get();
	}
	public SimpleIntegerProperty getComboProperty() {
		return combo;
	}
	public int getLives() {
		return lives.get();
	}
	public SimpleIntegerProperty getLivesProperty() {
		return lives;
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
	public Image getBackground() {
		return background;
	}
	public Image getPlanet() {
		return planet;
	}
	public Player getPlayer() {
		return player;
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
		score.set(score.get() + 100 * gameStage.get() * combo.get());	// Increase score based on stage and combo
		combo.set(combo.get() + 1);							// Increase combo and destroyed counter
		numDest += 1;
		if (numDest >= (gameStage.get() * 10))	// Destroyed counter high enough calls function to increase stage
			upStage();
	}
	
	// Alters values when asteroid hits planet
	public void asteroidHit() {
		combo.set(1);							// resets combo and decreases lives
		lives.set(lives.get() - 1);;
		if (lives.get() == 0)						// if lives hits zero set flag for end of game to true
			setGameEnded(true);
	}
	
	// Alters values when stage increases
	public void upStage() {
		score.set(score.get() + gameStage.get() * 1000);			// increase score based on stage
		gameStage.set(gameStage.get() + 1);						// increases stage and lives, resets destroyed counter
		lives.set(lives.get() + 1);
		numDest = 0;
	}
}
