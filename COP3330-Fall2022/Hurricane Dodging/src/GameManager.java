
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JComponent;

public class GameManager extends JComponent implements MouseListener, Runnable{
	
	private Player play;
	private ArrayList<Hurricane> hurrList;
	private int timer = 0;
	private int score = 0;
	
	public ArrayList<projectile> duckList;
	
	public GameManager() {
		play = new Player(400, 400);
		duckList = new ArrayList<projectile>();
		hurrList = new ArrayList<Hurricane>();
		hurrList.add(new Hurricane(100, 100, 20));
		hurrList.add(new typhoon(700, 100, 50));
		
		this.addMouseListener(this);
		Thread t = new Thread(this);
		t.start();
	}
	
	public int getPlayerX() {
		return play.getX();
	}
	
	public int getPlayerY() {
		return play.getY();
	}
	
	public void paint(Graphics g) {
		play.draw(g);
		for(Hurricane h: hurrList) {
			h.draw(g);
		}
		
		for(projectile p : duckList) {
			p.move();
			p.update();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println(e.getX() + " " + e.getY());
		play.setTarget(e.getX(), e.getY());
		
		}
	

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		while (true) {
			try {
				timer++;
				play.update();
				for(int i=0; i<hurrList.size(); i++) {
					hurrList.get(i).update();
					if(play.doesHit(hurrList.get(i))) {
						System.out.println("You got hit by a hurriane!");
						score += hurrList.get(i).score();
						System.out.println(score);
						i--;
						
						}
					}
				}
				
				if(timer > 100) {
					if(Math.random() < 5) {
						hurrList.add(new Hurricane((int)(Math.random()*800), (int)(Math.random()*800), (int)(Math.random()*100)));
					}
					else {
						hurrList.add(new typhoon((int)(Math.random()*800), (int)(Math.random()*800), (int)(Math.random()*100)));
					}
					timer = 0;
				}
				repaint();
				
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
