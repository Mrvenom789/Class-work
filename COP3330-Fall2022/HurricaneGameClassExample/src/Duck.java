import java.awt.Graphics;
import java.awt.Color;


public class Duck extends Projectile {

	double x, y;
	double vX, vY;
	
	public Duck(int x, int y, int vX, int vY) {
		super(30);
		this.x = x;
		this.y = y;
		this.vX = vX;
		this.vY = vY;
	}

	public void move() {
		this.x += vX;
		this.y += vY;
	}

	public void draw(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillRect((int)this.x, (int)this.y, 20, 20);
	}

	@Override
	public boolean hit(Projectile o) {
		// TODO Auto-generated method stub
		return false;
	}

}
