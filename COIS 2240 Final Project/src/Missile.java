import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

// contains methods to create and manage missiles fired by player
public class Missile {
	private double posX;			// x and y coordinates of missile
	private double posY;
	private double width; 			// dimensions of missile	
	private double height;
	private Image sprite;			// missile sprite
	double velX;					// x and y velocity components
	double velY;

	public Missile(Player player, Planet planet) {  
		posX = player.getPosX();											// Sets x and y coordinates to player position
		posY = player.getPosY();
		width = 20;															// sets width/height
		height = 20;
		sprite = new Image("file:resources/bullet.png", width, height,true,true);	// loads sprite with dimensions given previously
		velX = (posX - planet.getPosX())/50;								// determines velocity based on position compared to planet
		velY = (posY - planet.getPosY())/50;
		width = sprite.getWidth();											// determines width/height based on dimensions given for image earlier
		height = sprite.getHeight();
	}
	//getters
	public double getPosX() {  
		return posX;
	}
	public double getPosY() { 
		return posY;
	}

	// adjusts x and y coords based on velocity 
	public void update() {
		posX += velX;
		posY += velY;
	}
	
	// updates position and draws missile
	public void render(GraphicsContext gc) {
		update();
		gc.drawImage(sprite, posX - width/2, posY - height/2);
	}
	
	// defines a rectangle used to detect collision
	public Rectangle2D getBoundary()
	{
		return new Rectangle2D(posX - width/2, posY - height/2, width, height);
	}
}    

