
public class RangeFinder {

	private double planetX;
	private double planetY; 
	private double playerX;
	private double playerY; 
	private double slopeX;
	private double slopeY;
	private double slope;
public RangeFinder(double planetX, double planetY)
{
	this.planetX = planetX; 
	this.planetY = planetY; 
}
public double CalculateSlope(double playerX, double playerY)
{
	this.playerX=playerX;
	this.playerY = playerY; 
	
	slopeY = playerY-planetY;
	slopeX = playerX-planetX;
	
	slope = slopeY/slopeX; 
	
	return slope; 
}
}
