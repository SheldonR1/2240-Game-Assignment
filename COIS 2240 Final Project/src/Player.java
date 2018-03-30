import javafx.scene.image.Image;
import javafx.geometry.*;
import javafx.scene.canvas.GraphicsContext;

public class Player {

	private Image player;
    private double positionX;
    private double positionY;  
    private double width; 
    private double height;
    private double posCounter;
    private double rotation;
    
    public Player(Image player)
    {  
    	positionX = 450;
    	positionY = 175;
    	this.player = player;
    	posCounter = 0;
    
    	
    
    }
    public void update() {
    	setPositionX(450 + 175 * Math.cos(Math.toDegrees(posCounter))); 
    	setPositionY(450 + 175 * Math.sin(Math.toDegrees(posCounter)));
    	

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

	public double getPositionX() {
		return positionX;
	}

	public void setPositionX(double positionX) {
		this.positionX = positionX;
	}
	public void setRotation(double rotation) {
		this.rotation = rotation;
	}
	public double getRotation() {
		return rotation;
	}

	public double getPositionY() {
		return positionY;
	}

	public void setPositionY(double positionY) {
		this.positionY = positionY;
	}
    public void incPosCounter() {
    	posCounter += 0.001;
    }
    public void decPosCounter() {
    	posCounter -= 0.001;
    }

    }    
    

