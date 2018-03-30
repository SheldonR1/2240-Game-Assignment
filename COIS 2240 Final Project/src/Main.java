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
import javafx.geometry.*;

public class Main extends Application {
	
	
	@Override
	public void start(Stage primaryStage) throws Exception{
				primaryStage.setTitle("Toast");
		
				Pane root = new Pane();
				Scene theScene = new Scene(root);
				primaryStage.setScene(theScene);
		
				Canvas canvas = new Canvas(1000, 1000);
				root.getChildren().add(canvas);
				primaryStage.setResizable(false);
					
				
				GraphicsContext gc = canvas.getGraphicsContext2D();
				canvas.toBack();
				
				Image background = new Image("file:resources/background.jpg"); //sets background
				Image circle = new Image("file:resources/Circle.png");     // sets the circle
				Image player = new Image("file:resources/carsprite.png",450,75,true, true); //sets player image as car
				Image bullet = new Image("file:resources/bullet.png", 25,25,true,true);       //sets bullets
				Image planet = new Image("file:resources/earth.png", 150,150,true,true);      //sets planet
			
				Player playerData = new Player(player); 
				bullets projectile = new bullets(bullet);
				RangeFinder rangefinder = new RangeFinder(500, 375);
				
							
				
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
				            
				        	
				        //    gc.clearRect(0, 0, 1000,750);        //clears the canvas
			
				            
				            //CIRCULAR MOVEMENT EQUATION
				            
				            if (input.contains("RIGHT"))   //EQUATIONS FOR MOVEMENT REQUIRED
				            {
				            	playerData.incPosCounter();
				            	playerData.update();
				            }
				            else if (input.contains("LEFT")) //EQUATIONS FOR MOVEMENT REQUIRED
				            {
				            	playerData.decPosCounter();
				            	playerData.update();
					             
				            }
				            if (input.contains("SPACE"))
				            {   double x = rangefinder.CalculateSlope(playerData.getPositionX() , playerData.getPositionY());
				            	projectile.update(playerData.getPositionX(), playerData.getPositionY(),x);	
				            	
				            }
				 
				            // background image clears canvas
	   					
				            gc.drawImage(background, 10, 10);     //draws the canvas
				            gc.drawImage(planet, 385, 385);    //draws the circle 
				             playerData.render(gc);
				             projectile.render(gc);
				             
				             
				            
				          
				        }
				    }.start();
				    
				 primaryStage.show();

				
	}

	public static void main(String[] args) {
				launch(args);
	}
}


	