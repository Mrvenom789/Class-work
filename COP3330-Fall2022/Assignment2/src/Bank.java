import java.util.Scanner;

//Zachary Hull
//Assignment 2: Objects
//COP3330 0002
//09/14/2022

public class Bank {
	public static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) {
		//define array and variables
		Account[] number = new Account[5];
		int choice = 0;
		double interestRate = 0.0;
		double balance = 0.00;
		
		System.out.println("Welcome to Knightro Banking: ");
		
		//print account names
		for(int i=0; i<number.length; i++) {
			if(number[i] == null)
				System.out.println((i+1) + ". Empty");
			else
				System.out.println((i+1) + ". " + number[i]);
			}
		System.out.println("6. Exit Program");
		System.out.println("What would you like to do?");
		choice = scan.nextInt();
		
		int index = choice - 1;
		
		if(choice < 6) {
			System.out.println("Creating new account");
			System.out.println("What is the name of the account: ");
					
			String name = scan.next();
						
			System.out.println("What is the interest rate?");
						
			interestRate = scan.nextDouble();
			number[index] = new Account(name, interestRate); 
			number[index].setName(name);
						
			while(index <= 6){		
				//runs through the list of accounts
				for(int i=0; i<number.length; i++) {
					if(number[i] == null) {
						System.out.println((i+1) + ". Empty");
					}
					else {
						System.out.println((i+1) + ". " + number[i].getName());
					}
				}
					System.out.println("6. Exit Program");
					System.out.println("What would you like to do?");
							
					choice = scan.nextInt();
					index = choice - 1;
					
					//exits program
					if(choice == 6)
						break;
					
					//displays menu items after selecting an active account
					if(number[index] != null) {
						System.out.println("1. Deposit Funds");
						System.out.println("2. Withdraw Funds");
						System.out.println("3. Check Balance");
						System.out.println("4. Calculate Interest");
						System.out.println("5. Close Account");
								
						int select = scan.nextInt();
						
						switch(select) {
						//user wants to deposit
						case 1:
							balance = number[index].getBalance();
							System.out.println("How much money would you like to deposit?");
							double amount = scan.nextDouble();
							if(number[index].deposit(amount) == true) {
								balance += amount;
								number[index].setBalance(balance);
								System.out.printf("You have added $%.2f\n", amount);
							}
							else {
								System.out.println("This is an invalid amount");
							}
							break;
							
						//user wants to withdraw
						case 2:
							balance = number[index].getBalance();
							System.out.println("How much would you like to withdraw?");
							amount = scan.nextDouble();
							if(number[index].withdraw(balance, amount) == true) {
								balance = balance - amount;
								number[index].setBalance(balance);
								System.out.printf("You have withdrawn $%.2f\n", amount);
							}
							else {
								System.out.println("This is an invalid amount");
							}
							break;
						//user wants to check balance
						case 3:
							balance = number[index].getBalance();
							System.out.printf("You have $%.2f\n", balance);
							break;
						
						//user wants to calculate interest
						case 4:
							System.out.println("How many months have past?");
							int months = scan.nextInt();
							balance = number[index].calcInterest(months);
							number[index].setBalance(balance)
;							System.out.println("Done");
							break;
						
						//user wants to close account
						case 5:
							System.out.println("Account closed.");							
							number[index] = null;
							
						}
					}
					//account is null when selecting
					else {
						System.out.println("Creating new acount");
						System.out.println("What is the name of the account: ");
								
						name = scan.next();
									
						System.out.println("What is the interest rate?");
						interestRate = scan.nextDouble();
						
						number[index] = new Account(name, interestRate); 
					}
				}
			scan.close();
			System.out.println("Goodbye");
		}
		//user wants to close program at the very beginning (before first loop)
		else
			System.out.println("Goodbye");
	}
}