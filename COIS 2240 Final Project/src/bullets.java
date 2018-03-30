import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class bullets {
	private double positionX;
    private double positionY;  
    private double width; 
    private double height;
    private Image bullet;
    private double slope;
    private double speed = 2; 
    
    public bullets(Image bullet)
    {  
    
    	this.bullet = bullet; 
    }
    
    public void update(double positionX, double positionY,double slope)
    {
    	this.positionX = positionX;
    	this.positionY = positionY;
    	this.slope = slope;
    	 
    	  
    	
    
    }
    public void render(GraphicsContext gc)
    {
    gc.drawImage(bullet, this.getPositionX(), this.getPositionY());
    }
    public Rectangle2D getBoundary()
    {
    	return new Rectangle2D(positionX, positionY, width, height);
    }
    
    public boolean intersects(bullets shot)
    {
    	return shot.getBoundary().intersects(this.getBoundary());
    }
    public double getPositionX()
    {   positionX += speed;
    	return positionX;
    }
    public double getPositionY()
    { positionY+=speed*slope;
    	return positionY;
    }
    
    }    

