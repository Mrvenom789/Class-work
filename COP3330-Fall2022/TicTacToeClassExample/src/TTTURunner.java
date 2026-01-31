import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class TTTURunner extends JComponent implements MouseListener {

	private static int size = 900;
	public static void main(String[] args) {
		JFrame frame = new JFrame("Tic-Tac-Toe");
		frame.add(new TTTURunner());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(size, size);
		frame.setVisible(true);
	}

	TicTacToe[][] board;
	int turn = 0, winner = -1;
	int fRow = 1, fCol = 1;

	public TTTURunner() {
		board = new TicTacToe[3][3];
		for (int r=0; r<3; r++) {
			for (int c=0; c<3; c++) {
				System.out.println(size/3);
				board[r][c] = new TicTacToe(size/3, c*size/3, r*size/3);
			}
		}

		this.addMouseListener(this);
	}

	public void paint(Graphics g) {
		if (board[fRow][fCol].getWinner() > 0) {
			g.setColor(Color.yellow);
			g.fillRect(0, 0, size, size);
		}
		for (int r=0; r<3; r++) {
			for (int c=0; c<3; c++) {
				if (r==fRow && c==fCol) {
					g.setColor(Color.yellow);
					g.fillRect(c*size/3, r*size/3, size/3, size/3);
				}

				board[r][c].paint(g);
			}
		}
		g.setColor(Color.magenta);
		g.fillRect(size/3-10, 0, 20, size);
		g.fillRect(size*2/3-10, 0, 20, size);
		g.fillRect(0, size/3-10, size, 20);
		g.fillRect(0, size*2/3-10, size, 20);

		if (winner == 1) {
			g.setColor(Color.blue);
			g.fillRect(0, 0, size, size);
		}
		if (winner == 2) {
			g.setColor(Color.red);
			g.fillRect(0, 0, size, size);
		}
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
		int row = e.getY()/ (size/3);
		if ((col == fCol && row == fRow) || board[fRow][fCol].getWinner() > 0) { 
			if (board[row][col].getWinner() < 0) {	
				int c = e.getX() % (size/3) / (size/9);
				int r = e.getY() % (size/3) / (size/9);
				if (board[row][col].placePiece(r, c, turn%2+1)) {
					checkWin(turn%2+1);
					fCol = c;
					fRow = r;
					turn++;
				}
			}
		}

		repaint();
	}
	private void checkWin(int player) {
		for (int i=0; i<3; i++) {
			if (board[i][0].getWinner() == player && board[i][1].getWinner() == player && board[i][2].getWinner() == player) {
				winner = player;
			}
			if (board[0][i].getWinner() == player && board[1][i].getWinner() == player && board[2][i].getWinner() == player) {
				winner = player;
			}
		}
		if (board[0][0].getWinner() == player && board[1][1].getWinner() == player && board[2][2].getWinner() == player) {
			winner = player;
		}
		if (board[2][0].getWinner() == player && board[1][1].getWinner() == player && board[0][2].getWinner() == player) {
			winner = player;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
}
