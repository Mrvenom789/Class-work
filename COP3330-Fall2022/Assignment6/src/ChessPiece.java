//Zachary Hull
//Assignment 6: Inheritance
//COP3330 0002
//11/02/2022

import java.util.ArrayList;

public abstract class ChessPiece {
	/**
	 * masterList stores all the chess pieces in the game.
	 * color stores the color of the chess piece.
	 */
	public static ArrayList<ChessPiece> masterList = new ArrayList<ChessPiece>();
	private String color;
	
	public ChessPiece(String color) {
		this.color = color;
		masterList.add(this);
	}
	
	public String getColor() {
		return this.color;
	}
	/**
	 * @param row - The row of the space we are checking
	 * @param col - The column of the space we are checking
	 * @return return the chess piece that is currently in this space.  
	 * If there is none return null.
	 */
	public static ChessPiece isOccupied(int row, int col) {
		for(int i=0; i<masterList.size(); i++) {
			masterList.get(i);
			if(masterList.get(i).getRow() == row && masterList.get(i).getCol() == col) {
				return masterList.get(i);
			}
		}
		return null;
	}
	
	public abstract int getRow();
	public abstract int getCol();
	public abstract boolean isValidMove(int row, int col);
}
