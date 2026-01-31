import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class TTTRunner extends JComponent implements MouseListener {
	
	private static int size = 900;
	public static void main(String[] args) {
		JFrame frame = new JFrame("Tic-Tac-Toe");
		frame.add(new TTTRunner());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(size, size);
		frame.setVisible(true);
	}
	
	TicTacToe board;
	int turn = 0;
	
	public TTTRunner() {
		board = new TicTacToe(size);
		this.addMouseListener(this);
	}
	
	public void paint(Graphics g) {
		board.paint(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int col = e.getX()/ (size/3);
		int row = e.getY()/ (size /3);
		if (board.placePiece(row, col, turn%2+1)) {
			turn++;
		}
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
}
