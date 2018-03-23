
public class GameState {
	private final long startTime;
	private final long timestamp;
	private int score;
	private int gameStage;
	private int lives;
	private int combo;
	private int numDest;
	private String name;
	public GameState() {
		startTime = System.nanoTime();
		score = 0;
		gameStage = 1;
		lives = 10;
		combo = 1;
		numDest = 0;
		name = "";
		timestamp = System.currentTimeMillis();
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
	public long getTimestamp( ) {
		return timestamp;
	}
	public long getElapsedTime( ) {
		return startTime - System.nanoTime();
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
		lives -= 1;
	}
	public void upStage() {
		score += gameStage * 1000;
		gameStage += 1;
		lives += 1;
		numDest = 0;
	}
}
