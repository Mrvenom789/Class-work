import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class TTTRunner2 extends JComponent implements MouseListener {

	private static int size = 1200;
	public static void main(String[] args) {
		JFrame frame = new JFrame("Tic-Tac-Toe");
		frame.add(new TTTRunner2());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(size, size);
		frame.setVisible(true);
	}

	TicTacToe[][] board = new TicTacToe[3][3];
	int turn = 0, winner = -1, lastRow=1, lastCol = 1;

	public TTTRunner2() {
		for (int row=0; row<3; row++) {
			for (int col=0; col<3; col++) {
				board[row][col] = new TicTacToe(size/3, col*size/3, row*size/3);
			}
		}
		

		this.addMouseListener(this);
	}

	public void paint(Graphics g) {
		if (board[this.lastRow][this.lastCol].getWinner() > 0) {
			g.setColor(Color.cyan);
			g.fillRect(0, 0, size, size);
		}
		for (int row=0; row<3; row++) {
			for (int col=0; col<3; col++) {
				if (row == this.lastRow && col==this.lastCol) {
					g.setColor(Color.cyan);
					g.fillRect(col*size/3, row*size/3, size/3, size/3);
				}
				board[row][col].paint(g);
			}
		}

		g.setColor(Color.magenta);
		g.fillRect(size/3, 0, 20, size);
		g.fillRect(2*size/3, 0, 20, size);
		g.fillRect(0,size/3, size, 20);
		g.fillRect(0,2*size/3, size, 20);

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

		if ((col == this.lastCol && row == this.lastRow) || board[this.lastRow][this.lastCol].getWinner() > 0) {

			if (board[row][col].getWinner() == -1) {
				// internal board position...
				int c = e.getX() % (size/3) / (size/9);
				int r = e.getY() % (size/3) / (size/9);
				if (board[row][col].placePiece(r, c, turn%2+1)) {
					checkWin(turn%2+1);
					turn++;	
					this.lastCol = c;
					this.lastRow = r;
					System.out.println(this.lastCol +" " + this.lastRow);
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
