import javafx.scene.image.Image;
import javafx.geometry.*;
import javafx.scene.canvas.GraphicsContext;

public class Player {

	private Image sprite;
    private double positionX;
    private double positionY;  
    private double width; 
    private double height;
    private double posCounter;
    private double rotation;
    
    public Player()
    {  
    	positionX = 500 + 128;
    	positionY = 500;
    	sprite = new Image("file:resources/carsprite.png",40,80,true, true);
    	System.out.println(sprite.getHeight());
    	System.out.println(sprite.getWidth());
    	posCounter = 0;
    
    	
    
    }
    public void update() {
    	setPositionX(500 + 128 * Math.cos(Math.toDegrees(posCounter))); 
    	setPositionY(500 + 128 * Math.sin(Math.toDegrees(posCounter)));
    	

    }
    
      public void render(GraphicsContext gc)
     {
    	    gc.drawImage(sprite, positionX - 20, positionY - 40);
    }
    public Rectangle2D getBoundary()
    {
    	return new Rectangle2D(positionX, positionY, width, height);
    }
    
    public boolean intersects(Enemy A)
    {
    	return A.getBoundary().intersects(this.getBoundary());
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
    

