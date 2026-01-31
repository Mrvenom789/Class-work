import java.util.Arrays;
import java.util.Scanner;

public class ArrayNotes {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		
		int[] grades;
		grades = new int[10];
		grades[9] = 72;
		int[] another = new int[10];
		
		//default value of an int is zero
		
		for(int i=0; i<10; i++) {
			System.out.println(i + ": " + grades[i]);
		}
		
		System.out.println( grades.length );
		System.out.println("What size array would you want?");
		
		int size = scan.nextInt();
		grades = new int[size];
		
		// [0,1)
		//[0,1) * 6 = [0,6)
		//[0,1) + 1 = [1,7)
		for (int i=0; i<grades.length; i++) {
			grades[i] = (int) Math.random() * 6 + 1;
		}
		int sum = 0;
		for (int i=0; i<grades.length; i++) {
			sum += grades[i];
		}
		
		System.out.println(sum);
		
		for (int i=0; i<grades.length; i++) {
			System.out.println(i + ": " + grades[i]);
		}
		
		Arrays.sort(grades);
		for (int i=0; i<grades.length; i++) {
			System.out.println(i + "; " + grades[i]);
		}
		
		Arrays.fill(grades, 100);
		for (int i=0; i<grades.length; i++) {
			System.out.println(i + "; " + grades[i]);
		}
		
		int temp = grades[0];
		grades[0] = grades[grades.length-1];
		
		grades[grades.length-1] = temp;
		
		scan.close();
		

	}

	private static void close(Scanner scan) {
		// TODO Auto-generated method stub
		
	}

}
