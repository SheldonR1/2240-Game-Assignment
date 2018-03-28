import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GameState {
	private static final long startTime = System.nanoTime();
	private static final long timestamp = System.currentTimeMillis();
	private static int score = 0;
	private static int gameStage = 1;
	private static SimpleIntegerProperty lives = new SimpleIntegerProperty(10);
	private static int combo = 1;
	private static int numDest = 0;
	private static SimpleStringProperty name = new SimpleStringProperty("");
	/*public GameState() {
		startTime = System.nanoTime();
		score = 0;
		gameStage = 1;
		lives = 10;
		combo = 1;
		numDest = 0;
		name = "";
		timestamp = System.currentTimeMillis();
	}*/
	public static int getScore() {
		return score;
	}
	public static int getGameStage() {
		return gameStage;
	}
	public static int getCombo() {
		return combo;
	}
	public static int getNumDest() {
		return numDest;
	}
	public static String getName() {
		return name.get();
	}
	public static void setName(String name) {
		GameState.name.set(name);
	}
	public static SimpleStringProperty nameProperty() {
		return name;
	}
	public static int getLives() {
		return lives.get();
	}
	public static SimpleIntegerProperty livesProperty() {
		return lives;
	}
	public static long getTimestamp( ) {
		return timestamp;
	}
	public static long getElapsedTime( ) {
		return startTime - System.nanoTime();
	}
	public static void asteroidDestroyed() {
		score += 100 * gameStage * combo;
		combo += 1;
		numDest += 1;
		if (numDest >= (gameStage * 10))
			upStage();
	}
	public static void asteroidHit() {
		combo = 1;
		lives.set(lives.get() - 1);
	}
	public static void upStage() {
		score += gameStage * 1000;
		gameStage += 1;
		lives.set(lives.get() + 1);
		numDest = 0;
	}
}
