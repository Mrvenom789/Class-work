import javax.swing.JFrame;
import java.awt.event.*;


public class HurricaneDodge implements KeyListener {
	private static GameManger mygame ;
	public static void main(String[] args) {
		JFrame frame = new JFrame("Test");
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setVisible(true);
		frame.setSize(800, 800);
		mygame = new GameManger();
		frame.add( mygame );
		frame.addKeyListener( new HurricaneDodge() );
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == 'd') {
			int x = mygame.getPlayerX();
			int y = mygame.getPlayerY();
			mygame.duckList.add(new Duck(x, y, 5, 0));
		}
		if (e.getKeyChar() == 'a') {
			int x = mygame.getPlayerX();
			int y = mygame.getPlayerY();
			mygame.duckList.add(new Duck(x, y, -5, 0));
		}
		if (e.getKeyChar() == 'w') {
			int x = mygame.getPlayerX();
			int y = mygame.getPlayerY();
			mygame.duckList.add(new Duck(x, y,  0, -5));
		}
		if (e.getKeyChar() == 's') {
			int x = mygame.getPlayerX();
			int y = mygame.getPlayerY();
			mygame.duckList.add(new Duck(x, y, 0, 5));
		}
		if (e.getKeyChar() == 'z') {
			int x = mygame.getPlayerX();
			int y = mygame.getPlayerY();
			mygame.duckList.add(new Duck(x, y, -5, 0));
			mygame.duckList.add(new Duck(x, y,  0, -5));
			mygame.duckList.add(new Duck(x, y, 0, 5));
			mygame.duckList.add(new Duck(x, y, 5, 0));
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
