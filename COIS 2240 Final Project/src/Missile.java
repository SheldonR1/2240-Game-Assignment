import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

// contains methods to create and manage missiles fired by player
public class Missile {
	private double positionX;			// x and y coordinates of missile
    private double positionY;
    private double width; 				
    private double height;
    private Image sprite;				// missile sprite
    double velX;						// x and y velocity
    double velY;
    
    public Missile(Player player)
    {  
    	positionX = player.getPositionX();									// Sets x and y coordinates to player position
    	positionY = player.getPositionY();									
    	sprite = new Image("file:resources/bullet.png",20,20,true,true);	// loads sprite
    	velX = (positionX -500);											// determines velocity based on position compared to center of screen
    	velY = (positionY-500);
    	System.out.println(velX);
    	System.out.println(velY);
    }
    public double getPositionX()
    {  
    	return positionX;
    }
    public double getPositionY()
    { 
    	return positionY;
    }
    
    // moves x and y coords based on velocity 
    public void update()
    {
    	positionX += velX/50;
    	positionY += velY/50;
    }
    
    // updates position and draws missile
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
    
    
    }    

