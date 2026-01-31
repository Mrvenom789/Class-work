//Zachary Hull
//Assignment 6: Inheritance
//COP3330 0002
//11/02/2022

public class Rook extends ChessPiece {
	private int row, col;

	public Rook(String color, int row, int col) {
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
		//distance between start and end
		int deltaRow = row - this.row;
		int deltaCol = col - this.col;
		
		//cannot go diagonal
		if(deltaRow != 0 && deltaCol != 0) {
			return false;
		}
		//checks if destination is out of bounds
		if(row < 0 || row > 8 || col < 0 || col > 8) {
			return false;
		}
		if(super.isOccupied(row, col) != null) {//false if same color at destination
			if(super.isOccupied(row, col).getColor().equals(this.getColor())) { //same color
				return false;
			}
		}
		//rook goes right
		if(deltaRow > 0) { //loops through all spaces between start and target
			for(int i=1; i<Math.abs(deltaRow); i++) {
				if(super.isOccupied(row - i, col) != null) {//piece in between start and end
					return false;
				}
			}
			//space is empty, or has piece of different color
			return true;
		}
		
		//rook goes left
		if(deltaRow < 0) { //loops through all spaces between start and target
			for(int i=1; i<Math.abs(deltaRow); i++) {
				if(super.isOccupied(row + i, col) != null) {//piece in between start and end
					return false;
				}
			}//space is empty, or has piece of different color
			return true;
		}
		//rook goes up
		if(deltaCol > 0) { //loops through all spaces between start and target
			for(int i=1; i<Math.abs(deltaCol); i++) {
				if(super.isOccupied(row, col - i) != null) {//piece in between start and end
					return false;
				}
			}//space is empty, or has piece of different color
			return true;
		}
		//rook goes down
		if(deltaCol < 0) { //loops through all spaces between start and target
			for(int i=1; i<Math.abs(deltaCol); i++) {
				if(super.isOccupied(row, col + i) != null) {//piece in between start and end
					return false;
				}
			}//space is empty, or has piece of different color
			return true;
		}//rook does not move at all
		return false;
	}
}
