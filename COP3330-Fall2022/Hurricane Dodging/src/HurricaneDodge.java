import javax.swing.JFrame;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class HurricaneDodge implements KeyListener {
	private static GameManager myGame;

	public static void main(String[] args) {
		JFrame frame = new JFrame("Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(800, 800);
		//frame.add(new GameManager());
		myGame = new GameManager();
		frame.add(myGame);
		frame.addKeyListener(new HurricaneDodge());
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == 'd') {
			int x = myGame.getPlayerX();
			int y = myGame.getPlayerY();
			myGame.duckList.add(new duck(x, y, 0, 5));
		}
		if(e.getKeyChar() == 'w') {
			int x = myGame.getPlayerX();
			int y = myGame.getPlayerY();
			myGame.duckList.add(new duck(x, y, 5, 0));
		}
		if(e.getKeyChar() == 'a') {
			int x = myGame.getPlayerX();
			int y = myGame.getPlayerY();
			myGame.duckList.add(new duck(x, y, 0, -5));
		}
		if(e.getKeyChar() == 's') {
			int x = myGame.getPlayerX();
			int y = myGame.getPlayerY();
			myGame.duckList.add(new duck(x, y, -5, 0));
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
