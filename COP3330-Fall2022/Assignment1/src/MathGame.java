//Zachary Hull
//Assignment 1: Java Arrays
//COP3330 0002
//09/07/2022

import java.util.Scanner;

public class MathGame {
	public static Scanner input = new Scanner(System.in);
	
	public static void display(int g[]) {
		for(int j=0; j<g.length; j++) {
			System.out.print(g[j] + " ");
		}
	}
	
	public static void playGame(int g[], int h, int c) {
		
		for(int k=0; k<g.length; k++) {
			h += g[k];
		}
		
		System.out.println("Add these values:");
		
		display(g);
		
		System.out.print("\n");
		
		c = input.nextInt();
		
		if(c != h) {
			System.out.println("That was incorrect. The value adds to be: " + h + "\n");
		}
		
		if(c == h) {
			System.out.println("That is correct!\n");
		}
	}
	
	public static void swap(int g[], int s, int n) {
		
		System.out.print("Current: ");
		display(g);

		System.out.print("\n");
		System.out.println("How many swaps?");
		s = input.nextInt();
		
		for(int i=0; i<s; i++) {
			int j = (int)(Math.random()*n);
			int k = (int)(Math.random()*n);
			while(j == k) {
				j = (int)(Math.random()*n);
				k = (int)(Math.random()*n);
			}
			
			int temp = g[j];
			g[j] = g[k];
			g[k] = temp;
		}
		System.out.print("Final: ");
		
		display(g);
		System.out.println(" \n");
	}

	public static void sort(int g[]) {
		System.out.print("Current: ");
		display(g);
		System.out.println();
		
		int temp = 0;
		
		for(int i=0; i<g.length-1; i++) {
			for(int p=0; p<g.length-i-1; p++) {
				if(g[p] > g[p+1]) {
					temp = g[p];
					g[p] = g[p+1];
					g[p+1] = temp;
				}
			}
		}
		
		System.out.print("Final: ");
		display(g);
		System.out.println(" \n");
	}
	
	public static void main(String[] args) {
		int answer = 0;
		int choice = 0;
		int number = 0;
		int compare = 0;
		int switches = 0;
		
		System.out.println("Welcome to random number generator!");
		System.out.println("How many values would you like to use:");
		
		number = input.nextInt();
		
		int[] array = new int[number];
		
		for(int i=0; i<number; i++) {
			array[i] = (int)(Math.random()*(20 - 5) +5);
		}
		
		System.out.println("Created!");
		
		while(choice != 5) {
			System.out.println("Select your option:");
			System.out.println("1. Play Game");
			System.out.println("2. Swap Values");
			System.out.println("3. New Values");
			System.out.println("4. Sort");
			System.out.println("5. Exit");
			
			choice = input.nextInt();
			
			if(choice == 1) {
				playGame(array, answer, compare);
			}
			
			if(choice == 2) {
				swap(array, switches, number);
			}
			
			if(choice == 3) {
				
				System.out.print("New Values: ");
				for(int i=0; i<number; i++) {
					array[i] = (int)(Math.random()*(20 - 5) + 5);
					System.out.print(array[i] + " ");
				}
				System.out.println(" \n");
			}
			
			if(choice == 4) {
				sort(array);
			}
		}
		input.close();
	}
}