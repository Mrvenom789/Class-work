import java.awt.Graphics;

public abstract class projectile {
	double life;
	
	public projectile(double l) {
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
	public abstract boolean hit(projectile o);
	
}
