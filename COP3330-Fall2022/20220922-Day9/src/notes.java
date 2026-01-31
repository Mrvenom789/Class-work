import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class notes {

	public static void main(String[] args) {
		Queue<Integer> disneyLine = new LinkedList<Integer>();

		int variable = 83;
		disneyLine.add(10);
		disneyLine.add(7);
		disneyLine.add(variable);
		System.out.println(disneyLine);
		
		int next = disneyLine.peek();
		System.out.println(next);
		System.out.println(disneyLine);
		
		//poll - removes next item in the queue and returns it
		int getValue = disneyLine.poll();
		System.out.println(getValue);
		System.out.println(disneyLine);
		
		while(!disneyLine.isEmpty()) {
			System.out.println(disneyLine.poll());
			System.out.println(disneyLine);
		}
	}

}
