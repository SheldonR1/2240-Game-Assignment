import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.animation.*;

public class Main extends Application {
	
	
	@Override
	public void start(Stage primaryStage) throws Exception{
				primaryStage.setTitle("Toast");
		
				Pane root = new Pane();
				Scene theScene = new Scene(root);
				primaryStage.setScene(theScene);
		
				Canvas canvas = new Canvas(1000, 750);
				root.getChildren().add(canvas);
		
				GraphicsContext gc = canvas.getGraphicsContext2D();
				Image background = new Image("file:resources/background.jpg"); //sets background
				
				Image circle = new Image("file:resources/Circle.png");     // sets the circle
						canvas.toBack();
				ImageView player = new ImageView();
				player.setImage(circle);
				
				
						ArrayList<String> input = new ArrayList<String>();    //used to track the user's input
					 
						theScene.setOnKeyPressed(new EventHandler<KeyEvent>()  //Used to detect button presses and store the value
					    		{
					    	public void handle(KeyEvent e)                        
					    	{
					    		String code = e.getCode().toString();         //used to interpret button presses as a string value.
					    			if (!input.contains(code))
					    				{
					    				input.add(code);						//used to ensure only one value is stored per press
					    				}
					    				
					    	}
					    		});
					    theScene.setOnKeyReleased(new EventHandler<KeyEvent>()  //used to detect when the button has been released
					    		{
					    	public void handle(KeyEvent e)						
					    	{
					    		String code = e.getCode().toString();     //used to remove the code from the arraylist
					    		input.remove(code);
					    	}
					    	
					    		});
						
				 final long startNanoTime = System.nanoTime();
               		
				  new AnimationTimer()
				    {
				        public void handle(long currentNanoTime)
				        {
				            double t = (currentNanoTime - startNanoTime) / 1000000000.0; 
				
				        //    gc.clearRect(0, 0, 1000,750);        //clears the canvas
				            gc.drawImage(background, 0, 0);     //draws the canvas
				            gc.drawImage(circle, 500, 375);    //draws the circle 
				           				            //CIRCULAR MOVEMENT EQUATION
				            
				            if (input.contains("RIGHT"))   //EQUATIONS FOR MOVEMENT REQUIRED
				            {
				            
				            
				            }
				            else if (input.contains("LEFT")) //EQUATIONS FOR MOVEMENT REQUIRED
				            {
	  
	   
				            }
				 
				            // background image clears canvas
	   					
				           
				          
				        }
				    }.start();
				    
				 primaryStage.show();

				//stat pane
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

	// Class holding methods relating to HighScores SQLite database
	private static class HighScores {
		static ArrayList<Score> highScores = new ArrayList<Score>();				// Initialize ArrayList to hold scores

		// Loads scores from database into array
		private static void loadScores() {
			Connection conn = null;													// Initialize connection, etc, to access database
			Statement smt = null;
			ResultSet rs = null;
			String url = "jdbc:sqlite:HighScores.db";								// url of database
			String sql = "SELECT name, score FROM high_scores ORDER BY score DESC";	// sql code to query/sort scores in database
			try {
				conn = DriverManager.getConnection(url);							// connect to database and execute sql code to query
				smt = conn.createStatement();
				rs = smt.executeQuery(sql);
				while(rs.next()) {													// iterate through results and load scores into highScores ArrayList
					highScores.add(new Score(rs.getString("name"), rs.getInt("score")));
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			} finally {
				try {
					if (rs != null) {												// close connection, etc
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
		
		// adds new score to HighScore database and removes lowest score
		private static void saveScores(Score currentScore) {
			Connection conn = null;												// initialize connection, etc, to access database
			PreparedStatement ps = null;
			String url = "jdbc:sqlite:HighScores.db";							//url of database
			String sqlIns = "INSERT INTO high_scores (name, score) VALUES(?, ?)";							// sql code to insert score into database
			String sqlDel = "DELETE FROM high_scores WHERE score = (SELECT MIN(score) FROM high_scores)";	// sql code to delete lowest score from database
			try {
				conn = DriverManager.getConnection(url);						// connect to database and execute sql code to insert score
				ps = conn.prepareStatement(sqlIns);
				ps.setString(1, currentScore.getName());
				ps.setInt(2, currentScore.getScore());
				ps.executeUpdate();
				ps = conn.prepareStatement(sqlDel);								// execute sql code to delete score
				ps.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			} finally {
				try {
					if (ps != null) {											// close connection, etc
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

