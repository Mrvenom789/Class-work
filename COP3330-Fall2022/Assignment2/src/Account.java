//Zachary Hull
//Assignment 2: Objects
//COP3330 0002
//09/14/2022

public class Account {
	
	private String name;
	private double balance;
	private double interestRate;
	private double funds;
	
	//constructor
	public Account(String name, double interestRate) {
		this.name = name;
		this.interestRate = interestRate;
	}
	
	//tests is money can be deposited
	public boolean deposit(double funds) {
		if(funds <= 0)
			return false;
		return true;
	}
	
	//checks if money can be withdrawn
	public boolean withdraw(double balance, double funds) {
		if(balance < funds)
			return false;
		if(funds <= 0)
			return false;
		return true;
	}
	
	//returns balance
	public double getBalance() {
		return this.balance;
	}
	
	//updates balance
	public double setBalance(double balance) {
		this.balance = balance;
		return this.balance;
	}
	
	//set account name
	public void setName(String name) {
		this.name = name;
	}
	
	//returns account name
	public String getName() {
		return this.name;
	}
	
	//calculates the interest
	public double calcInterest(int month) {
		double interestPeriod = (double) (month / 12.0) * interestRate / 100;
		balance = balance * (1 + interestPeriod);
		return this.balance;
	}
}
