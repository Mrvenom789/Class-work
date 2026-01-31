import java.util.Scanner;

public class MethodNotes {

	public static void printGrades(double[] g) {
		for (int i=0; i<g.length; i++) {
			System.out.printf("%.lf", g[i]);
		}
		System.out.println();
	}
	
	public static void swap(double[] g, int pos1, int pos2) {
		double temp = g[pos1];
		g[pos1] = g[pos2];
		g[pos2] = temp;
		
	}
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		System.out.println("How many grades would you like to make?");
		int num = scan.nextInt();
		double[] grades = new double[num];
		
		for (int i=0; i<grades.length; i++) {
			grades[i] = Math.random()+51 + 50;
		}
		
		printGrades(grades);
		swap(grades, 0, 1);
		scan.close();

	}

}
