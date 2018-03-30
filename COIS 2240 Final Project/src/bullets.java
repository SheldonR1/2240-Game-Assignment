import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class bullets {
	private double positionX;
    private double positionY;  
    private double width; 
    private double height;
    private Image bullet;
    
    public bullets(Image bullet)
    {  
    
    	this.bullet = bullet; 
    }
    
    public void update(double positionX, double positionY)
    {
    	this.positionX = positionX;
    	this.positionY = positionY;
    
    }
    public void render(GraphicsContext gc)
    {
    gc.drawImage(bullet, positionX, positionY);
    }
    public Rectangle2D getBoundary()
    {
    	return new Rectangle2D(positionX, positionY, width, height);
    }
    
    public boolean intersects(bullets shot)
    {
    	return shot.getBoundary().intersects(this.getBoundary());
    }
    
    }    

