import java.util.Scanner;

public class Day2NotesIF {
	public static void main(String[] args) {
	
	Scanner scan = new Scanner(System.in);
	
	System.out.println("What grade do you have in this class?");
	int grade = scan.nextInt();
	
	if (grade >= 90) {
		System.out.print("A");
	} else if (grade >= 80) {
		System.out.print("B");
	} else if (grade >= 70) {
		System.out.println("C");
	} else {
		System.out.println("F");
	}
	
	scan.close();
	}
}
