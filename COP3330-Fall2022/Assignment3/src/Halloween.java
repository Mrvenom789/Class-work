//Zachary Hull
//Assignment 3: Strings and Method Overloading
//COP3330 0002
//10/12/2022

import java.util.Scanner;

public class Halloween {
	public static String[] sticker;
	public static int count = 0;
	
	public static void setSticker() { //sets message to each index
		sticker = new String[4];
		sticker[0] = "PUMPKIN";
		sticker[1] = "BAT";
		sticker[2] = "WITCH";
		sticker[3] = "VAMPIRE";
	}
	
	public static String sticker(String name) { //replaces '$' with stickers in name
		String newName = "";
		String subName = "";
		setSticker();
		
		for(int i = 0; i<name.length(); i++) {
			if(count > 3) { //prevents stickers from going out of bounds
				count = 0;
			}
			if(name.charAt(i) == '$') { //replace '$' with two stickers
				newName = newName.concat(sticker[count]);
				count++;
				if(count > 3) { //prevents OOB in the middle
					count = 0;
				}
				newName = newName.concat(sticker[count]);
				count++;
			}
			else { //no '$' detected
				if(i==0) { //use substring at index 0
					subName = name.substring(0,1);
					newName = newName.concat(subName);
				}
				else { //normal case
					subName = name.substring(i, i+1);
					newName = newName.concat(subName);
				}
			}
		}
		//return the name with stickers
		return newName;
	}
	
	public static String sticker(String message, Boolean check) { //replaces '$' with stickers in date/slogan
		if(check == true) {// output is empty string
			message = "";
			return "";
		}
		else {
			String newSlogan = "";
			String subSlogan = "";
			setSticker();
				
			for(int i = 0; i<message.length(); i++) {
				if(count > 3) { //prevents stickers from going out of bounds
					count = 0;
				}
				if(message.charAt(i) == '$') { //replaces '$' with one sticker
					newSlogan = newSlogan.concat(sticker[count]);
					count++;
				}
				else { //no '$' detected
					if(i==0) { //use substring at index 0
						subSlogan = message.substring(0,1);
						newSlogan = newSlogan.concat(subSlogan);
					}
					else { //normal case
						subSlogan = message.substring(i, i+1);
						newSlogan = newSlogan.concat(subSlogan);
					}
				}
			}
			//return slogan with stickers
			return newSlogan;
		}
	}
	
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		boolean input;
		
		//select data type
		System.out.println("What type of data?");
		String data = scan.nextLine();
		String data2 = data.toLowerCase();
		
		while(data2.equals("exit") == false) { //sets exit condition
			if(data2.equals("name")) {
				System.out.println("What is the message?");
				String inputName = scan.nextLine(); //input name
				if(inputName.contains("$")) {
					System.out.println(sticker(inputName));
				}
				else { //no '$' in string
					System.out.println(inputName);
				}
			}
			
			else if(data2.equals("date")) {
				input = true; //input is a date
				System.out.println("What is the message?");
				String inputDate = scan.nextLine(); //input date
				if(inputDate.contains("$")) {
					System.out.println(sticker(inputDate, input));
				}
				else { //no '$' in string
					System.out.println(inputDate);
				}
			}
			
			else if(data2.equals("slogan")) {
				input = false; //input is not date
				System.out.println("What is the message?");
				String inputSlogan = scan.nextLine(); //input slogan
				if(inputSlogan.contains("$")) {
					System.out.println(sticker(inputSlogan, input));
				}
				else { //no '$' in string
					System.out.println(inputSlogan);
				}
			}
			//select next data type or end loop
			System.out.println("What type of data?");
			data = scan.nextLine();
			data2 = data.toLowerCase();
		}

		scan.close();
	}

}
