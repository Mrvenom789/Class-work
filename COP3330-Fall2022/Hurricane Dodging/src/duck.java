import java.awt.Color;
import java.awt.Graphics;

public class duck extends projectile{
	
	double x, y;
	double vX, vY;

	public duck(int x, int y, int vX, int vY) {
		super(30);
		this.x = x;
		this.y = y;
		this.vX = vX;
		this.vY = vY;
		
	}

	@Override
	public void move() {
		this.x += vX;
		this.y += vY;
		
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.yellow);
		
		
		
	}

	@Override
	public boolean hit(projectile o) {
		// TODO Auto-generated method stub
		return false;
	}
	

}
