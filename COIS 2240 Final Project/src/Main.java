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
		//highScoreTest();
		System.out.println(System.currentTimeMillis());
		launch(args);
	}

	public static void highScoreTest() {
		Score toast = new Score("toast3", 300);
		HighScores.loadScores();
		for(Score i : HighScores.highScores) {
			System.out.print(i.getName() + ": " + i.getScore() + "\n");
		}
		int pos = HighScores.compareHighScores(toast);
		if (pos >= 0) {
			System.out.println("New High Score: Pos " + (pos));
			HighScores.saveScores(toast);
		} else {
			System.out.println("No New High Score");
		}
		for(Score i : HighScores.highScores) {
			System.out.print(i.getName() + ": " + i.getScore() + "\n");
		}
	}

	
	// Class holding methods relating to HighScores SQLite database
	private static class HighScores {
		static ArrayList<Score> highScores = new ArrayList<Score>();				// Initialize ArrayList to hold scores
		
		private static void checkDatabase() {
			String url = "jdbc:sqlite:HighScores.db";								// url of database
			String sql = "CREATE TABLE IF NOT EXISTS high_scores ("
					+ "name text, "
					+ "score integer, "
					+ "timestamp integer);";
			try (Connection conn = DriverManager.getConnection(url);				// connect to database and execute sql code to query
				Statement smt = conn.createStatement()) {
				smt.execute(sql);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		// Loads scores from database into array
		private static void loadScores() {
			String url = "jdbc:sqlite:HighScores.db";								// url of database
			String sql = "SELECT name, score FROM high_scores ORDER BY score DESC";	// sql code to query/sort scores in database
			try (Connection conn = DriverManager.getConnection(url);							// connect to database and execute sql code to query
				Statement smt = conn.createStatement();
				ResultSet rs = smt.executeQuery(sql)) {
				while(rs.next()) {													// iterate through results and load scores into highScores ArrayList
					highScores.add(new Score(rs.getString("name"), rs.getInt("score")));
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		
		// adds new score to HighScore database and removes lowest score
		private static void saveScores(Score currentScore) {
			String url = "jdbc:sqlite:HighScores.db";							//url of database
			String sqlIns = "INSERT INTO high_scores (name, score, timestamp) VALUES(?, ?, ?)";							// sql code to insert score into database
			String sqlDel = "DELETE FROM high_scores WHERE timestamp = (SELECT MAX(timestamp) FROM (SELECT MIN(score) FROM high_scores)";	// sql code to delete lowest score from database
			try (Connection conn = DriverManager.getConnection(url);						// connect to database and execute sql code to insert score
				PreparedStatement ps = conn.prepareStatement(sqlIns);
				Statement smt = conn.createStatement();) {
				ps.setString(1, currentScore.getName());
				ps.setInt(2, currentScore.getScore());
				ps.setLong(3, System.currentTimeMillis());
				ps.execute();
				smt.execute(sqlDel);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		
		// Compares score to high scores and updates high scores if higher
		// Returns high score position of score or -1 if not higher
		private static int compareHighScores(Score currentScore) {
			for (int i = 0; i < 10 ; i++) {										// compares current score to each score in HighScores
				if (currentScore.getScore() > highScores.get(i).getScore()) {
					highScores.add(i, currentScore);							// adds currentScore to highScores
					highScores.remove(10);										// removes last score from highScores
					return i+1;													// returns position currentScore was placed in
				}
			}
			return -1;															// returns -1 if score was less than all high scores
		}
	}
}