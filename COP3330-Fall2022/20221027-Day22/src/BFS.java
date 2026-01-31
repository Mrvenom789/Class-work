import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class BFS {
	
	public static boolean isBounds(int row, int col, int maxRow, int maxCol) {
		if(row<0 || col < 0) return false;
		if(row)
	}

	public static void main(String[] args) {
		Scanner scan = new Scanner (System.in);
		int rows, cols;
		rows = scan.nextInt();
		cols = scan.nextInt();
		Queue<Point> bfs = new LinkedList<Point>();
		scan.nextLine();
		
		char[] [] board = new char[rows][cols];
		int[] [] distance = new int[rows][cols];
		
		for (int i=0; i<rows; i++) {
			board[i] = scan.nextLine().toCharArray();
			Arrays.fill(distance[i], -1);
			
			//Arrays.fill is the same as below
			/*for(int j=0; j<cols; j++){
				distance[i][j] = -1;
			}*/
		}
		
		//fins the S to know where to start
		for (int row=0; row<rows; row++) {
			for (int col=0; col<cols; col++) {
				if(board[row][col] == 'S') {
					bfs.add(new Point(col, row));
					distance[row][col] = 0;
				}
			}
		}
		
		while(!bfs.isEmpty()) {
			Point current = bfs.remove();
			
			int[] deltaX = {0, 1, 0, -1};
			int[] deltaY = {1, 0, -1, 0};
			
			if(isBounds(current.getY()-1, current.getX(), rows, cols)) {   //is it in bound
				if(board[current.getY()-1] [current.getX()] == '.') {      //is a valid move
					if(distance[current.getY()-1][current.getX()] == -1) { //have we been there
						distance[current.getY()-1][current.getX()] = distance[current.getY()][current.getX()]+1;
						bfs.add(new Point(current.getX(), current.getY()));
					}
				}
			}
		}
		
		for(int row=0; row<rows; row++) {
			for(int col=0; col<cols; col++) {
				if(distance[row][col] == -1) 
			}
			
		}
		
	}
	
	public class Point{
		private int x, y;
		
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public int getX() {
			return this.x;
		}
		
		public int getY() {
			return this.y;
		}
	}

}

/*
5 6
......
......
..S...
......
......

5 6
......
..X...
..SXX.
..XXX.
...X.X
*/