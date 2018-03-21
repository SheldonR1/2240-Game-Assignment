
public class GameState {
	private final long startTime;
	private int score;
	private int gameStage;
	int lives;
	private int combo;
	int numDest;
	public GameState() {
		startTime = System.nanoTime();
		score = 0;
		gameStage = 1;
		lives = 10;
		combo = 1;
		numDest = 0;
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
