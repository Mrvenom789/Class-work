//Zachary Hull
//Assignment 6: Inheritance
//COP3330 0002
//11/02/2022

public class Knight extends ChessPiece{
	private int row, col;

	public Knight(String color, int row, int col) {
		super(color);
		this.row = row;
		this.col = col;
	}

	@Override
	public int getRow() {
		return this.row;
	}

	@Override
	public int getCol() {
		return this.col;
	}

	@Override
	public boolean isValidMove(int row, int col) {
		//checks if piece is in bounds
		if(row >= 0 && row <= 8 && col >= 0 && col <= 8) {
			//space is not empty
			if(super.isOccupied(row, col) != null) {
					//returns false when color of pieces are the same
				if(super.isOccupied(row, col).getColor().equals(this.getColor())) {
						return false;
					}
						//row + and - 2, col + and - 1
				else if((Math.abs(row - this.row) == 2) && (Math.abs(col - this.col) == 1)) {
					return true;
				}
						//row + and - 1, col + and - 2
				else if((Math.abs(row - this.row) == 1) && (Math.abs(col - this.col) == 2)) {
					return true;
				}
			}
			//The space is empty
			else {
				//row + and - 2, col + and - 1
				if((Math.abs(row - this.row) == 2) && (Math.abs(col - this.col) == 1)) {
					return true;
				}
				//row + and - 1, col + and - 2
				else if((Math.abs(row - this.row) == 1) && (Math.abs(col - this.col) == 2)) {
					return true;
				}
			}
		}
		return false;
	}

}
