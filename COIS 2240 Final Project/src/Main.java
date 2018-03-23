import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception{
				primaryStage.setTitle("Toast");
		
				Pane root = new Pane();
				Scene theScene = new Scene(root);
				primaryStage.setScene(theScene);
		
				Canvas canvas = new Canvas(800, 800);
				root.getChildren().add(canvas);
		
				GraphicsContext gc = canvas.getGraphicsContext2D();
		
				/*Image circleImage = new Image("file:resources/Circle.png");
				ImageView circleImageView = new ImageView(circleImage);
				Image semicircleImage = new Image("file:resources/semicircle.png");
				ImageView semicircleImageVeiw = new ImageView(semicircleImage);
				semicircle.setRotate(90);
				gc.drawImage(circleImageView, 350, 350);
				gc.drawImage(semicircle, 390, 340);*/
				
				
		
				primaryStage.show();
	}

	public static void main(String[] args) {
		HighScores.checkDatabase();
		highScoreTest();
		launch(args);
	}

	// used to test score class
	public static void highScoreTest() {
		GameState gameState = new GameState();
		HighScores.outputHighScores(gameState.getTimestamp());
		gameState.setName("toast15");
		for (int i = 1; i < 4; i++)
			gameState.asteroidDestroyed();
		if (!(HighScores.enoughHighScores())){
			HighScores.addHighScore(gameState.getName(), gameState.getScore(), gameState.getTimestamp());
			System.out.println("New High Score");
		} else if (HighScores.higherScore(gameState.getScore())) {
			HighScores.addHighScore(gameState.getName(), gameState.getScore(), gameState.getTimestamp());
			HighScores.removeHighScore();
			System.out.println("New High Score");
		} else {
			System.out.println("No New High Score");
		}
		HighScores.outputHighScores(gameState.getTimestamp());
		
	}

	
	// Class holding methods relating to HighScores SQLite database
	private static class HighScores {
		
		// attempts to connect to database/table and recreates them if they are not found
		private static void checkDatabase() {
			String url = "jdbc:sqlite:HighScores.db";																// url of database
			String sql = "CREATE TABLE IF NOT EXISTS high_scores (name text, score integer, timestamp integer);";	// sql code to create a new table if one is not found
			try (Connection conn = DriverManager.getConnection(url);												// connect to database and execute sql code
				Statement smt = conn.createStatement()) {
				smt.executeUpdate(sql);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		
		// adds new score to HighScore database
		private static void addHighScore(String name, int score, long timestamp) {
			String url = "jdbc:sqlite:HighScores.db";																// url of database
			String sql = "INSERT INTO high_scores (name, score, timestamp) VALUES(?, ?, ?)";						// sql code to insert score into database
			try (Connection conn = DriverManager.getConnection(url);												// connect to database and execute sql code
				PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setString(1, name);																				// load values into wildcards of prepared statement
				ps.setInt(2, score);
				ps.setLong(3, timestamp);
				ps.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		
		// removes the lowest/most recent high score from the database
		private static void removeHighScore() {
			String url = "jdbc:sqlite:HighScores.db";																// url of database
			String sql = "DELETE FROM high_scores WHERE timestamp = (SELECT MAX(timestamp) FROM (SELECT * FROM high_scores WHERE score = (SELECT MIN(score) FROM high_scores)))";	// sql code to delete lowest score with most recent timestamp
			try (Connection conn = DriverManager.getConnection(url);												// connect to database and execute sql code
				Statement smt = conn.createStatement()) {
				smt.executeUpdate(sql);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		
		// Checks if 10 high scores saved and returns false if not
		private static boolean enoughHighScores() {

			String url = "jdbc:sqlite:HighScores.db";																// url of database
			String sql = "SELECT COUNT(*) FROM high_scores";														// sql code to determine number of high scores stored in table
			try (Connection conn = DriverManager.getConnection(url);												// connect to database and execute sql code
				Statement smt = conn.createStatement();
				ResultSet rs = smt.executeQuery(sql)) {
				while(rs.next()) {
					if (rs.getInt(1) < 10)																			// returns false if number of scores < 10
						return false;
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			return true;																							// returns true otherwise
		}
		
		// Compares score to high scores in database and returns true if new score is higher than any saved scores
		private static boolean higherScore(int score) {
			String url = "jdbc:sqlite:HighScores.db";																// url of database
			String sql = "SELECT name, score FROM high_scores ORDER BY score DESC";									// sql code to return sorted scores from database
			try (Connection conn = DriverManager.getConnection(url);												// connect to database and execute sql code
				Statement smt = conn.createStatement();
				ResultSet rs = smt.executeQuery(sql)) {
				while(rs.next()) {																					// iterate through results and return true if new score is higher than any in table
					if (rs.getInt("score") < score)
						return true;
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			return false;																							// returns false otherwise
		}
		
		// outputs all high scores
		// output method must be changed to display on screen
		private static void outputHighScores(long timestamp) {
			String url = "jdbc:sqlite:HighScores.db";																// url of database
			String sql = "SELECT name, score, timestamp FROM high_scores ORDER BY score DESC, timestamp ASC";		// sql code to return sorted scores from database
			try (Connection conn = DriverManager.getConnection(url);												// connect to database and execute sql code
				Statement smt = conn.createStatement();
				ResultSet rs = smt.executeQuery(sql)) {
				while(rs.next()) {																					// iterate through results and output, indicates which score if any is new
					if (rs.getLong("timestamp") == timestamp)
						System.out.println("---->" + rs.getString(1) + " " + rs.getInt(2));
					else 
						System.out.println(rs.getString(1) + " " + rs.getInt(2));
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
}