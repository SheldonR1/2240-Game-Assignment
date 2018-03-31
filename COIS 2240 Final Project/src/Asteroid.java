import java.util.Random;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Asteroid {
	private Image sprite;
    private double positionX;
	private double positionY; 
    private double velX;
    private double velY;
    private double width; 
    private double height;
    
    
    public Asteroid() {
    	Random spawner = new Random();
    	int side = spawner.nextInt(3);
    	int startPoint = spawner.nextInt(999) + 1;
    	int endPoint = spawner.nextInt(139) + 1;
    	switch (side) {
    	case 0:
    		positionX = startPoint;
    		positionY = 0;
    		velX = getRealVel(430 + endPoint - startPoint);
    		System.out.println(velX);
    		velY = getCompVel(velX);
    		System.out.println(velY);
    		velX *= Math.log(GameState.getGameState().getGameStage() + Math.E) / 300;
    		System.out.println(velX);
    		break;
    	case 1:
    		positionX = startPoint;
    		positionY = 1000;
    		velX = getRealVel(425 + endPoint - startPoint);
    		velY = -getCompVel(velX);
    		velX *= Math.log(GameState.getGameState().getGameStage() + Math.E) / 300;
    		break;
    	case 2:
    		positionY = startPoint;
    		positionX = 0;
    		velY = getRealVel(425 + endPoint - startPoint);
    		velX = getCompVel(velY);
    		velY *= Math.log(GameState.getGameState().getGameStage() + Math.E) / 300;
    		break;
    	case 3:
    		positionY = startPoint;
    		positionX = 1000;
    		velY = getRealVel(425 + endPoint - startPoint);
    		velX = -getCompVel(velY);
    		velY *= Math.log(GameState.getGameState().getGameStage() + Math.E) / 300;
    		break;
    	}
    	sprite = new Image("file:resources/Circle.png",50,50,true,true);
    	width = sprite.getWidth();
    	height = sprite.getHeight();
    }
    public double getPositionX() {
		return positionX;
	}

	public double getPositionY() {
		return positionY;
	}
    
    public void update() {
    	positionX += velX;
    	positionY += velY;
    }
    
    public void render(GraphicsContext gc) {
    update();	
    gc.drawImage(sprite, positionX - 25, positionY - 25);
    }
    public Rectangle2D getBoundary()
    {
    	return new Rectangle2D(positionX - 25, positionY - 25, width, height);
    }
    private double getRealVel(double vel) {
    	return vel * Math.sqrt(2*500*500) / Math.sqrt(vel*vel + 500*500);
    } 
    private double getCompVel(double vel) {
    	return Math.sqrt(2*500*500 - vel)*Math.log(GameState.getGameState().getGameStage() + Math.E) / 300;
    }
}
    

