//Zachary Hull
//Assignment 4: File I/O and Linked Lists
//COP 3330 0002
//10/30/2022

public class customer {
	private String name;
	private boolean returning;
	
	public customer(String name, boolean returning) {
		this.name = name;
		this.returning = returning;
	}
	
	public String getName() {
		return this.name;
	}
	public boolean getBusiness() {
		return this.returning;
	}
}
