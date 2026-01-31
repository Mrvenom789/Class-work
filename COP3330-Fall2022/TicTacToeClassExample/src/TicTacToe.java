import java.awt.Color;
import java.awt.Graphics;

public class TicTacToe {
	private int[][] board;
	private int winner;
	private int size;
	private int offsetX, offsetY;

	public TicTacToe(int s) {
		board = new int[3][3];
		winner = -1;
		this.size = s;
	}
	public TicTacToe(int s, int offsetX, int offsetY) {
		board = new int[3][3];
		winner = -1;
		this.size = s;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}

	public boolean placePiece(int row, int col, int player) {
		if (board[row][col] == 0) {
			board[row][col] = player;
			// check the winner
			checkWin(player);
			return true;
		}
		return false;
	}
	private void checkWin(int player) {
		boolean hasZero = false;
		for (int row=0; row<3; row++) {
			for (int col=0; col<3; col++) {
				hasZero = true;
			}
		}
		if (!hasZero)
			winner = 3;
		
		for (int i=0; i<3; i++) {
			if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
				winner = player;
			}
			if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
				winner = player;
			}
		}
		if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
			winner = player;
		}
		if (board[2][0] == player && board[1][1] == player && board[0][2] == player) {
			winner = player;
		}
	}

	public int getWinner() {
		return this.winner;
	}

	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(this.size/3-10 + offsetX, offsetY, 20, this.size);
		g.fillRect(this.size*2/3-10+ offsetX, offsetY, 20, this.size);
		g.fillRect(offsetX, this.size/3-10+ offsetY, this.size, 20);
		g.fillRect(offsetX, this.size*2/3-10+ offsetY, this.size, 20);

		int distance = this.size/3;
		for (int r=0; r<3; r++) {
			for (int c=0; c<3; c++) {
				if (board[r][c] == 1) {
					g.setColor(Color.blue);
					g.fillRect(c*distance+10+ offsetX, r*distance+10+offsetY, distance-30, distance-30);
				}
				if (board[r][c] == 2) {
					g.setColor(Color.red);
					g.fillRect(c*distance+10+ offsetX, r*distance+10+offsetY, distance-30, distance-30);
				}
			}
		}
		if (winner == 1) {
			g.setColor(Color.blue);
			g.fillRect(offsetX, offsetY, size, size);
		}
		if (winner == 2) {
			g.setColor(Color.red);
			g.fillRect(offsetX, offsetY, size, size);
		}
	}
}
