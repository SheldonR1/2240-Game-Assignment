import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

// Class holding methods relating to HighScores SQLite database
public final class HighScores {
	// private constructor to prevent instantiation
	private HighScores(){};
	
	// attempts to connect to database/table and recreates them if they are not found
	public static void checkDatabase() {
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
	public static void addHighScore(String name, int score, long timestamp) {
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
	public static void removeHighScore() {
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
	public static boolean enoughHighScores() {

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
	public static boolean higherScore(int score) {
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

	
	public static GridPane highScoreEntry() {
		GameState.addNameListener();
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(5, 5, 5, 5));
		Label lblHighScoreMsg = new Label("New High Score");
		lblHighScoreMsg.setFont(new Font(40));
		Label lblNamePrompt = new Label("Enter Name: ");
		Label lblInvalidName = new Label("Name must be 2-15 characters");
		lblInvalidName.setVisible(false);
		TextField nameTextField = new TextField();
		nameTextField.setOnAction((event) -> {
			if ((nameTextField.getText().length() > 15) || (nameTextField.getText().length() < 2)) {
				lblInvalidName.setVisible(true);
			} else {
				GameState.getGameState().setName(nameTextField.getText());
				GameState.getGameState().setNameEntered(true);
			}
		});
		HBox hBox = new HBox();
		hBox.getChildren().addAll(lblNamePrompt, nameTextField);
		grid.add(lblHighScoreMsg, 0, 0);
		grid.add(hBox, 0, 3);
		grid.add(lblInvalidName, 0, 5);
		return grid;
	}

	
	public static GridPane displayHighScores(long timestamp) {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(5, 5, 5, 5));
		grid.getColumnConstraints().addAll(new ColumnConstraints(25), new ColumnConstraints(100), new ColumnConstraints(100));
		int scorePos = 1;
		Boolean newScore = false;
		String url = "jdbc:sqlite:HighScores.db";																// url of database
		String sql = "SELECT name, score, timestamp FROM high_scores ORDER BY score DESC, timestamp ASC";		// sql code to return sorted scores from database
		try (Connection conn = DriverManager.getConnection(url);												// connect to database and execute sql code
				Statement smt = conn.createStatement();
				ResultSet rs = smt.executeQuery(sql)) {
			while(rs.next()) {
				newScore = (timestamp == rs.getLong("timestamp"));
				grid.add(newScoreLabel(String.valueOf(scorePos), newScore), 0, scorePos);
				grid.add(newScoreLabel(rs.getString("name"), newScore), 1, scorePos);
				grid.add(newScoreLabel(String.valueOf(rs.getInt("score")), newScore), 2, scorePos);
				scorePos++;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return grid;
	}

	public static Label newScoreLabel(String text, Boolean newScore) {
		Label lbl = new Label(text);
		if (newScore == true)
			lbl.setTextFill(Color.BLUE);
		else
			lbl.setTextFill(Color.RED);
		return lbl;
	}
}
