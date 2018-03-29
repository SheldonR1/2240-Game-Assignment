import javafx.scene.image.Image;
import javafx.geometry.*;
import javafx.scene.canvas.GraphicsContext;

public class Player extends Main {

	private Image player;
    private double positionX;
    private double positionY;  
    private double width; 
    private double height;
    
    public Player(double positionX, double positionY, Image player)
    {  
    	this.positionX = positionX;
    	this.positionY = positionY;
    	this.player = player; 
    }
    
    public void update(double time)
    {
    	
    }
    public void render(GraphicsContext gc)
    {
    gc.drawImage(player, positionX, positionY);
    }
    public Rectangle2D getBoundary()
    {
    	return new Rectangle2D(positionX, positionY, width, height);
    }
    
    public boolean intersects(Player Car)
    {
    	return Car.getBoundary().intersects(this.getBoundary());
    }
    
    }    
    

