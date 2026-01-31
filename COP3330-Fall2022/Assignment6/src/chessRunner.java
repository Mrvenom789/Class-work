//Zachary Hull
//Assignment 6: Inheritance
//COP3330 0002
//11/02/2022

public class chessRunner {
	
	public static void main(String[] args) {
		new Rook("Black", 6, 4);
		new Knight("White", 7, 4);
		new Knight("Black", 6, 2);
		new Rook("Black", 2, 4);
		//new Knight("White", 5, 4);

		System.out.println(ChessPiece.masterList.get(0).getCol());
		System.out.println(ChessPiece.masterList.get(0).getRow());
		System.out.println(ChessPiece.masterList.get(0).isValidMove(6, 6));
		System.out.println(ChessPiece.masterList.get(0).isValidMove(6, 5));
		System.out.println(ChessPiece.masterList.get(0).isValidMove(6, 3));
		System.out.println(ChessPiece.masterList.get(0).isValidMove(6, 2));
		System.out.println(ChessPiece.masterList.get(0).isValidMove(6, 1));
		System.out.println(ChessPiece.masterList.get(0).isValidMove(7, 1));
		System.out.println(ChessPiece.masterList.get(0).isValidMove(1, 4));
		System.out.println(ChessPiece.masterList.get(0).isValidMove(2, 4));
		System.out.println(ChessPiece.masterList.get(0).isValidMove(3, 4));
		System.out.println(ChessPiece.masterList.get(0).isValidMove(4, 4));
		System.out.println(ChessPiece.masterList.get(0).isValidMove(8, 4));
		System.out.println(ChessPiece.masterList.get(0).isValidMove(7, 4));
		

	}
	
}
