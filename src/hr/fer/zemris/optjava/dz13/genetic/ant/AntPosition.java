package hr.fer.zemris.optjava.dz13.genetic.ant;

public class AntPosition {
	
	private int x;
	private int y;
	private int moveX;
	private int moveY;
	public AntPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.moveX = 0;
		this.moveY = 1;
	}
	public int getX()
	{
		return this.x;
	}
	public int getY()
	{
		return this.y;
	}
	public void setX(int x)
	{
		this.x = x;
	}
	public void setY(int y)
	{
		this.y = y;
	}
	public int getMoveX()
	{
		return this.moveX;
	}
	public int getMoveY()
	{
		return this.moveY;
	}
	public void moveRight()
	{
		if(moveX == 0 && moveY == 1)
		{
			moveX = 1; moveY = 0;
		}
		else if(moveX == 1 && moveY == 0)
		{
			moveX = 0; moveY = -1;
		}
		else if(moveX == 0 && moveY == -1)
		{
			moveX = -1; moveY = 0;
		}
		else if(moveX == -1 && moveY == 0)
		{
			moveX = 0; moveY = 1;
		}
	}
	public void moveLeft()
	{
		if(moveX == 0 && moveY == 1)
		{
			moveX = -1; moveY = 0;
		}
		else if(moveX == 1 && moveY == 0)
		{
			moveX = 0; moveY = 1;
		}
		else if(moveX == 0 && moveY == -1)
		{
			moveX = +1; moveY = 0;
		}
		else if(moveX == -1 && moveY == 0)
		{
			moveX = 0; moveY = -1;
		}
	}
	public void setXandY(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	public AntPosition clone()
	{
		AntPosition ant = new AntPosition(x,y);
		ant.moveX = moveX;
		ant.moveY = moveY;
		ant.x     = x;
		ant.y     = y;
		return ant;
	}
}
