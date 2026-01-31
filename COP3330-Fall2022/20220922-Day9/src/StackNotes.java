import java.util.Stack;

public class StackNotes {

	public static void main(String[] args) {
		Stack<Integer> pancakes = new Stack<Integer>();
		
		pancakes.push(3);
		pancakes.push(5);
		System.out.println(pancakes);
		
		System.out.println(pancakes.pop());
		System.out.println(pancakes);
		
		System.out.println(pancakes.peek());

	}

}
