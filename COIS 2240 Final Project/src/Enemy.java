import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Enemy {
	private Image sprite;
    private double positionX;
    private double positionY;
    private double Xval;
    private double Yval;
    private double newXpos; 
    private double newYpos; 
    private double speedX = 1;
    private double direction;
    private double speedY = 1;
    private double width; 
    private double height;
    
    
    public Enemy()
    {  
    	sprite = new Image("file:resources/Circle.png",50,50,true,true); 
    }
    
    public void update()
    {
    	direction = Math.atan(((425-Yval))/((425-Xval)));
    	newXpos = (this.positionX + (speedX*Math.cos(direction)));
    	newYpos = (this.positionY + (speedY*Math.cos(direction)));
    }
    public void render(GraphicsContext gc)
    {
    update();	
    gc.drawImage(sprite, newXpos, newYpos);
    }
    public Rectangle2D getBoundary()
    {
    	return new Rectangle2D(positionX, positionY, width, height);
    }
    
    public boolean intersects(Missile A)
    {   
    	
    	return A.getBoundary().intersects(this.getBoundary());
    	
    }
    public void setpositionX(double posX)
    {
    	this.positionX = posX;
    	this.Xval = posX;
    }
   
    public void setpositionY(double posY)
    {
    	this.positionY = posY;
    	this.Yval = posY;
    }
    
    }    
    

