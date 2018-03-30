import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Enemy {
	private Image Enemy;
    private double positionX;
    private double positionY;
    private double speed;
    private double width; 
    private double height;
    
    public Enemy(double speed, double positionX, double positionY, Image Enemy)
    {  
    	this.positionX = positionX;
    	this.positionY = positionY;
    	this.Enemy = Enemy; 
    }
    
    public void update(double time)
    {
    	positionX += speed*time; 
    	positionY += speed*time; 
    }
    public void render(GraphicsContext gc)
    {
    gc.drawImage(Enemy, positionX, positionY);
    }
    public Rectangle2D getBoundary()
    {
    	return new Rectangle2D(positionX, positionY, width, height);
    }
    
    public boolean intersects(Enemy A)
    {
    	return A.getBoundary().intersects(this.getBoundary());
    }
    
    }    
    

