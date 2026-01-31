//Zachary Hull
//Assignment 4: File I/O and Linked Lists
//COP 3330 0002
//10/30/2022

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class store {

	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("Please enter the name of the file:");
		Scanner scan = new Scanner(System.in); //initial scanner
		
		String nameInput = scan.nextLine(); //input name of text file
		Scanner inputFile = new Scanner(new File(nameInput)); //file scanner
		
		//set up linked lists
		LinkedList<customer> checkOut = new LinkedList<customer>();
		LinkedList<customer> returnLine = new LinkedList<customer>();
		
		//takes in number of names from text file
		int number = inputFile.nextInt();
		
		//initialize variables
		String name = null;
		boolean what;
		
		for(int i=0; i<number; i++) {
			//add names and booleans to the check out line
			name = inputFile.next();
			what = inputFile.nextBoolean();
			checkOut.add(new customer(name, what));
		}
		
		while(!checkOut.isEmpty()) { //loop ends when empty
			for(int i=0; i<2; i++) { //checks out two customers at a time
				if(!checkOut.isEmpty()) {
					customer c = checkOut.removeFirst(); //stores customer information
					if(c.getBusiness() == true) { //customer is forwarded to return line
						returnLine.add(c);
						System.out.println(c.getName() + " was forwarded to the return line");
					}
					else { //customer is checked out
						System.out.println(c.getName() + " checked out");
					}
				}
			}
			//customer returns item
			if(!returnLine.isEmpty()) {
				customer c = returnLine.removeFirst();
				System.out.println(c.getName() + " returned an item.");
			}
			
		}
		scan.close();
		inputFile.close();
	}

}
