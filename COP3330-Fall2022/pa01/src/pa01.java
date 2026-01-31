import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class pa01 {
	/*=============================================================================
	| Assignment: pa01 - Encrypting a plaintext file using the Vigenere cipher
	|
	| Author: Zachary Hull
	| Language: Java
	|
	| To Compile: javac pa01.java
	| gcc -o pa01 pa01.c
	| g++ -o pa01 pa01.cpp
	| go build pa01.go
	|
	| To Execute: java -> java pa01 kX.txt pX.txt
	| or c++ -> ./pa01 kX.txt pX.txt
	| or c -> ./pa01 kX.txt pX.txt
	| or go -> ./pa01 kX.txt pX.txt
	| or python -> python3 pa01.py kX.txt pX.txt
	| where kX.txt is the keytext file
	| and pX.txt is plaintext file
	|
	| Note: All input files are simple 8 bit ASCII input
	|
	| Class: CIS3360 - Security in Computing - Spring 2023
	| Instructor: McAlpin
	| Due Date: 03/10/2023
	|
	+=============================================================================*/

	public static void main(String[] args) throws FileNotFoundException{
		Scanner scan = new Scanner(System.in); //initial scanner
		
		Scanner kinputFile = new Scanner(new File(args[0])); //file scanner
		Scanner pinputFile = new Scanner(new File(args[1])); //file scanner
		
		//size of matrix
		int size = kinputFile.nextInt();
		//create arrays
		int [][] matrix = new int[size][size];
		int [][] letterArray = new int[10000][size];
		System.out.println("\nKey matrix:");
		
		int[] key = new int[1];
		
		//print key matrix
		for(int i=0; i<size; i++) {
			for(int j=0; j<size; j++) {
				matrix[i][j] = kinputFile.nextInt();
				System.out.print("\t" + matrix[i][j]);
			}
			System.out.println("\n");	
		}
		char letter = '\0';
		int countChar = 0;
		int row = 0;
		int index = 0;
		int changeToInt;
		//print plain text
		System.out.println("\nPlaintext:");
		int pt = 0;
		String line;
		while(pinputFile.nextLine() != null) {
			line = scan.nextLine();
			for(int i=0; i<line.length(); i++) {
				letter = line.charAt(pt);
				if(letter >= 'a' && letter <= 'z' || letter >= 'A' && letter <='Z') {
					System.out.print(Character.toLowerCase(letter));
					countChar++;
					changeToInt = Character.toLowerCase(letter) - 97;
					letterArray[row][index] = changeToInt;
					index++;
					//if reach matrix size
					if(index == size) {
						index = 0;
						row++;
					}
					if(countChar == 80) {
						countChar = 0;
						System.out.print("\n");
					}
				
					
				}
				else
					continue;
			}
		}
		
		
	}

	
	/*=============================================================================
	| I Zachary Hull (5062063) affirm that this program is
	| entirely my own work and that I have neither developed my code together with
	| any another person, nor copied any code from any other person, nor permitted
	| my code to be copied or otherwise used by any other person, nor have I
	| copied, modified, or otherwise used programs created by others. I acknowledge
	| that any violation of the above terms will be treated as academic dishonesty.
	+=============================================================================*/
}
