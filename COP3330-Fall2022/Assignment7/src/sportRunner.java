//Zachary Hull
//Assignment 8: interfaces
//COP 3330 0002
//11/16/2022

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class sportRunner {

	public static void main(String[] args) throws FileNotFoundException{
		System.out.println("Please enter the name of the file:");
		Scanner scan = new Scanner(System.in); //initial scanner
		
		String nameInput = scan.nextLine(); //input name of text file
		Scanner inputFile = new Scanner(new File(nameInput)); //file scanner
		
		ArrayList<Sports> team = new ArrayList<Sports>(); //defines array list
		
		int num = inputFile.nextInt(); //reads number of teams
		
		for(int i=0; i<num; i++) {
			
			String game = inputFile.next(); //reads sport name
			
			//creates basketball team
			if(game.equals("Basketball")) {
				team.add(new Basketball(0, i));
			}
			
			//creates football team
			else if(game.equals("Football")) {
				team.add(new Football(0, i));
			}
		}
		
		int num2 = inputFile.nextInt(); //reads number of points earned
		
		for(int j = 0; j < num2; j++) {
			int check = inputFile.nextInt(); //checks team id
			String score = inputFile.next(); //checks size of points earned
			
			team.get(check);
			
			if(score.equals("s")) { //adds small score
				team.get(check).scoreSmall();
			}
			else if(score.equals("m")) { //adds medium score
				team.get(check).scoreMed();
			}
			else if(score.equals("l")) { //adds large score
				team.get(check).scoreLarge();
			}
		}
		
		Collections.sort(team);
		
		for(int k = 0; k<team.size(); k++) {
			team.get(k);
			System.out.println(team.get(k).getId() + " - " + team.get(k).getScore());
		}
		
		scan.close();
		inputFile.close();
	}
}
