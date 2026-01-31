import java.util.Scanner;

//Zachary Hull
//Assignment 0: Welcome to OOP!
//COP3330 0002
//08/31/2022

public class Daytona {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		int choice = 0;
		
		System.out.println("Welcom to Daytona Beach!");
		
		while (choice != 4) {
			System.out.println("What would you like to do?");
			System.out.println("1. Drive on the Beach");
			System.out.println("2. Play Disc Golf");
			System.out.println("3. See the race");
			System.out.println("4. Exit");
			
			choice = scan.nextInt();
			
			if(choice == 1) {
				System.out.println("What time did Knightro go to the beach (24-hour clock)");
				int hour = 0;
				
				hour = scan.nextInt();
				
				if(hour >=9 && hour <= 18) {
					System.out.println("Enjoy your drive!\n");
				}
				else {
					System.out.println("Oh no! The beach is closed\n");
				}
				
			}
			
			if(choice == 2) {
				System.out.println("Welome to the park!");
				
				int disc = 0;
				int feet = 347;
				
				while(feet != 0) {
					System.out.println("There is " + feet + " feet left until the basket");
					System.out.println("How far did you throw?");
					
					disc = scan.nextInt();
					feet -= disc;
					
					if(feet < 0) {
						feet *= -1;
					}
				}
				System.out.println("You made it in!\n");
				
			}
			
			if(choice == 3) {
				System.out.println("How many laps are left?");
				int lap = 0;
				lap = scan.nextInt();
				
				while(lap != 0) {
					System.out.println("There are " + lap + " laps left");
					lap -= 1;
				}
				System.out.println("Finished!\n");
			}
			
		}

		System.out.println("Goodbye");
		
		scan.close();
	}

}
