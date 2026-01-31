
public class username {
	/*
	 * instance variable
	 */
	private String name, username;
	private int age;
	/*
	 * static variable
	 */
	
	public static String [] usedUsername;
	/*
	 * constructor
	 */
	public username(String name) {
	this.name = name;
	this.age = -1;
	this.username = null;
	}
	
	public username(String name, int age) {
		this.name = name;
		this.age = age;
		this.username = null;
	}
	
	public username(String name, int age, String username) {
		this.name = name;
		this.age = age;
		this.username = username;
	}
	
	/*
	 * instance methods
	 */
	
	public void genUsername() {
		int method = (int)(Math.random()*4);
		
		switch (method) {
		case 0:
			this.username = this.name;
			break;
		case 1:
			this.username = this.name + (2022-this.age);
			if(this.age != -1)
				break;
		case 2:
			//Substring is the same as str
			this.username = this.name.substring(2) + 2022;
			break;
		case 3:
			this.username = this.name.substring(0, this.name.length()-2) + 22;
			break;
		}
	}
	
	public String getUsername() {
		if(this.username == null) {
			return "name not found";
		}
		return this.username;
	}

}
