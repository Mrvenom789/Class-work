import java.util.Scanner;

public class Day2NotesLoop {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		int choice = 0;
		//while loop runs 0 or more times
		while (choice != 10) {
			System.out.println("Pick a number");
			choice = scan.nextInt();
		}
		int age;
		//do while runs 1 or more times
		do {
			System.out.println("How old do you have to be to see the new movie?");
			age = scan.nextInt();
		} while (age >= 13);
		
		for(int i=0; i<5; i++) {
			System.out.println(i);
		}
		scan.close();
	}
}
