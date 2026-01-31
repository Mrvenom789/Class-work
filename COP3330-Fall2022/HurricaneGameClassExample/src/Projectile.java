import java.awt.Graphics;
import java.awt.Color;

public abstract class Projectile {
	private double life;
	
	public Projectile(double l) {
		this.life = l;
	}
	
	public void update() {
		this.life -= 1;
	}
	
	public boolean isAlive() {
		return this.life > 0;
	}
	
	public abstract void move();
	public abstract void draw(Graphics g);
	public abstract boolean hit(Projectile o);
}
