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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception{
		//		primaryStage.setTitle("Toast");
		//
		//		Pane root = new Pane();
		//		Scene theScene = new Scene(root);
		//		primaryStage.setScene(theScene);
		//
		//		Canvas canvas = new Canvas(1000, 750);
		//		root.getChildren().add(canvas);
		//
		//		GraphicsContext gc = canvas.getGraphicsContext2D();
		//
		//		Image circle = new Image("file:resources/Circle.png");
		//
		//		gc.drawImage(circle, 250, 150);
		//
		//		primaryStage.show();

	}

	public static void main(String[] args) {
		highScoreTest();
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

	private static class HighScores {
		static ArrayList<Score> highScores = new ArrayList<Score>();


		private static void loadScores() {
			Connection conn = null;
			Statement smt = null;
			ResultSet rs = null;
			String url = "jdbc:sqlite:HighScores.db";
			String sql = "SELECT Name, Score FROM HighScores ORDER BY Score DESC";
			try {
				conn = DriverManager.getConnection(url);
				smt = conn.createStatement();
				rs = smt.executeQuery(sql);
				while(rs.next()) {
					highScores.add(new Score(rs.getString("Name"), rs.getInt("Score")));
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			} finally {
				try {
					if (rs != null) {
						rs.close();
					}
					if (smt != null) {
						smt.close();
					}
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			}
		}

		private static void saveScores(Score currentScore) {
			Connection conn = null;
			PreparedStatement ps = null;
			String url = "jdbc:sqlite:HighScores.db";
			String sqlIns = "INSERT INTO HighScores (Name, Score) VALUES(?, ?)";
			String sqlDel = "DELETE FROM HighScores WHERE Score = (SELECT MIN(Score) FROM HighScores)";
			try {
				conn = DriverManager.getConnection(url);
				ps = conn.prepareStatement(sqlIns);
				ps.setString(1, currentScore.getName());
				ps.setInt(2, currentScore.getScore());
				ps.executeUpdate();
				ps = conn.prepareStatement(sqlDel);
				ps.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			} finally {
				try {
					if (ps != null) {
						ps.close();
					}
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			}
		}

		private static int compareHighScores(Score currentScore) {
			for (int i = 0; i < 10 ; i++) {
				if (currentScore.getScore() > highScores.get(i).getScore()) {
					highScores.add(i, currentScore);
					highScores.remove(10);
					return i+1;
				}
			}
			return -1;
		}

	}
}

