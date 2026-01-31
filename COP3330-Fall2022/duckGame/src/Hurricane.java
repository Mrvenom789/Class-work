import java.awt.Color;
import java.awt.Graphics;

public class Hurricane {
	private double x, y;
	private int size;
	
	public Hurricane(int x, int y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;
	}
	
	public void update() {
		this.x = x + Math.random()* 21 - 10;
		this.y += Math.random()* 21 - 10;
		
	}
	
	public double getX() {
		return this.x + this.size/2;
	}
	public double getY() {
		return this.y + this.size/2;
	}
	public int getSize() {
		return this.size;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.red);
		g.fillOval((int)this.x, (int)this.y, this.size, this.size);
	}
	
	public int score() {
		return 1;
	}
	
}
