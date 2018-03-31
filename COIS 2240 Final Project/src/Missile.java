import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class Missile {
	private double positionX;
    private double positionY;  
    private double width; 
    private double height;
    private Image sprite;
    double velX;
    double velY;
    
    public Missile(Player player)
    {  
    	positionX = player.getPositionX();
    	positionY = player.getPositionY();
    	sprite = new Image("file:resources/bullet.png",20,20,true,true);
    	velX = (positionX -500);
    	velY = (positionY-500);
    	System.out.println(velX);
    	System.out.println(velY);
    }
    
    public void update()
    {
    	positionX += velX/50;
    	positionY += velY/50;
    }
    public void render(GraphicsContext gc)
    {
    	update();
    gc.drawImage(sprite, positionX - 10, positionY - 10);
    }
    public Rectangle2D getBoundary()
    {
    	return new Rectangle2D(positionX, positionY, width, height);
    }
    
    public boolean intersects(Missile shot)
    {
    	return shot.getBoundary().intersects(this.getBoundary());
    }
    public double getPositionX()
    {  
    	return positionX;
    }
    public double getPositionY()
    { 
    	return positionY;
    }
    
    }    

