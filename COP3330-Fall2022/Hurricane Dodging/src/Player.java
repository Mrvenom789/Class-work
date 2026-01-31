import java.awt.Color;
import java.awt.Graphics;

public class Player {
	
	private double targetX, targetY, x, y;
	private int size, speed;
	//private int health;
	
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
		this.targetX = 0;
		this.targetY = 0;
		this.speed = 10;
		//this.health = 5;
		this.size = 30;
	}
	
	public void setTarget(int x, int y) {
		this.targetX = x;
		this.targetY = y;
	}
	
	public int getX() {
		return (int) this.x;
	}
	
	public int getY() {
		return (int) this.y;
	}
	
	public void update() {
		double deltaX = this.targetX - this.x;
		double deltaY = this.targetY - this.y;
		double angle = Math.atan2(deltaY, deltaX);
		x += this.speed * Math.cos(angle);
		y += this.speed * Math.sin(angle);
		double dx = this.speed * Math.cos(angle);
		double dy = this.speed * Math.sin(angle);
		if(Math.abs(dx) > Math.abs(deltaX)) {
			dx = deltaX;
		}
		if(Math.abs(dy) > Math.abs(deltaY)) {
			dy = deltaY;
		}
		this.x += dx;
		this.y += dy;
	}
	
	public boolean doesHit(Hurricane ian) {
			double centerX = this.x + this.size/2;
			double centerY = this.y + this.size/2;
			double deltaX = centerX - ian.getX();
			double deltaY = centerY - ian.getY();
			double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
			double sumRads = ian.getSize()/2 + this.size/2;
			if(sumRads >= distance) {
				return true;
			}
			return false;
		}
	
	public void draw(Graphics g) {
		g.setColor(Color.blue);
		g.fillOval((int)this.x,  (int)this.y, this.size, this.size);
	}

}
