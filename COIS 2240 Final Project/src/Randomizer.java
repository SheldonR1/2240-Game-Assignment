import java.util.Random;
public class Randomizer {

	
	private int side; 
	private int Xpos; 
	private int Ypos; 
	
	public Randomizer()
	{
	 	
	}
	
	public void SpawnEnemy()                       //Randomly generates a number, between 0 and 3 determines which side enemy spawns
	                                               //Randomly generates a number between 0 and 1000 determines where on the side enemy spawns
	{
				
		Random SIDE = new Random(); 
		side = SIDE.nextInt(3);
			
		switch(side)
		{
		case 1: side = 0;
		{
		Ypos = 0; 
		Xpos = SIDE.nextInt(1000);
			break;
		}
		case 2: side = 1;
		{
			Ypos = 0; 
			Xpos = SIDE.nextInt(0);	
			break;
		}
		case 3: side = 2;
		{
			Xpos = 0; 
			Ypos = SIDE.nextInt(1000);	
			break;
		}
		case 4: side = 3;
		{
			Xpos = 0; 
			Ypos = SIDE.nextInt(0);
			break;
		}
		}
		
	}
	public int getX()		                     //Used to return the X values generated from SpawnEnemy()
	{
		return Xpos;
	}
	public int getY()                           //Used to return the Y values generated from SpawnEnemy()
	{
		return Ypos;
	}
	
		
}
