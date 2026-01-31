import javax.swing.JComponent;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GameManger extends JComponent implements MouseListener, Runnable {

	private Player play;
	private ArrayList<Hurricane> hurrList;
	private int timer = 0;
	private int score = 0;
	
	public ArrayList<Projectile> duckList;

	public GameManger() {
		play = new Player(0, 0);
		duckList = new ArrayList<Projectile>();
		hurrList = new ArrayList<Hurricane>();
		hurrList.add(new Hurricane(100, 100, 20));
		hurrList.add(new Hurricane(700, 100, 50));
		
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
		for (Hurricane h : hurrList) {
			h.draw(g);
		}
		for (Projectile p : duckList) {
			p.draw(g);
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
				
				for (int i=0; i<hurrList.size(); i++) {
					hurrList.get(i).update();
					if (play.doesHit(hurrList.get(i))) {
						System.out.println("You got hit by a hurricane");
						score+= hurrList.get(i).score();
						hurrList.remove(i);
						
						System.out.println(score);
						i--;
					}
				}
				
				for (int i=0; i<duckList.size(); i++) {
					duckList.get(i).move();
					duckList.get(i).update();
					if (!duckList.get(i).isAlive()) {
						duckList.remove(i);
						i--;
					}
				}
				
				if (timer > 100) {
					if (Math.random() < .5) {
					hurrList.add(new Hurricane ((int) (Math.random()* 800),  
							(int) (Math.random()* 800), (int) (Math.random()* 100)));
					} else {
						hurrList.add(new Typhoon ((int) (Math.random()* 800),  
								(int) (Math.random()* 800), (int) (Math.random()* 100)));
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
